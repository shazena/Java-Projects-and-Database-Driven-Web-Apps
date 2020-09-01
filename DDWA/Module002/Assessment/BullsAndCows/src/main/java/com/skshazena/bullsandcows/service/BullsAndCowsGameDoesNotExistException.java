package com.skshazena.bullsandcows.service;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Aug 24, 2020
 */
public class BullsAndCowsGameDoesNotExistException extends Exception {

    public BullsAndCowsGameDoesNotExistException(String message) {
        super(message);
    }

    public BullsAndCowsGameDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
