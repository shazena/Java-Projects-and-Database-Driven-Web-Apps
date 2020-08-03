package com.skshazena.vendingmachine.service;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 5, 2020
 */
public class VendingMachineNoInventoryException extends Exception {

    public VendingMachineNoInventoryException(String message) {
        super(message);
    }

    public VendingMachineNoInventoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
