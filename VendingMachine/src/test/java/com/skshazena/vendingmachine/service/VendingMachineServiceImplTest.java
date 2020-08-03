/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skshazena.vendingmachine.service;

import com.skshazena.vendingmachine.dao.VendingMachineAuditDao;
import com.skshazena.vendingmachine.dao.VendingMachineDao;
import com.skshazena.vendingmachine.dao.VendingMachinePersistenceException;
import com.skshazena.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Shazena
 */
public class VendingMachineServiceImplTest {

    private VendingMachineService service;

    public VendingMachineServiceImplTest() {
        VendingMachineDao dao = new VendingMachineDaoStubImpl();
        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();

        service = new VendingMachineServiceImpl(dao, auditDao);
    }

    @Test
    public void testAddValidItem() throws Exception {
        Item validItem = new Item("Water", BigDecimal.TEN.setScale(2), 10);
        try {
            service.addNewItemToInventory(validItem);
        } catch (VendingMachinePersistenceException
                | VendingMachineDataValidationException
                | VendingMachineDuplicateItemException e) {
            fail("Item was valid. Test should not fail.");
        }
    }

    @Test
    public void testAddDuplicateItem() throws Exception {
        Item sameItem = new Item("Twix", BigDecimal.ONE.setScale(2), 7);

        try {
            service.addNewItemToInventory(sameItem);
        } catch (VendingMachinePersistenceException
                | VendingMachineDataValidationException e) {
            fail("Incorrect exception was thrown");
        } catch (VendingMachineDuplicateItemException e) {
        }
    }

    @Test
    public void testAddInvalidItem() throws Exception {
        Item invalidItem = new Item("Twizzlers", null, 0);
        try {
            service.addNewItemToInventory(invalidItem);
        } catch (VendingMachinePersistenceException
                | VendingMachineDuplicateItemException e) {
            fail("Incorrect exception was thrown");
        } catch (VendingMachineDataValidationException e) {
        }
    }

    @Test
    public void testUpdateValidItem() throws Exception {
        String itemName = "Twix";

        Item updatedItem = new Item(itemName, BigDecimal.TEN.setScale(2), 11);
        try {
            service.updateInventory(updatedItem);
        } catch (VendingMachinePersistenceException
                | VendingMachineDataValidationException
                | VendingMachineNoInventoryException e) {
            fail("Item was valid, should not have failed. ");
        }

    }

    @Test
    public void testUpdateInvalidItem() throws Exception {
        Item invalidUpdatedItem = new Item("Twix", null, 11);
        try {
            service.updateInventory(invalidUpdatedItem);
        } catch (VendingMachinePersistenceException
                | VendingMachineNoInventoryException e) {
            fail("Item was valid, should not have failed. ");
        } catch (VendingMachineDataValidationException e) {
        }
    }

    @Test
    public void testUpdateNoSuchInventoryItem() throws Exception {
        Item noSuchUpdatedItem = new Item("Twizzlers", BigDecimal.TEN, 11);
        try {
            service.updateInventory(noSuchUpdatedItem);
        } catch (VendingMachinePersistenceException
                | VendingMachineDataValidationException e) {
            fail("Item was valid, should not have failed. ");
        } catch (VendingMachineNoInventoryException e) {
        }
    }

    @Test
    public void testRemoveItemFromInventory() throws Exception {
        String itemName = "Twix";
        Item theItem = new Item(itemName, BigDecimal.ONE.setScale(2), 7);
        try {
            service.removeItemFromInventory(itemName);
        } catch (VendingMachinePersistenceException
                | VendingMachineNoSuchItemException e) {
            fail("No Exception should have been caught.");
        }
    }

    @Test
    public void testRemoveInvalidItemFromInventory() throws Exception {
        String itemName = "Twix";
        Item theItem = new Item(itemName, BigDecimal.ONE.setScale(2), 7);
        try {
            service.removeItemFromInventory("Twizzlers");
        } catch (VendingMachinePersistenceException e) {
            fail("Incorrect exception caught");
        } catch (VendingMachineNoSuchItemException e) {
        }
    }

    @Test
    public void testGetAllItemsMaintenance() throws Exception {
        String item1Name = "Twix";
        Item theItem1 = new Item(item1Name, BigDecimal.ONE.setScale(2), 7);
        String item2Name = "Snickers";
        Item theItem2 = new Item(item2Name, BigDecimal.TEN.setScale(2), 0);

        List<Item> listOfItemsMaintenance = service.getAllItemsMaintenance();

        assertEquals(2, listOfItemsMaintenance.size(), "The list should contain 2 items");
        assertTrue(listOfItemsMaintenance.contains(theItem1), "The list should contain item 1");
        assertTrue(listOfItemsMaintenance.contains(theItem2), "The list should contain item 2");
    }

    @Test
    public void testGetNonZeroItemMaintenance() throws Exception {
        String itemName = "Twix";
        Item theItem = new Item(itemName, BigDecimal.ONE.setScale(2), 7);
        Item retrievedItemMaintenance = service.getItemMaintenance(itemName);
        assertEquals(theItem, retrievedItemMaintenance, "Items should be the same");
    }

    @Test
    public void testGetZeroQuantityItemMaintenance() throws Exception {
        String itemName = "Snickers";
        Item theItem = new Item(itemName, BigDecimal.TEN.setScale(2), 0);
        Item retrievedItemMaintenance = service.getItemMaintenance(itemName);
        assertEquals(theItem, retrievedItemMaintenance, "Items should be the same");
    }

    @Test
    public void testGetAllItemsPurchase() throws Exception {
        String item1Name = "Twix";
        Item theItem1 = new Item(item1Name, BigDecimal.ONE.setScale(2), 7);
        String item2Name = "Snickers";
        Item theItem2 = new Item(item2Name, BigDecimal.TEN.setScale(2), 0);

        List<Item> listOfItemsPurchase = service.getAllItemsPurchase();

        assertEquals(1, listOfItemsPurchase.size(), "The list should contain 2 items");
        assertTrue(listOfItemsPurchase.contains(theItem1), "The list should contain item 1");
        assertFalse(listOfItemsPurchase.contains(theItem2), "The list should contain item 2");
    }

    @Test
    public void testGetNonZeroItemPurchase() throws Exception {
        String itemName = "Twix";
        Item theItem = new Item(itemName, BigDecimal.ONE.setScale(2), 7);
        Item retrievedItemPurchase = service.getItemPurchase(itemName);
        assertEquals(theItem, retrievedItemPurchase, "Items should be the same");
    }

    @Test
    public void testGetZeroQuantityItemPurchase() throws Exception {
        String itemName = "Snickers";
        Item theItem = new Item(itemName, BigDecimal.TEN.setScale(2), 0);
        try {
            Item retrievedItemPurchase = service.getItemPurchase(itemName);
        } catch (VendingMachinePersistenceException
                | VendingMachineNoSuchItemException e) {
        }
    }

    @Test
    public void testPurchaseItemExactMoney() throws Exception {
        String itemName = "Twix";
        Item theItem = new Item(itemName, BigDecimal.ONE.setScale(2), 7);
        BigDecimal theMoney = BigDecimal.ONE.setScale(2);
        try {
            service.purchaseItem(itemName, theMoney);
        } catch (VendingMachinePersistenceException
                | VendingMachineDataValidationException
                | VendingMachineInsufficientFundsException
                | VendingMachineNoInventoryException e) {
            fail("No exceptions should have been caught.");
        }
    }

    @Test
    public void testPurchaseItemExtraMoney() throws Exception {
        String itemName = "Twix";
        Item theItem = new Item(itemName, BigDecimal.ONE.setScale(2), 7);
        BigDecimal theMoney = BigDecimal.valueOf(3).setScale(2);
        try {
            service.purchaseItem(itemName, theMoney);
        } catch (VendingMachinePersistenceException
                | VendingMachineDataValidationException
                | VendingMachineInsufficientFundsException
                | VendingMachineNoInventoryException e) {
            fail("No exceptions should have been caught.");
        }
    }

    @Test
    public void testPurchaseItemNotEnoughMoney() throws Exception {
        String itemName = "Twix";
        Item theItem = new Item(itemName, BigDecimal.ONE.setScale(2), 7);
        BigDecimal theMoney = BigDecimal.valueOf(0.5).setScale(2);
        try {
            service.purchaseItem(itemName, theMoney);
        } catch (VendingMachinePersistenceException
                | VendingMachineDataValidationException
                | VendingMachineNoInventoryException e) {
            fail("Incorrect Exception has been caught.");
        } catch (VendingMachineInsufficientFundsException e) {
        }
    }

    @Test
    public void testPurchaseItemZeroInventory() throws Exception {
        String itemName = "Snickers";
        Item theItem = new Item(itemName, BigDecimal.TEN.setScale(2), 0);
        BigDecimal theMoney = BigDecimal.valueOf(12).setScale(2);
        try {
            service.purchaseItem(itemName, theMoney);
        } catch (VendingMachinePersistenceException
                | VendingMachineInsufficientFundsException
                | VendingMachineDataValidationException e) {
            fail("Incorrect Exception has been caught.");
        } catch (VendingMachineNoInventoryException e) {
        }
    }

    @Test
    public void testPurchaseItemNotAnItemInTheMachine() throws Exception {
        String itemName = "Twizzlers";
        Item theItem = new Item(itemName, BigDecimal.TEN.setScale(2), 0);
        BigDecimal theMoney = BigDecimal.valueOf(12).setScale(2);
        try {
            service.purchaseItem(itemName, theMoney);
        } catch (VendingMachinePersistenceException
                | VendingMachineInsufficientFundsException e) {
            fail("Incorrect Exception has been caught.");
        } catch (VendingMachineDataValidationException
                | VendingMachineNoInventoryException e) {
        }
    }

    @Test
    public void testCalculateChange() throws Exception {
        String itemName = "Twix";
        Item theItem = new Item(itemName, BigDecimal.ONE.setScale(2), 7);
        BigDecimal theMoney = BigDecimal.valueOf(3.43);
        int[] changeInCoins = service.calculateChangeInCoins(itemName, theMoney);
        assertEquals(9, changeInCoins[0], "Number of Quarters should be 9");
        assertEquals(1, changeInCoins[1], "Number of Dimes should be 1");
        assertEquals(1, changeInCoins[2], "Number of Nickels should be 1");
        assertEquals(3, changeInCoins[3], "Number of Pennies should be 3");
    }
}
