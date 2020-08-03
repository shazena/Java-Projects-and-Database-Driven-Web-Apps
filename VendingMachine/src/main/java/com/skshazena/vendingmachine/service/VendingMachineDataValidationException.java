package com.skshazena.vendingmachine.service;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 6, 2020
 */
public class VendingMachineDataValidationException extends Exception {

    public VendingMachineDataValidationException(String message) {
        super(message);
    }

    public VendingMachineDataValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
