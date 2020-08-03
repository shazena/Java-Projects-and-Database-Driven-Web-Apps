package com.skshazena.vendingmachine.dto;

/**
 * Coins Enum with names and Integer values
 *
 * @author Shazena <\br>
 * Date Created: Jun 5, 2020
 */
public enum Coins {
    QUARTERS(25), DIMES(10), NICKELS(5), PENNIES(1);

    private int value;

    private Coins(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
