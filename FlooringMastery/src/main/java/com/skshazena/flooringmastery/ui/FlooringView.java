package com.skshazena.flooringmastery.ui;

import com.skshazena.flooringmastery.dto.Order;
import com.skshazena.flooringmastery.dto.ProductType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 28, 2020
 */
public class FlooringView {

    private UserIO io;
    private DateTimeFormatter patternForUser = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    public FlooringView(UserIO io) {
        this.io = io;
    }

    public void welcomeMessage() {
        io.print(" * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("\t<<Flooring Program - Mastery Project by Shazena Khan>>");
        io.print(" * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }

    public int displayMainMenuAndGetSelection() {
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export all Data");
        io.print("6. Quit");
        return io.readInt("Enter your selection", 1, 6);
    }

    public LocalDate getDateOfOrderFromUser() {
        return io.readLocalDateFromIntegers("Enter the date ");
    }

    public void displayDisplayOrdersBanner() {
        io.print("====== DISPLAY LIST OF ORDERS =====");
    }
    public void displayAddBanner() {
        io.print("====== ADD AN ORDER =====");
    }

    public Order getNewOrderInfo(List<ProductType> listOfProducts) {
        io.print("---Add An Order---");
        LocalDate dateOfOrder = io.readLocalDateFromIntegers("Enter the date ");
        String customerName = io.readStringLoopIfBlank("Enter the customer name");
        String stateName = io.readStringLettersAndSpacesOnlyLoopIfBlank("Enter the name of the state");

        io.print("Product Type Options listed below.");
        displayAllProducts(listOfProducts);
        String productTypeName = io.readStringLettersAndSpacesOnlyLoopIfBlank("Enter the product type");

        BigDecimal area = io.readBigDecimalLoop("Enter square footage to purchase");

        return new Order(dateOfOrder, customerName, stateName, productTypeName, area);
    }

    public String getUserApprovalOfOrder() {
        return io.readStringSingleCharacterYN("Confirm Order? (Y/N)");
    }

    public int getOrderNumberFromUser() {
        return io.readInt("Enter the order number");
    }

    public void displayEditBanner() {
        io.print("====== EDIT AN ORDER =====");
    }

    public Order getEditedOrderInfo(Order orderToEdit, List<ProductType> listOfProducts) {
        io.print("---Edit An Order---");
        io.print("All fields are optional. Press Enter to skip a field.");

        String newCustomerName = io.readString("Enter customer name (" + orderToEdit.getCustomerName() + ")");

        String newStateName = io.readString("Enter state name (" + orderToEdit.getTaxInfo().getStateName() + ")");

        io.print("Product Type Options listed below.");
        displayAllProducts(listOfProducts);
        String newProductTypeName = io.readString("Enter the product type (" + orderToEdit.getProductType().getProductType() + ").");

        BigDecimal newArea = io.readBigDecimalOptional("Enter the area (" + orderToEdit.getArea() + ")");

        return new Order(orderToEdit.getDateOfOrder(), newCustomerName, newStateName, newProductTypeName, newArea);
        //This returns empty strings for the first three and null for the last if the fields are not filled in.
    }

    public void displayAllProducts(List<ProductType> listOfProducts) {
        listOfProducts.stream().forEach(
                (product) -> io.print(product.getProductType()
                        + "---Cost Per SqFt: $" + product.getCostPerSquareFoot()
                        + "--- Labor Cost Per SqFt: $" + product.getLaborCostPerSquareFoot()));
    }

    public void displaySuccessOrDiscardBanner(String whatHappened, boolean wasOrderSaved) {
        if (wasOrderSaved) {
            io.print("The order has been successfully " + whatHappened);
        } else {
            io.print("No changes have been made.");
        }
        io.readString("Press Enter to Continue");
    }

    public void displaySuccessfulExport() {
        io.print("Orders have been successfully exported");
        io.readString("Press Enter to Continue");
    }

    public void displayDetailsOfOneOrder(Order anOrder) {

        io.print("Here are the details of the Order");

        io.print("#" + anOrder.getOrderNumber() + "\t" + anOrder.getCustomerName()
                + "\t" + anOrder.getDateOfOrder().format(patternForUser)
                + "\t Area: " + anOrder.getArea() + " SqFt\tProduct: "
                + anOrder.getProductType().getProductType() + "\tState: " + anOrder.getTaxInfo().getStateAbbreviation());

        io.print("\tMaterial Cost: $" + anOrder.getMaterialCostForOrder() + "\tLabor Cost: $"
                + anOrder.getLaborCostForOrder() + "\tTax: $" + anOrder.getTaxForOrder());

        io.print("\t---Total: $" + anOrder.getTotalForOrder() + "---");

    }

    public void displayRemoveBanner() {
        io.print("====== REMOVE AN ORDER =====");
    }

    public void displayExportBanner() {
        io.print("====== EXPORT ALL ORDERS =====");
    }

    public void displayOrders(List<Order> listOfOrdersByDate, LocalDate date) {
        //note to self, should be in a table format
        //one order per line preferably
        io.print("---All Orders For " + date.format(patternForUser) + "---");
        listOfOrdersByDate.stream().forEach((order) -> {
            io.print("#" + order.getOrderNumber() + "\t" + order.getCustomerName() + "\t---Total: $" + order.getTotalForOrder() + "---");
            io.print("\tArea: " + order.getArea() + " SqFt\tProduct: "
                    + order.getProductType().getProductType() + "\t\tState: " + order.getTaxInfo().getStateAbbreviation());
            io.print("\tMaterial Cost: $" + order.getMaterialCostForOrder() + "\tLabor Cost: $"
                    + order.getLaborCostForOrder() + "\tTax: $" + order.getTaxForOrder());
            io.print("");
        });
        io.readString("Press Enter to Continue");
    }

    public void displayExitBanner() {
        io.print("Thank you for placing an order with TSG Corp!");
        io.print("Come again soon!");
    }

    public void displayErrorMessage(String errorMessage) {
        io.print("------ERROR------");
        io.print(errorMessage);
        io.readString("Press Enter to Continue");
    }

}
