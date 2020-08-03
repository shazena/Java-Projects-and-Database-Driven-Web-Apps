package com.skshazena.vendingmachine;

import com.skshazena.vendingmachine.controller.VendingMachineController;
import com.skshazena.vendingmachine.dao.*;
import com.skshazena.vendingmachine.service.*;
import com.skshazena.vendingmachine.ui.*;

/**
 * Vending Machine App - simulates purchasing an item from a vending machine and
 * gives out change. Maintenance feature also added where, if the password is
 * correct, you can add a new item to the machine, update an item, and remove an
 * item.
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 5, 2020 Last Edit: June 11, 2020
 */
public class App {

    public static void main(String[] args) throws InterruptedException {
        UserIO io = new UserIOConsoleImpl();
        VendingMachineView view = new VendingMachineView(io);
        VendingMachineDao dao = new VendingMachineDaoFileImpl();
        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoImpl();
        VendingMachineService service = new VendingMachineServiceImpl(dao, auditDao);
        VendingMachineController controller = new VendingMachineController(service, view);

        controller.run();
    }
}
//view.welcomeMessage();
//        dao.addItem("Snickers", new Item("Snickers", BigDecimal.ONE, 12));
//        dao.addItem("Twix", new Item("Twix", BigDecimal.valueOf(2), 10));
//
//        List<Item> allItems = service.getAllItemsPurchase();
//        for (Item item : allItems) {
//            System.out.println(item.toString());
//        }
//        System.out.println("");
//
//        Item itemToUpdate = new Item("Snickers", BigDecimal.ONE, 15);
//        service.updateInventory(itemToUpdate);
//
//        itemToUpdate = new Item("Twix", BigDecimal.ONE, 21);
//        service.updateInventory(itemToUpdate);
//
//        allItems = service.getAllItemsPurchase();
//        for (Item item : allItems) {
//            System.out.println(item.toString());
//        }
//        System.out.println("");
//
//        Item newItem = new Item("Ritz", BigDecimal.valueOf(1.35), 10);
//        service.addNewItemToInventory(newItem);
//        newItem = new Item("Honey Bun", BigDecimal.valueOf(2.00), 14);
//        service.addNewItemToInventory(newItem);
//        newItem = new Item("Chips", BigDecimal.valueOf(1.00), 20);
//        service.addNewItemToInventory(newItem);
//
//        allItems = service.getAllItemsPurchase();
//        for (Item item : allItems) {
//            System.out.println(item.toString());
//        }
//        System.out.println("");
//
//        dao.removeItem("Ritz");//works
//        dao.removeItem("Honey Bun");//works
//        //dao.removeItem("Chips");//works
//
//        Item updatedItem = new Item("Chips", BigDecimal.ONE, 12);
//        service.updateInventory(updatedItem);
////        updatedItem = new Item("Cake", BigDecimal.ONE, 12);
////        service.updateInventory(updatedItem);
//
//        dao.addItem("Sneakers", new Item("Sneakers", BigDecimal.ONE, 0));//works
//
//        allItems = dao.getAllItemsPurchase();
//        for (Item item : allItems) {
//            System.out.println(item.toString());
//        }
//        System.out.println("end of dao retrieval");
//
//        allItems = service.getAllItemsPurchase();
//        for (Item item : allItems) {
//            System.out.println(item.toString());
//        }
//        System.out.println("");
//
//        BigDecimal moneyCollected = BigDecimal.valueOf(3.34);
//        System.out.println(service.purchaseItem("Snickers", moneyCollected));
//        int[] calculateChangeInCoins = service.calculateChangeInCoins("Snickers", moneyCollected);
//        for (int calculateChangeInCoin : calculateChangeInCoins) {
//            System.out.println(calculateChangeInCoin);
//        }
//
//        moneyCollected = BigDecimal.ONE;
//        System.out.println(service.purchaseItem("Snickers", moneyCollected));

//        moneyCollected = BigDecimal.valueOf(0.35);
//        System.out.println(service.purchaseItem("Snickers", moneyCollected));
//        System.out.println(service.purchaseItem("Snickers", moneyCollected));
/*
TESTS
Dao methods
add item - works
getallitems - works
getitem - works
remove item = works
reading, writing from file = all works

Service methods
add new item to inventory - works
add new item to inventory, when something is already there - works
update inventory, item not there - works
update inventory - works
get all items - works
get all items, excluding zero - works
purchase item, enough money - works
purchase item, more money - works
purchase item, not enough money - works
calculate change in coins - 

 */
