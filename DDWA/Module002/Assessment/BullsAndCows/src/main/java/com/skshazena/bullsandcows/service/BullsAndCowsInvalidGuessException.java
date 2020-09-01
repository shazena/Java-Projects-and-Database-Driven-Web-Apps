package com.skshazena.bullsandcows.service;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Aug 24, 2020
 */
public class BullsAndCowsInvalidGuessException extends Exception {

    public BullsAndCowsInvalidGuessException(String message) {
        super(message);
    }

    public BullsAndCowsInvalidGuessException(String message, Throwable cause) {
        super(message, cause);
    }
}
