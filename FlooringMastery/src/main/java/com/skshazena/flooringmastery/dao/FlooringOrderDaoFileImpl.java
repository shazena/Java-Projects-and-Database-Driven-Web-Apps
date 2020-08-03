package com.skshazena.flooringmastery.dao;

import com.skshazena.flooringmastery.dto.Order;
import com.skshazena.flooringmastery.dto.ProductType;
import com.skshazena.flooringmastery.dto.StateTaxInfo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Flooring Order Dao File Implementation
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 28, 2020
 */
public class FlooringOrderDaoFileImpl implements FlooringOrderDao {

    private HashMap<LocalDate, HashMap<Integer, Order>> orderMapByDateThenOrderNum = new HashMap<>();
    private static String ORDER_FOLDER;
    public static final String DELIMITER = ",";
    public static final String DELIMITER_FOR_COMMA_REPLACEMENT = "%$^&?&#%";
    public static final String DATE_PATTERN_FOR_FILE = "MMddyyyy";

    public FlooringOrderDaoFileImpl() {
        ORDER_FOLDER = ".\\Orders";
    }

    public FlooringOrderDaoFileImpl(String ORDER_FOLDER) {
        this.ORDER_FOLDER = ORDER_FOLDER;
    }

    @Override
    public Order addOrder(Order newOrder) throws FlooringPersistenceException {
        loadOrderFiles();
        HashMap<Integer, Order> orderMapbyDate = orderMapByDateThenOrderNum.get(newOrder.getDateOfOrder());
        if (orderMapbyDate == null) {
            orderMapByDateThenOrderNum.put(newOrder.getDateOfOrder(), new HashMap<>());
        }
        orderMapbyDate = orderMapByDateThenOrderNum.get(newOrder.getDateOfOrder());
        Order addedOrder = orderMapbyDate.put(newOrder.getOrderNumber(), newOrder);
        orderMapByDateThenOrderNum.put(newOrder.getDateOfOrder(), orderMapbyDate);
        writeOrderFiles();
        return addedOrder;
    }

    @Override
    public Order getOneOrder(LocalDate date, int orderNumber) throws FlooringPersistenceException {
        loadOrderFiles();
        Set<LocalDate> allDates = orderMapByDateThenOrderNum.keySet();
        if (allDates.contains(date)) {
            return orderMapByDateThenOrderNum.get(date).get(orderNumber);
        } else {
            return null;
        }
    }

    @Override
    public List<Order> getAllOrders() throws FlooringPersistenceException {
        loadOrderFiles();
        Set<LocalDate> allDates = orderMapByDateThenOrderNum.keySet();//get all dates
        ArrayList<Order> listOfAllOrders = new ArrayList<>();//list to hold all orders
        allDates.stream().forEach((date) -> {
            ArrayList<Order> allOrdersForOneDate = new ArrayList<>(orderMapByDateThenOrderNum.get(date).values());
            listOfAllOrders.addAll(allOrdersForOneDate);//add this set of orders to the bigger list
        });
        return listOfAllOrders;
    }

    @Override
    public List<Order> getAllOrdersForSpecifiedDate(LocalDate date) throws FlooringPersistenceException {
        loadOrderFiles();
        Set<LocalDate> listOfDates = orderMapByDateThenOrderNum.keySet();
        if (listOfDates.contains(date)) {
            List<Order> listOfOrders = new ArrayList<>(orderMapByDateThenOrderNum.get(date).values());
            return listOfOrders;
        } else {
            return null;
        }
    }

    @Override
    public Order editOrder(Order editedOrder) throws FlooringPersistenceException {
        loadOrderFiles();
        HashMap<Integer, Order> orderMapByDate = orderMapByDateThenOrderNum.get(editedOrder.getDateOfOrder());
        Order theEditedOrder = orderMapByDate.put(editedOrder.getOrderNumber(), editedOrder);
        orderMapByDateThenOrderNum.put(editedOrder.getDateOfOrder(), orderMapByDate);
        writeOrderFiles();
        return theEditedOrder;
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) throws FlooringPersistenceException {
        loadOrderFiles();
        HashMap<Integer, Order> orderMapByDate = orderMapByDateThenOrderNum.get(date);
        Order removedOrder = orderMapByDate.remove(orderNumber);
        orderMapByDateThenOrderNum.put(date, orderMapByDate);
        writeOrderFiles();
        return removedOrder;
    }

    private String marshallOrder(Order anOrder) {

        String orderAsString = anOrder.getOrderNumber() + DELIMITER;

        String customerNameWithCommasReplaced = anOrder.getCustomerName();
        customerNameWithCommasReplaced = customerNameWithCommasReplaced.replace(DELIMITER, DELIMITER_FOR_COMMA_REPLACEMENT);
        orderAsString += customerNameWithCommasReplaced + DELIMITER;

        orderAsString += anOrder.getTaxInfo().getStateAbbreviation() + DELIMITER;
        orderAsString += anOrder.getTaxInfo().getTaxRate() + DELIMITER;
        orderAsString += anOrder.getProductType().getProductType() + DELIMITER;
        orderAsString += anOrder.getArea() + DELIMITER;
        orderAsString += anOrder.getProductType().getCostPerSquareFoot() + DELIMITER;
        orderAsString += anOrder.getProductType().getLaborCostPerSquareFoot() + DELIMITER;
        orderAsString += anOrder.getMaterialCostForOrder() + DELIMITER;
        orderAsString += anOrder.getLaborCostForOrder() + DELIMITER;
        orderAsString += anOrder.getTaxForOrder() + DELIMITER;
        orderAsString += anOrder.getTotalForOrder();

        return orderAsString;
    }

    private Order unmarshallOrder(String orderAsString, LocalDate dateOfOrder) {

        String[] tokens = orderAsString.split(DELIMITER);
        int orderNumber = Integer.parseInt(tokens[0]);

        String customerName = tokens[1];
        customerName = customerName.replace(DELIMITER_FOR_COMMA_REPLACEMENT, DELIMITER);

        String stateAbbreviation = tokens[2];
        BigDecimal taxRate = new BigDecimal(tokens[3]);
        String productTypeName = tokens[4];
        BigDecimal area = new BigDecimal(tokens[5]);
        BigDecimal costPerSquareFoot = new BigDecimal(tokens[6]);
        BigDecimal laborCostPerSquareFoot = new BigDecimal(tokens[7]);
        BigDecimal materialCostForOrder = new BigDecimal(tokens[8]);
        BigDecimal laborCostForOrder = new BigDecimal(tokens[9]);
        BigDecimal taxForOrder = new BigDecimal(tokens[10]);
        BigDecimal totalForOrder = new BigDecimal(tokens[11]);

        StateTaxInfo stateTaxInfo = new StateTaxInfo(stateAbbreviation, taxRate);
        ProductType productType = new ProductType(productTypeName, costPerSquareFoot, laborCostPerSquareFoot);
        Order order = new Order(dateOfOrder, customerName, stateTaxInfo, productType, area, orderNumber, materialCostForOrder, laborCostForOrder, taxForOrder, totalForOrder);

        return order;
    }

    private void loadOrderFiles() throws FlooringPersistenceException {

        File dir = new File(ORDER_FOLDER);
        File[] files = dir.listFiles();
//for (File file : dir.listFiles())

        for (File f : files) {

            HashMap<Integer, Order> orderMapByOrderNumber = new HashMap<>();

            LocalDate orderDate = LocalDate.parse(f.getName().substring(7, 15), DateTimeFormatter.ofPattern(DATE_PATTERN_FOR_FILE));

            if (f.isFile()) {

                Scanner scanner;

                try {
                    scanner = new Scanner(
                            new BufferedReader(
                                    new FileReader(f)));
                } catch (FileNotFoundException e) {
                    throw new FlooringPersistenceException("Could not load order files.");
                }

                String currentLine;
                Order currentOrderInfo;
                scanner.nextLine();//to skip header line

                while (scanner.hasNextLine()) {
                    currentLine = scanner.nextLine();
                    currentOrderInfo = unmarshallOrder(currentLine, orderDate);
                    orderMapByOrderNumber.put(currentOrderInfo.getOrderNumber(), currentOrderInfo);
                }
                scanner.close();

            }
            orderMapByDateThenOrderNum.put(orderDate, orderMapByOrderNumber);

        }
    }

    private void writeOrderFiles() throws FlooringPersistenceException {
        PrintWriter out;

        Set<LocalDate> setOfAllDates = orderMapByDateThenOrderNum.keySet();

        for (LocalDate date : setOfAllDates) {

            String dateAsString = date.format(DateTimeFormatter.ofPattern(DATE_PATTERN_FOR_FILE));

            ArrayList<Order> listOfOrders = new ArrayList<>(orderMapByDateThenOrderNum.get(date).values());

            if (!listOfOrders.isEmpty()) {

                try {
                    out = new PrintWriter(new FileWriter(".\\" + ORDER_FOLDER + "\\Orders_" + dateAsString + ".txt"));
                } catch (IOException e) {
                    throw new FlooringPersistenceException("Could not save order files.");
                }

                out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");

                String orderAsString;
                for (Order order : listOfOrders) {
                    orderAsString = marshallOrder(order);
                    out.println(orderAsString);
                    out.flush();
                }

                out.close();
            } else {
                File file = new File(".\\" + ORDER_FOLDER + "\\Orders_" + dateAsString + ".txt");
                file.delete();
            }
        }
    }
}
