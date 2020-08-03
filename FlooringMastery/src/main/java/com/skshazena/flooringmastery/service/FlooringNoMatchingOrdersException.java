package com.skshazena.flooringmastery.service;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Jul 10, 2020
 */
public class FlooringNoMatchingOrdersException extends Exception {

    public FlooringNoMatchingOrdersException(String message) {
        super(message);
    }

    public FlooringNoMatchingOrdersException(String message, Throwable cause) {
        super(message, cause);
    }
}
