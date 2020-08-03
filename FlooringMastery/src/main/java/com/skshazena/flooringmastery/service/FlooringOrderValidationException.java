package com.skshazena.flooringmastery.service;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Jul 5, 2020
 */
public class FlooringOrderValidationException extends Exception {

    public FlooringOrderValidationException() {
    }

    public FlooringOrderValidationException(String message) {
        super(message);
    }

    public FlooringOrderValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
