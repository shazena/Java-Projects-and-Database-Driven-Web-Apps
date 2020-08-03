package com.skshazena.flooringmastery.service;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Jul 5, 2020
 */
public class FlooringNoSuchOrderException extends Exception {

    public FlooringNoSuchOrderException(String message) {
        super(message);
    }

    public FlooringNoSuchOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
