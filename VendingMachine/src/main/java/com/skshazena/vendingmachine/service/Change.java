package com.skshazena.vendingmachine.service;

import com.skshazena.vendingmachine.dto.Coins;

/**
 * Change class - This class helps the service calculate change. It contains
 * getters for each coin and uses the values of the enum in the calculations.
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 6, 2020
 */
public class Change {

    private int quarters;
    private int dimes;
    private int nickels;
    private int pennies;

    private int calculateQuarters(int totalChange) {
        this.quarters = totalChange / (Coins.QUARTERS.getValue());
        return totalChange % Coins.QUARTERS.getValue();
    }

    private int calculateDimes(int totalChange) {
        this.dimes = totalChange / (Coins.DIMES.getValue());
        return totalChange % Coins.DIMES.getValue();
    }

    private int calculateNickels(int totalChange) {
        this.nickels = totalChange / (Coins.NICKELS.getValue());
        return totalChange % Coins.NICKELS.getValue();
    }

    public void calculateChange(int change) {
        change = calculateQuarters(change);
        change = calculateDimes(change);
        change = calculateNickels(change);
        this.pennies = change;
    }

    public int getQuarters() {
        return quarters;
    }

    public int getDimes() {
        return dimes;
    }

    public int getNickels() {
        return nickels;
    }

    public int getPennies() {
        return pennies;
    }
}
