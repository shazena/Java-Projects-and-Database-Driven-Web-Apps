package com.skshazena.bullsandcows.service;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Aug 24, 2020
 */
public class BullsAndCowsNoRoundsForGameException extends Exception {

    public BullsAndCowsNoRoundsForGameException(String message) {
        super(message);
    }

    public BullsAndCowsNoRoundsForGameException(String message, Throwable cause) {
        super(message, cause);
    }
}
