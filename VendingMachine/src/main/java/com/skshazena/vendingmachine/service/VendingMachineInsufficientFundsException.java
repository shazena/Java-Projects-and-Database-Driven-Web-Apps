package com.skshazena.vendingmachine.service;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 5, 2020
 */
public class VendingMachineInsufficientFundsException extends Exception {

    public VendingMachineInsufficientFundsException(String message) {
        super(message);
    }

    public VendingMachineInsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
