package com.skshazena.flooringmastery.service;

import com.skshazena.flooringmastery.dao.FlooringExportDao;
import com.skshazena.flooringmastery.dao.FlooringOrderDao;
import com.skshazena.flooringmastery.dao.FlooringPersistenceException;
import com.skshazena.flooringmastery.dao.FlooringProductDao;
import com.skshazena.flooringmastery.dao.FlooringTaxDao;
import com.skshazena.flooringmastery.dto.Order;
import com.skshazena.flooringmastery.dto.ProductType;
import com.skshazena.flooringmastery.dto.StateTaxInfo;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 28, 2020
 */
public class FlooringServiceImpl implements FlooringService {

    private FlooringOrderDao orderDao;
    private FlooringProductDao productDao;
    private FlooringTaxDao taxDao;
    private FlooringExportDao exportDao;
    private DateTimeFormatter patternForUser = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public FlooringServiceImpl(FlooringOrderDao orderDao, FlooringProductDao productDao, FlooringTaxDao taxDao, FlooringExportDao exportDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
        this.exportDao = exportDao;
    }

    @Override
    public List<ProductType> getAllProducts() throws FlooringPersistenceException {
        return productDao.getAllProductTypeObjects();
    }

    @Override
    public List<Order> getAllOrdersForSpecifiedDate(LocalDate date) throws FlooringPersistenceException, FlooringNoMatchingOrdersException {
        List<Order> listOfOrders = orderDao.getAllOrdersForSpecifiedDate(date);
        if (listOfOrders != null) {
            return listOfOrders;
        } else {
            throw new FlooringNoMatchingOrdersException("There are no orders for " + date.format(patternForUser));
        }
    }

    @Override
    public Order getAnOrder(LocalDate date, int orderNumber) throws FlooringPersistenceException, FlooringNoSuchOrderException {
        Order theOrder = orderDao.getOneOrder(date, orderNumber);
        if (theOrder != null) {
            String stateAbbreviation = theOrder.getTaxInfo().getStateAbbreviation();
            StateTaxInfo stateTaxInfoObject = taxDao.getStateTaxInfoObjectFromAbbreviation(stateAbbreviation);
            theOrder.setTaxInfo(stateTaxInfoObject);
            return theOrder;
        } else {
            throw new FlooringNoSuchOrderException("That order does not exist");
        }
    }

    @Override
    public Order editValidateAndCalculateOrder(Order originalOrder, Order editedOrder) throws FlooringPersistenceException, FlooringOrderValidationException {

        LocalDate dateOfOrder = originalOrder.getDateOfOrder();
        int orderNumber = originalOrder.getOrderNumber();

        String customerName = originalOrder.getCustomerName();
        if (!(editedOrder.getCustomerName().isBlank())) {
            customerName = editedOrder.getCustomerName();
        }

        String stateName = originalOrder.getTaxInfo().getStateName();
        if (!editedOrder.getTaxInfo().getStateName().isBlank()) {
            stateName = editedOrder.getTaxInfo().getStateName();
        }

        String productTypeName = originalOrder.getProductType().getProductType();
        if (!editedOrder.getProductType().getProductType().isBlank()) {
            productTypeName = editedOrder.getProductType().getProductType();
        }

        BigDecimal area = originalOrder.getArea();
        if (editedOrder.getArea() != null) {
            area = editedOrder.getArea();
        }

        Order editedOrderDetails = new Order(dateOfOrder, customerName, stateName, productTypeName, area);

        return validateAndCalculateOrder(editedOrderDetails, orderNumber);
    }

    @Override
    public Order validateAndCalculateOrder(Order theOrderSoFar, int orderNumber) throws FlooringPersistenceException, FlooringOrderValidationException {
        //brings in date, customerName, StateName, ProductTypeName, and Area

        //validate order - check all parts that came in, collect exceptions, throw them
        FlooringOrderValidationException theExceptions = new FlooringOrderValidationException();

        int howManyExceptions = 0;

        if (orderNumber == 0) {//this would mean that the order is a new one
            if (theOrderSoFar.getDateOfOrder().compareTo(LocalDate.now()) <= 0) {//could also use .isBefore()
                FlooringOrderValidationException dateException = new FlooringOrderValidationException("Date must be in the future.");
                theExceptions.addSuppressed(dateException);
                howManyExceptions++;
            }
        }

        if (!theOrderSoFar.getCustomerName().matches("[a-zA-Z0-9\\,\\.\\s]*")) {
            FlooringOrderValidationException customerNameException = new FlooringOrderValidationException("Customer Name must contain only letters, numbers, commas, and periods.");
            theExceptions.addSuppressed(customerNameException);
            howManyExceptions++;
        }

        //Make first letter capital and rest lowercase
        String stateName = theOrderSoFar.getTaxInfo().getStateName();
        stateName = stateName.substring(0, 1).toUpperCase() + stateName.substring(1).toLowerCase();

        StateTaxInfo stateTaxInfoObject = taxDao.getStateTaxInfoObjectFromName(stateName);

        if (stateTaxInfoObject == null) {
            FlooringOrderValidationException stateNameException = new FlooringOrderValidationException("State is not one that is sold in.");
            theExceptions.addSuppressed(stateNameException);
            howManyExceptions++;
        }

        //Make first letter capital and rest lowercase
        String productTypeName = theOrderSoFar.getProductType().getProductType();
        productTypeName = productTypeName.substring(0, 1).toUpperCase() + productTypeName.substring(1).toLowerCase();

        ProductType productTypeObject = productDao.getProductTypeObject(productTypeName);

        if (productTypeObject == null) {
            FlooringOrderValidationException productException = new FlooringOrderValidationException("Product not one of the available products.");
            theExceptions.addSuppressed(productException);
            howManyExceptions++;
        }

        if (theOrderSoFar.getArea().compareTo(new BigDecimal("100")) < 0) {
            FlooringOrderValidationException areaException = new FlooringOrderValidationException("Area needs to be at least 100 SqFt.");
            theExceptions.addSuppressed(areaException);
            howManyExceptions++;
        }

        if (howManyExceptions > 0) {
            throw theExceptions;
        }
        //Extract Data from order for new constructor
        LocalDate dateOfOrder = theOrderSoFar.getDateOfOrder();
        String customerName = theOrderSoFar.getCustomerName();
        BigDecimal area = theOrderSoFar.getArea();

        //now that exceptions are thrown, continue to calculate other fields of order
        //generateOrderNumber
        if (orderNumber == 0) { //if it is zero, then it is a new order and this needs to be generated.
            List<Order> listOfAllOrders = orderDao.getAllOrders();
            if (!listOfAllOrders.isEmpty()) {
                orderNumber = listOfAllOrders.stream().mapToInt((order) -> order.getOrderNumber()).max().getAsInt();
                orderNumber++;
            } else {
                orderNumber = 1;
            }
        }
        //materialCostForOrder, laborCostForOrder, TaxForOrder, Total
        int scaleForBigDecimal = 2;
        BigDecimal materialCostForOrder = area.multiply(productTypeObject.getCostPerSquareFoot()).setScale(scaleForBigDecimal, RoundingMode.HALF_UP);
        BigDecimal laborCostForOrder = area.multiply(productTypeObject.getLaborCostPerSquareFoot()).setScale(scaleForBigDecimal, RoundingMode.HALF_UP);
        BigDecimal materialAndLaborCost = materialCostForOrder.add(laborCostForOrder);
        BigDecimal taxPercentage = stateTaxInfoObject.getTaxRate().divide(new BigDecimal("100")).setScale(scaleForBigDecimal, RoundingMode.HALF_UP);
        BigDecimal taxForOrder = materialAndLaborCost.multiply(taxPercentage).setScale(scaleForBigDecimal, RoundingMode.HALF_UP);
        BigDecimal totalForOrder = materialAndLaborCost.add(taxForOrder);

        Order finalizedOrder = new Order(dateOfOrder, customerName, stateTaxInfoObject,
                productTypeObject, area, orderNumber,
                materialCostForOrder.setScale(scaleForBigDecimal, RoundingMode.HALF_UP),
                laborCostForOrder.setScale(scaleForBigDecimal, RoundingMode.HALF_UP),
                taxForOrder.setScale(scaleForBigDecimal, RoundingMode.HALF_UP),
                totalForOrder.setScale(scaleForBigDecimal, RoundingMode.HALF_UP));

        return finalizedOrder;
    }

    @Override
    public boolean addAnOrder(Order theOrder, String userApprovalOfOrder) throws FlooringPersistenceException {
        if (userApprovalOfOrder.equals("Y")) {
            orderDao.addOrder(theOrder);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean editAnOrder(Order theOrder, String userApprovalOfOrder) throws FlooringPersistenceException {
        if (userApprovalOfOrder.equals("Y")) {
            orderDao.editOrder(theOrder);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeAnOrder(LocalDate date, int orderNumber, String userApprovalOfOrder) throws FlooringPersistenceException {
        if (userApprovalOfOrder.equals("Y")) {
            orderDao.removeOrder(date, orderNumber);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void exportAllData() throws FlooringPersistenceException {
        List<Order> allOrders = orderDao.getAllOrders();
        allOrders.sort(Comparator.comparing(Order::getOrderNumber));
        exportDao.createBackupOfAllOrders(allOrders);
    }

}
