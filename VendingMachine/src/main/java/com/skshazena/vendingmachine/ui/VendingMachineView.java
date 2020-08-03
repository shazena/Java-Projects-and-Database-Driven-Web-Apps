package com.skshazena.vendingmachine.ui;

import com.skshazena.vendingmachine.dto.*;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 5, 2020
 */
public class VendingMachineView {

    private UserIO io;

    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    ////////////////////////////////////////////////////////////////////////////
    //WELCOME AND MAIN MENU SECTION
    ////////////////////////////////////////////////////////////////////////////
    public void welcomeMessage() {
        io.print("____________________________________________________");
        io.print("|						   |");
        io.print("|						   |");
        io.print("|		      WELCOME			   |");
        io.print("|						   |");
        io.print("|___________________________________________________|");
    }

    public void displayMainMenuBanner() {
        io.print("------MAIN MENU------");
    }

    public int displayMainMenuAndGetSelection() {
        io.print("Press 1 to Purchase");
        io.print("Press 2 to Maintain");
        io.print("Press 3 to Exit");
        return io.readInt("Enter your choice", 1, 3);
    }

    ////////////////////////////////////////////////////////////////////////////
    // PURCHASE SECTION
    ////////////////////////////////////////////////////////////////////////////
    public void displayAllInventoryPurchase(List<Item> listOfItems) {
        io.print("------Inventory------");
        listOfItems.stream().forEach((item) -> displayOneItemPurchase(item));
        io.print("");
    }

    public void displayOneItemPurchase(Item anItem) {
        io.print(anItem.getName() + "---$" + anItem.getPrice());
    }

    public int displayPurchaseMenuAndGetSelection() {
        io.print("------Purchase Mode------");
        io.print("Press 1 to Enter Money to Purchase.");
        io.print("Press 2 for Inventory.");
        io.print("Press 3 for Main Menu.");
        return io.readInt("Enter your choice ", 1, 3);
    }

    public BigDecimal getMoney() {
        double money = io.readDouble("Enter amount of money");
        return BigDecimal.valueOf(money);
    }

    public String getItemName() {
        return io.readString("Enter the Item Name");
    }

    public void displaySuccessfulPurchaseInfo(Item purchasedItem, int[] changeInCoins) throws InterruptedException {
        io.print(purchasedItem.getName() + " has been successfully purchased.");

        //check to see if there is any change
        boolean anyChange = false;
        for (int i = 0; i < changeInCoins.length; i++) {
            if (changeInCoins[i] > 0) {
                anyChange = true;
            }
        }

        Coins[] coinNames = Coins.values();

        //if there is change, then display it, if not, it would skip this section
        if (anyChange) {

            io.print("Dispensing Change...");

            for (int i = 0; i < coinNames.length; i++) {
                io.print(coinNames[i] + " - " + changeInCoins[i]);
                Thread.sleep(800);
            }
        }

        io.print("Thank you! Come again soon!");
        Thread.sleep(800);
    }

    ////////////////////////////////////////////////////////////////////////////
    // MAINTENANCE SECTION
    ////////////////////////////////////////////////////////////////////////////
    public void displayMaintenanceModeBanner() {
        io.print("------Maintenance Mode------");
    }

    public String getMaintenancePassword() {
        return io.readString("Enter the Maintenance Password");
    }

    public void displayMaintenanceIncorrectPasswordBanner() {
        io.print("Incorrect Password. ");
        io.readString("Press Enter to go back to Main Menu");
    }

    public int displayMaintenanceMenuAndGetSelection() {
        io.print("Press 1 to Update an Item.");
        io.print("Press 2 to Add a new Item.");
        io.print("Press 3 to Remove an Item");
        io.print("Press 4 to View Current Inventory.");
        io.print("Press 5 to Exit to the Main Menu.");

        return io.readInt("Enter your choice ", 1, 5);
    }

    public void displayAllInventoryMaintenance(List<Item> listOfItems) {
        io.print("------Inventory------");
        listOfItems.stream().forEach((item) -> displayOneItemMaintenance(item));
        io.print("");
    }

    public void displayOneItemMaintenance(Item anItem) {
        io.print(anItem.getName() + "---$" + anItem.getPrice()
                + "---QTY:" + anItem.getQuanity());
    }

    public Item getUpdatedItemInfo(Item itemToUpdate) {
        io.print("Fields are optional, press enter to skip one");
        String newPriceString = io.readString("Please enter the new price for " + itemToUpdate.getName());
        if (!newPriceString.isBlank()) { //if the string is not blank

            BigDecimal newPriceBD = new BigDecimal(newPriceString); //convert it to a BigDecimal

            if (newPriceBD.compareTo(BigDecimal.ZERO) == 1) { //if it is greater then zero, then change the price

                itemToUpdate.setPrice(newPriceBD);
            }
        }
        String newQuantityString = io.readString("Please enter the quantity change. Positive number for add,"
                + " negative number for remove");

        if (!newQuantityString.isBlank()) { //if the string is not blank

            int newQuantity = Integer.parseInt(newQuantityString); //turn it into an integer

            newQuantity = itemToUpdate.getQuanity() + newQuantity; //calculate the updated quantity

            itemToUpdate.setQuanity(newQuantity);//set the new quantity
        }

        return itemToUpdate;
    }

    public void displayMaintenanceSuccessBanner(String itemName, String whatHappened) {
        //The String whatHappened can be "added", "updated", or "removed"
        //to make this method multi-purpose
        io.print(itemName + " has successfully been " + whatHappened);
        io.readString("Press Enter to continue");
    }

    public Item getNewItemInfo(String newItemName) {
        String priceString;
        do {
            priceString = io.readString("Please enter the price for " + newItemName);
        } while (priceString.isBlank() || priceString.equals("0") || priceString.equals("0.00") || priceString.equals("0.0"));
        //don't allow user to enter a price of zero or no price at all

        BigDecimal priceBD = new BigDecimal(priceString);

        String quantityString;
        do {
            quantityString = io.readString("Please enter the quantity for " + newItemName);
        } while (quantityString.isBlank() || quantityString.equals("0"));
        //don't allow user to enter nothing or a quantity of zero.

        int quantity = Integer.parseInt(quantityString);

        Item itemToAdd = new Item(newItemName, priceBD, quantity);

        return itemToAdd;
    }

    ////////////////////////////////////////////////////////////////////////////
    // EXIT AND ERROR SECTION
    ////////////////////////////////////////////////////////////////////////////
    public void displayExitBanner() {
        io.print("Thank you! Come again soon!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("------ERROR------");
        io.print(errorMsg);
    }

}
