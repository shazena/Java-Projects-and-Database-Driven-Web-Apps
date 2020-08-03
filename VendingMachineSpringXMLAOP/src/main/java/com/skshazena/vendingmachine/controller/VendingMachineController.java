package com.skshazena.vendingmachine.controller;

import com.skshazena.vendingmachine.dao.VendingMachinePersistenceException;
import com.skshazena.vendingmachine.dto.Item;
import com.skshazena.vendingmachine.service.*;
import com.skshazena.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.util.List;

/**
 * Vending Machine Controller
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 5, 2020
 */
public class VendingMachineController {

    private VendingMachineService service;
    private VendingMachineView view;

    public VendingMachineController(VendingMachineService service, VendingMachineView view) {
        this.service = service;
        this.view = view;
    }

    public void run() throws InterruptedException {
        boolean keepGoing = true;
        int menuSelection = 0;

        try {
            welcomeMessage();
            while (keepGoing) {
                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        enterPurchaseMode();
                        break;
                    case 2:
                        enterMaintenanceMode();
                        break;
                    default:
                        keepGoing = false;
                        break;
                }
            }
            exitMessage();
        } catch (VendingMachinePersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }

    }

    private void welcomeMessage() throws VendingMachinePersistenceException {
        view.welcomeMessage();
        List<Item> allItems = service.getAllItemsPurchase();
        view.displayAllInventoryPurchase(allItems);
    }

    private int getMenuSelection() {
        view.displayMainMenuBanner();
        return view.displayMainMenuAndGetSelection();
    }

    ////////////////////////////////////////////////////////////////////////////
    //PURCHASE MODE
    ////////////////////////////////////////////////////////////////////////////
    private void enterPurchaseMode() throws InterruptedException {
        OUTER:
        while (true) {
            int subMenuChoice = view.displayPurchaseMenuAndGetSelection();

            switch (subMenuChoice) {
                case 1:
                    collectMoneyAndPurchaseItem();
                    break;
                case 2:
                    getAndDisplayInventoryPurchase();
                    break;
                default:
                    break OUTER;
                    //could also be boolean value to end loop
            }
        }
    }

    private void collectMoneyAndPurchaseItem() throws InterruptedException {

        try {
            BigDecimal moneyCollected = view.getMoney(); //collect money from user
            String itemNameToPurchase = view.getItemName(); //collect item name to purchase
            Item itemToPurchase = service.getItemPurchase(itemNameToPurchase);//validate if item is able to be purchased
            Item purchasedItem = service.purchaseItem(itemNameToPurchase, moneyCollected);//if so, then conduct the purchase
            int[] changeInCoins = service.calculateChangeInCoins(itemNameToPurchase, moneyCollected);//calculate the change
            view.displaySuccessfulPurchaseInfo(purchasedItem, changeInCoins);//send item and change to view to display to user

        } catch (VendingMachinePersistenceException
                | VendingMachineInsufficientFundsException
                | VendingMachineNoInventoryException
                | VendingMachineNoSuchItemException
                | VendingMachineDataValidationException
                | NumberFormatException ex) {
            view.displayErrorMessage(ex.getMessage());
        }

    }

    private void getAndDisplayInventoryPurchase() {
        try {
            List<Item> allItems = service.getAllItemsPurchase();
            view.displayAllInventoryPurchase(allItems);
        } catch (VendingMachinePersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //MAINTENANCE MODE
    ////////////////////////////////////////////////////////////////////////////
    private void enterMaintenanceMode() {
        view.displayMaintenanceModeBanner();
        String maintenancePassword = view.getMaintenancePassword();
        if (maintenancePassword.equals("Unicorn")) {
            OUTER:
            while (true) {
                int subMenuChoice = view.displayMaintenanceMenuAndGetSelection();

                switch (subMenuChoice) {
                    case 1:
                        collectInfoAndUpdateItem();
                        break;
                    case 2:
                        collectInfoAndAddNewItem();
                        break;
                    case 3:
                        collectInfoAndRemoveItem();
                        break;
                    case 4:
                        getAndDisplayInventoryMaintenance();
                        break;
                    default:
                        break OUTER;
                }
            }
        } else {
            view.displayMaintenanceIncorrectPasswordBanner();
        }
    }

    private void collectInfoAndUpdateItem() {
        try {
            String itemName = view.getItemName();//get name of item from user
            Item itemToUpdate = service.getItemMaintenance(itemName);//validate item to see if it can be updated
            if (itemToUpdate != null) { //if it is valid, then...
                Item updatedItem = view.getUpdatedItemInfo(itemToUpdate); //collect updated info from user
                service.updateInventory(updatedItem);//update the item
                view.displayMaintenanceSuccessBanner(updatedItem.getName(), "updated");
            } else {
                throw new VendingMachineNoSuchItemException(itemName + " is not in the vending machine.");
            }
        } catch (VendingMachinePersistenceException
                | VendingMachineNoSuchItemException
                | VendingMachineNoInventoryException
                | VendingMachineDataValidationException
                | NumberFormatException ex) {
            view.displayErrorMessage(ex.getMessage());
        }
    }

    private void collectInfoAndAddNewItem() {
        try {
            String newItemName = view.getItemName();//get item name
            service.getItemMaintenance(newItemName);//make sure it isn't already in the system
            Item newItem = view.getNewItemInfo(newItemName);//collect new info
            service.addNewItemToInventory(newItem);//add it to the system
            view.displayMaintenanceSuccessBanner(newItem.getName(), "added");
        } catch (VendingMachinePersistenceException
                | VendingMachineNoSuchItemException
                | VendingMachineDuplicateItemException
                | VendingMachineDataValidationException
                | NumberFormatException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void collectInfoAndRemoveItem() {
        try {
            String itemName = view.getItemName();//get item name
            service.removeItemFromInventory(itemName);//remove from the system
            view.displayMaintenanceSuccessBanner(itemName, "removed");
        } catch (VendingMachinePersistenceException
                | VendingMachineNoSuchItemException ex) {
            view.displayErrorMessage(ex.getMessage());
        }
    }

    private void getAndDisplayInventoryMaintenance() {
        try {
            List<Item> allItems = service.getAllItemsMaintenance();
            view.displayAllInventoryMaintenance(allItems);
        } catch (VendingMachinePersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    //EXIT
    ////////////////////////////////////////////////////////////////////////////

    private void exitMessage() {
        view.displayExitBanner();
    }
}
