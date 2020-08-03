package com.skshazena.vendingmachine.dao;

import com.skshazena.vendingmachine.dto.Item;
import java.util.List;

/**
 * Vending Machine Dao Interface
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 5, 2020
 */
public interface VendingMachineDao {

    /**
     * addItem - This method adds an item to the Vending Machine Inventory file
     *
     * @param itemName {String}
     * @param item {Item}
     * @return {Item} - if there is already an item with that name. Returns null
     * if Item was successfully added to the inventory.
     * @throws VendingMachinePersistenceException
     */
    Item addItem(String itemName, Item item)
            throws VendingMachinePersistenceException;

    /**
     * getAllItems - This method returns a list of all the Items in the Vending
     * Machine.
     *
     * @return {List<Item>} List of all Items
     * @throws VendingMachinePersistenceException
     */
    List<Item> getAllItems()
            throws VendingMachinePersistenceException;

    /**
     * getItem - This method gets a single Item from the inventory map
     *
     * @param itemName {String} the item name
     * @return {Item} the matching item
     * @throws VendingMachinePersistenceException
     */
    Item getItem(String itemName)
            throws VendingMachinePersistenceException;

    /**
     * removeItem - This method removes an item from the inventory
     *
     * @param itemName {String} the item name
     * @return {Item} the removed item
     * @throws VendingMachinePersistenceException
     */
    Item removeItem(String itemName)
            throws VendingMachinePersistenceException;

}
