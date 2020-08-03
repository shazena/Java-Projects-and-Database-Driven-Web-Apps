package com.skshazena.vendingmachine.service;

import com.skshazena.vendingmachine.dao.VendingMachinePersistenceException;
import com.skshazena.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 * Vending Machine Service - As much validation as I could fit in here. I'm sure
 * there's more that could always be added...
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 5, 2020
 */
public interface VendingMachineService {

    /**
     * addNewItemToInventory - This method allows the Maintenance user to add an
     * item to the Vending Machine Inventory.
     *
     * @param newItem {Item}
     * @throws VendingMachinePersistenceException
     * @throws VendingMachineDuplicateItemException
     * @throws VendingMachineDataValidationException
     */
    void addNewItemToInventory(Item newItem) throws
            VendingMachinePersistenceException,
            VendingMachineDuplicateItemException,
            VendingMachineDataValidationException;

    /**
     * updateInventory - This method allows the Maintenance user to update an
     * item in the Vending Machine Inventory. The price can be changed or the
     * quantity or both.
     *
     * @param item {Item}
     * @throws VendingMachinePersistenceException
     * @throws VendingMachineNoInventoryException
     * @throws VendingMachineDataValidationException
     */
    void updateInventory(Item item) throws
            VendingMachinePersistenceException,
            VendingMachineNoInventoryException,
            VendingMachineDataValidationException;

    /**
     * removeItemFromInventory - This method allows the Maintenance user to
     * remove an item from the Vending Machine Inventory.
     *
     * @param itemName
     * @throws VendingMachinePersistenceException
     * @throws VendingMachineNoSuchItemException
     */
    void removeItemFromInventory(String itemName) throws
            VendingMachinePersistenceException,
            VendingMachineNoSuchItemException;

    /**
     * getAllItemsMaintenance - This method returns all items that are in the
     * inventory. It is a pass-through method for the dao.
     *
     * @return {List<Item>} List of all items in the inventory
     * @throws VendingMachinePersistenceException
     */
    List<Item> getAllItemsMaintenance() throws VendingMachinePersistenceException;

    /**
     * getItemMaintenance - This method returns the item requested by the
     * Maintenance user. It is a pass-through method for the dao.
     *
     * @param itemName {String}
     * @return {Item}
     * @throws VendingMachinePersistenceException
     * @throws VendingMachineNoSuchItemException
     */
    Item getItemMaintenance(String itemName) throws
            VendingMachinePersistenceException,
            VendingMachineNoSuchItemException;

    /**
     * getAllItemsPurchase - This method returns all items that re in the
     * inventory that have a quantity that is NON-ZERO. In other words, this
     * only returns items that are available for purchase.
     *
     * @return {List<Item>} List of all items in the inventory that have a
     * non-zero quantity
     * @throws VendingMachinePersistenceException
     */
    List<Item> getAllItemsPurchase() throws VendingMachinePersistenceException;

    /**
     * getItemPurchase - This method returns the item requested by the
     * Purchaser. It only returns the item if the item has a non-zero quantity.
     * If it does, it throws an error to prevent the user from trying to
     * purchase it.
     *
     * @param itemName {String}
     * @return {Item}
     * @throws VendingMachinePersistenceException
     * @throws VendingMachineNoSuchItemException
     */
    Item getItemPurchase(String itemName) throws
            VendingMachinePersistenceException,
            VendingMachineNoSuchItemException;

    /**
     * purchaseItem - This method first determines if the user gave enough money
     * to conduct the transaction and then updates the dao with the correct
     * quantity if that is the case. If the user did not provide enough money,
     * then it will throw an error to prevent the purchase. It will also throw
     * an error if the item is not available for purchase because it is out of
     * stock or does not exist in the machine.
     *
     * @param itemName
     * @param moneyGiven
     * @return
     * @throws VendingMachinePersistenceException
     * @throws VendingMachineInsufficientFundsException
     * @throws VendingMachineNoInventoryException
     * @throws VendingMachineDataValidationException
     */
    Item purchaseItem(String itemName, BigDecimal moneyGiven) throws
            VendingMachinePersistenceException,
            VendingMachineInsufficientFundsException,
            VendingMachineNoInventoryException,
            VendingMachineDataValidationException;

    /**
     * calculateChangeInCoins - This method returns an integer array that
     * contains the number of each coin that the user will be getting back in
     * change.
     *
     * @param itemName {String}
     * @param moneyGiven {BigDecimal}
     * @return
     * @throws VendingMachinePersistenceException
     */
    int[] calculateChangeInCoins(String itemName, BigDecimal moneyGiven) throws
            VendingMachinePersistenceException;
}
