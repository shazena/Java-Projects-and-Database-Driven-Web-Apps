
package com.skshazena.bullsandcows.service;

/**
 *
 * @author Shazena Khan 
 * 
Date Created: Aug 27, 2020
 */
public class BullsAndCowsGameIsAlreadyFinishedException extends Exception {

    public BullsAndCowsGameIsAlreadyFinishedException(String message) {
        super(message);
    }

    public BullsAndCowsGameIsAlreadyFinishedException(String message, Throwable cause) {
        super(message, cause);
    }
}
