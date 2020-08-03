package com.skshazena.vendingmachine.dao;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 5, 2020
 */
public class VendingMachinePersistenceException extends Exception {

    public VendingMachinePersistenceException(String message) {
        super(message);
    }

    public VendingMachinePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
