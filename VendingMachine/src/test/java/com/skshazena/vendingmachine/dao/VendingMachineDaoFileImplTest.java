/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skshazena.vendingmachine.dao;

import com.skshazena.vendingmachine.dto.Item;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Shazena
 */
public class VendingMachineDaoFileImplTest {

    VendingMachineDaoFileImpl testDao;

    public VendingMachineDaoFileImplTest() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        String testFile = "testInventory.txt";
        new FileWriter(testFile);
        testDao = new VendingMachineDaoFileImpl(testFile);
    }

    @Test
    public void testAddItem() throws Exception {
        String item1Name = "Twix";
        Item item1 = new Item(item1Name, BigDecimal.ONE.setScale(2), 10);
        Item addedItem = testDao.addItem(item1Name, item1);
        assertNull(addedItem, "The added item should be null if successfully added");
        Item retrievedItem = testDao.getItem(item1Name);
        assertEquals(item1, retrievedItem, "Items should be the same");
        assertEquals(item1.getName(), retrievedItem.getName(), "Names should be the same");
        assertEquals(item1.getPrice(), retrievedItem.getPrice(), "Prices should be the same");
        assertEquals(item1.getQuanity(), retrievedItem.getQuanity(), "Quantities should be the same");
    }

    @Test
    public void testAddGet2Items() throws Exception {
        String item1Name = "Twix";
        Item item1 = new Item(item1Name, BigDecimal.ONE.setScale(2), 10);
        Item addedItem1 = testDao.addItem(item1Name, item1);
        assertNull(addedItem1, "The added item should be null if successfully added");

        String item2Name = "Snickers";
        Item item2 = new Item(item2Name, BigDecimal.TEN.setScale(2), 12);
        Item addedItem2 = testDao.addItem(item2Name, item2);
        assertNull(addedItem2, "The added item should be null if successfully added");

        Item retrievedItem1 = testDao.getItem(item1Name);
        assertEquals(item1, retrievedItem1, "The retrieved item should be the same as the first item.");
        Item retrievedItem2 = testDao.getItem(item2Name);
        assertEquals(item2, retrievedItem2, "The retrieved item should be the same as the second item.");
    }

    @Test
    public void testAddGetAllItems() throws Exception {
        String item1Name = "Twix";
        Item item1 = new Item(item1Name, BigDecimal.ONE.setScale(2), 10);
        Item addedItem1 = testDao.addItem(item1Name, item1);
        assertNull(addedItem1, "The added item should be null if successfully added");

        String item2Name = "Snickers";
        Item item2 = new Item(item2Name, BigDecimal.TEN.setScale(2), 12);
        Item addedItem2 = testDao.addItem(item2Name, item2);
        assertNull(addedItem2, "The added item should be null if successfully added");

        List<Item> listOfItems = testDao.getAllItems();

        assertNotNull(listOfItems, "List should not be null");
        assertEquals(listOfItems.size(), 2, "The list should contain 2 items");
        assertTrue(listOfItems.contains(item1), "List should contain first item");
        assertTrue(listOfItems.contains(item2), "List should contain second item");
    }

    @Test
    public void testRemoveItems() throws Exception {
        String item1Name = "Twix";
        Item item1 = new Item(item1Name, BigDecimal.ONE.setScale(2), 10);
        Item addedItem1 = testDao.addItem(item1Name, item1);
        assertNull(addedItem1, "The added item should be null if successfully added");

        String item2Name = "Snickers";
        Item item2 = new Item(item2Name, BigDecimal.TEN.setScale(2), 12);
        Item addedItem2 = testDao.addItem(item2Name, item2);
        assertNull(addedItem2, "The added item should be null if successfully added");

        List<Item> listOfItems = testDao.getAllItems();

        assertNotNull(listOfItems, "List should not be null");
        assertEquals(listOfItems.size(), 2, "The list should contain 2 items");
        assertTrue(listOfItems.contains(item1), "List should contain Item 1");
        assertTrue(listOfItems.contains(item2), "List should contain Item 2");

        Item removedItem = testDao.removeItem(item1Name);

        assertEquals(item1, removedItem, "Removed Item should be the same as Item 1");

        listOfItems = testDao.getAllItems();
        assertEquals(listOfItems.size(), 1, "List should only contain one item");
        assertFalse(listOfItems.contains(item1), "List should not contain item 1");
        assertTrue(listOfItems.contains(item2), "List should contain item 2");

        removedItem = testDao.removeItem(item2Name);
        listOfItems = testDao.getAllItems();
        assertEquals(listOfItems.size(), 0, "List should only contain no items");
        assertFalse(listOfItems.contains(item2), "List should not contain item 1");
        assertEquals(item2, removedItem, "Removed Item should be the same as Item 2");

        Item retrievedItem = testDao.getItem(item1Name);
        assertNull(retrievedItem, "Item 1 should be null");
        retrievedItem = testDao.getItem(item2Name);
        assertNull(retrievedItem, "Item 2 should be null");

    }
}
