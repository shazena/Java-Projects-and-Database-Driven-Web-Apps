package com.skshazena.vendingmachine.service;

import com.skshazena.vendingmachine.dao.*;
import com.skshazena.vendingmachine.dto.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 5, 2020
 */
public class VendingMachineServiceImpl implements VendingMachineService {

    private VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;

    public VendingMachineServiceImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    @Override
    public void addNewItemToInventory(Item newItem) throws
            VendingMachinePersistenceException,
            VendingMachineDuplicateItemException,
            VendingMachineDataValidationException {

        Item newItemToAdd = dao.getItem(newItem.getName());

        if (newItemToAdd != null) {
            throw new VendingMachineDuplicateItemException(newItem.getName() + " is already "
                    + "in the vending machine. Currenty quantity: "
                    + newItemToAdd.getQuanity());
        }

        validateItem(newItem);

        dao.addItem(newItem.getName(), newItem);
        auditDao.writeAuditEntry("New Item, " + newItem.getName() + ", Qty: "
                + newItem.getQuanity() + ", has been added.");

    }

    @Override
    public void updateInventory(Item item) throws
            VendingMachinePersistenceException,
            VendingMachineNoInventoryException,
            VendingMachineDataValidationException {

        Item itemToUpdate = dao.getItem(item.getName());

        if (itemToUpdate == null) {
            throw new VendingMachineNoInventoryException(item.getName() + " is not in the vending machine.");
        }

        validateItem(item);

        dao.addItem(item.getName(), item);

        //all lines below here pertain to the audit dao
        String priceUpdate;
        if (itemToUpdate.getPrice().equals(item.getPrice())) {
            priceUpdate = "No Change in Price. ";
        } else {
            priceUpdate = " Old Price: $" + itemToUpdate.getPrice()
                    + ". New Price: $" + item.getPrice() + " ";
        }
        String quantityUpdate;
        if (itemToUpdate.getQuanity() == item.getQuanity()) {
            quantityUpdate = "No Change in Quantity. ";
        } else {
            quantityUpdate = " Old Quantity: " + itemToUpdate.getQuanity()
                    + " New Quantity: " + item.getQuanity() + " ";
        }

        auditDao.writeAuditEntry(item.getName() + " has been updated. "
                + priceUpdate + quantityUpdate);

    }

    @Override
    public void removeItemFromInventory(String itemName) throws
            VendingMachinePersistenceException,
            VendingMachineNoSuchItemException {
        if (dao.getItem(itemName) != null) {
            dao.removeItem(itemName);
            auditDao.writeAuditEntry(itemName + " has been removed from the vending machine.");
        } else {
            throw new VendingMachineNoSuchItemException(itemName + " is not an "
                    + "item in the Vending Machine.");
        }

    }

    @Override
    public List<Item> getAllItemsMaintenance() throws VendingMachinePersistenceException {
        List<Item> completeInventory = dao.getAllItems().stream()
                .collect(Collectors.toList());
        return completeInventory;
    }

    @Override
    public Item getItemMaintenance(String itemName) throws
            VendingMachinePersistenceException,
            VendingMachineNoSuchItemException {
        Item itemToCheck = dao.getItem(itemName);
        return itemToCheck;
    }

    @Override
    public List<Item> getAllItemsPurchase() throws VendingMachinePersistenceException {
        List<Item> nonZeroInventory = dao.getAllItems().stream()
                .filter((item) -> item.getQuanity() > 0)
                .collect(Collectors.toList());
        return nonZeroInventory;
    }

    @Override
    public Item getItemPurchase(String itemName) throws
            VendingMachinePersistenceException,
            VendingMachineNoSuchItemException {
        Item itemToCheck = dao.getItem(itemName);
//TODO this section might not be needed, but it needs investigating (up to the first else)
        if (itemToCheck == null) {
            throw new VendingMachineNoSuchItemException(itemName + " is not an "
                    + "item in the vending machine.");
        } else if (itemToCheck.getQuanity() > 0 && itemToCheck != null) {
            return itemToCheck;
        } else {
            throw new VendingMachineNoSuchItemException(itemName + " is not an "
                    + "item in the vending machine.");
        }
    }

    @Override
    public Item purchaseItem(String itemName, BigDecimal moneyGiven) throws
            VendingMachinePersistenceException,
            VendingMachineInsufficientFundsException,
            VendingMachineNoInventoryException,
            VendingMachineDataValidationException {

        Item purchasingItem = dao.getItem(itemName);

        validateItem(purchasingItem);

        BigDecimal price = purchasingItem.getPrice();
        if (moneyGiven.compareTo(price) == -1) {
            throw new VendingMachineInsufficientFundsException("Not enough money"
                    + " provided. $" + moneyGiven.setScale(2) + " was given. Still need $"
                    + (price.subtract(moneyGiven)));
        }

        if (purchasingItem.getQuanity() > 0) {
            int quanity = dao.getItem(itemName).getQuanity();
            purchasingItem.setQuanity(quanity - 1);
        } else {
            throw new VendingMachineNoInventoryException("Sorry that item is out of stock");
        }

        dao.addItem(purchasingItem.getName(), purchasingItem);

        auditDao.writeAuditEntry(itemName + " was purchased. Qty remaining: " + dao.getItem(itemName).getQuanity());

        return purchasingItem;
    }

    @Override
    public int[] calculateChangeInCoins(String itemName, BigDecimal moneyGiven) throws VendingMachinePersistenceException {
        Change changeGiver = new Change();
        BigDecimal itemPrice = dao.getItem(itemName).getPrice();
        BigDecimal change = moneyGiven.subtract(itemPrice);
        int totalChangeInPennies = change.multiply(BigDecimal.valueOf(100)).intValue();

        changeGiver.calculateChange(totalChangeInPennies);

        int numOfQuarters = changeGiver.getQuarters();
        int numOfDimes = changeGiver.getDimes();
        int numOfNickels = changeGiver.getNickels();
        int numOfPennies = changeGiver.getPennies();
        int[] changeInCoins = {numOfQuarters, numOfDimes, numOfNickels, numOfPennies};

        return changeInCoins;
    }

    private void validateItem(Item anItem) throws VendingMachineDataValidationException {
        if (anItem == null
                || anItem.getName() == null
                || anItem.getName().trim().length() == 0
                || anItem.getPrice() == null
                || anItem.getPrice().equals(BigDecimal.ZERO) //TODO this is not actually working I think, idk
                //                || anItem.getQuanity() == 0
                ) {
            throw new VendingMachineDataValidationException("ERROR: All fields are required and need to be non-zero");
        }
    }

}
