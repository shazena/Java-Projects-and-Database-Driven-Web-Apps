package com.skshazena.vendingmachine.service;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 7, 2020
 */
public class VendingMachineNoSuchItemException extends Exception {

    public VendingMachineNoSuchItemException(String message) {
        super(message);
    }

    public VendingMachineNoSuchItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
