package com.skshazena.vendingmachine.service;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 6, 2020
 */
public class VendingMachineDuplicateItemException extends Exception {

    public VendingMachineDuplicateItemException(String message) {
        super(message);
    }

    public VendingMachineDuplicateItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
