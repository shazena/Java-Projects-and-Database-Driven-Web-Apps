package com.skshazena.vendingmachine.service;

import com.skshazena.vendingmachine.dao.VendingMachineDao;
import com.skshazena.vendingmachine.dao.VendingMachinePersistenceException;
import com.skshazena.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 10, 2020
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao {

    public Item firstItem;
    public Item secondItem;

    public VendingMachineDaoStubImpl() {
        firstItem = new Item("Twix", BigDecimal.ONE.setScale(2), 7);
        secondItem = new Item("Snickers", BigDecimal.TEN.setScale(2), 0);
    }

    public VendingMachineDaoStubImpl(Item testItem) {
        this.firstItem = testItem;
    }

    @Override
    public Item addItem(String itemName, Item item) throws VendingMachinePersistenceException {
        if (itemName.equals(firstItem.getName())) {
            return firstItem;
        } else if (itemName.equals(secondItem.getName())) {
            return secondItem;
        } else {
            return null;
        }
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        List<Item> listOfItems = new ArrayList<Item>();
        listOfItems.add(firstItem);
        listOfItems.add(secondItem);
        return listOfItems;
    }

    @Override
    public Item getItem(String itemName) throws VendingMachinePersistenceException {
        if (itemName.equals(firstItem.getName())) {
            return firstItem;
        } else if (itemName.equals(secondItem.getName())) {
            return secondItem;
        } else {
            return null;
        }
    }

    @Override
    public Item removeItem(String itemName) throws VendingMachinePersistenceException {
        if (itemName.equals(firstItem.getName())) {
            return firstItem;
        } else if (itemName.equals(secondItem.getName())) {
            return secondItem;
        } else {
            return null;
        }
    }

}
