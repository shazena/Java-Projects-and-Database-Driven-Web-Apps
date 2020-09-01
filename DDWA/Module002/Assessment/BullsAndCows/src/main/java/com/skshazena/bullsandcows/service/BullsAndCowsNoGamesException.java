package com.skshazena.bullsandcows.service;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Aug 24, 2020
 */
public class BullsAndCowsNoGamesException extends Exception {

    public BullsAndCowsNoGamesException(String message) {
        super(message);
    }

    public BullsAndCowsNoGamesException(String message, Throwable cause) {
        super(message, cause);
    }
}
