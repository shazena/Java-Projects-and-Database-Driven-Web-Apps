package com.skshazena.vendingmachine.dao;

import com.skshazena.vendingmachine.dto.Item;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * Vending Machine Dao File Implementation
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 5, 2020
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao {

    private Map<String, Item> inventory = new HashMap<>();
    private final String INVENTORY_FILE;
    public static final String DELIMETER = "::";

    public VendingMachineDaoFileImpl() {
        INVENTORY_FILE = "inventory.txt";
    }

    public VendingMachineDaoFileImpl(String INVENTORY_FILE) {
        this.INVENTORY_FILE = INVENTORY_FILE;
    }

    @Override
    public Item addItem(String itemName, Item item) throws VendingMachinePersistenceException {
        loadInventory();
        Item newItem = inventory.put(itemName, item);
        writeInventory();
        return newItem;
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        loadInventory();
        return new ArrayList<>(inventory.values());
    }

    @Override
    public Item getItem(String itemName) throws VendingMachinePersistenceException {
        loadInventory();
        return inventory.get(itemName);
    }

    @Override
    public Item removeItem(String itemName) throws VendingMachinePersistenceException {
        loadInventory();
        Item removedItem = inventory.remove(itemName);
        writeInventory();
        return removedItem;
    }

    private Item unmarshallItem(String itemAsString) {
        String[] itemTokens = itemAsString.split(DELIMETER);
        String itemName = itemTokens[0];
        BigDecimal itemPrice = new BigDecimal(itemTokens[1]);
        int itemQuantity = Integer.parseInt(itemTokens[2]);
        Item itemFromFile = new Item(itemName, itemPrice, itemQuantity);
        return itemFromFile;
    }

    private String marshallItem(Item anItem) {
        String itemAsString = anItem.getName() + DELIMETER;
        itemAsString += anItem.getPrice() + DELIMETER;
        itemAsString += anItem.getQuanity();
        return itemAsString;
    }

    private void loadInventory() throws VendingMachinePersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(INVENTORY_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException("Could not load inventory file.");
        }

        String currentLine;
        Item currentItem;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentItem = unmarshallItem(currentLine);
            inventory.put(currentItem.getName(), currentItem);
        }
        scanner.close();
    }

    private void writeInventory() throws VendingMachinePersistenceException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException("Could not save Inventory file");
        }
//        String itemAsString;
        List<Item> itemList = new ArrayList<>(inventory.values());
//        for (Item item : itemList) {
//            itemAsString = marshallItem(item);
//            out.println(itemAsString);
//            out.flush();
//        }
        itemList.stream().forEach(
                (item) -> {
                    String itemAsString = marshallItem(item);
                    out.println(itemAsString);
                    out.flush();
                });
        out.close();
    }
}
