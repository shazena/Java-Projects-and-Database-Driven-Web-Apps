package com.skshazena.flooringmastery.dao;

/**
 * Flooring Persistence Exception - This exception is thrown when there is an
 * error saving to or loading from files.
 *
 * @author Shazena Khan
 *
 * Date Created: Jul 5, 2020
 */
public class FlooringPersistenceException extends Exception {

    public FlooringPersistenceException(String message) {
        super(message);
    }

    public FlooringPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
