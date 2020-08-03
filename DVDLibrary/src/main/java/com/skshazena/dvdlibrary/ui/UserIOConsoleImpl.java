package com.skshazena.dvdlibrary.ui;

import java.util.Scanner;

/**
 * UserIOConsoleImpl - Implementation of the UserIO Interface.
 *
 * @author Shazena May 14, 2020
 */
public class UserIOConsoleImpl implements UserIO {

    public Scanner inputReader = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        String result = inputReader.nextLine();
        return result;
    }

    @Override
    public int readInt(String prompt) {
        System.out.println(prompt);
        int result = Integer.parseInt(inputReader.nextLine());
        return result;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int result = 0;
        boolean isValid = false;
        do {
            try {
                System.out.println(prompt);
                result = Integer.parseInt(inputReader.nextLine());
                if (result >= min && result <= max) {
                    isValid = true;
                }
            } catch (NumberFormatException ex) {
                System.out.println("That was not a valid entry!");
            }
        } while (!isValid);

        return result;
    }

    @Override
    public double readDouble(String prompt) {
        System.out.println(prompt);
        double result = Double.parseDouble(inputReader.nextLine());
        return result;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        double result = 0;
        boolean isValid = false;
        do {
            try {
                System.out.println(prompt);
                result = Double.parseDouble(inputReader.nextLine());
                if (result >= min && result <= max) {
                    isValid = true;
                }
            } catch (NumberFormatException ex) {
                System.out.println("That was not a valid entry!");
            }
        } while (!isValid);

        return result;
    }

    @Override
    public float readFloat(String prompt) {
        System.out.println(prompt);
        float result = Float.parseFloat(inputReader.nextLine());
        return result;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        float result = 0;
        boolean isValid = false;
        do {
            try {
                System.out.println(prompt);
                result = Float.parseFloat(inputReader.nextLine());
                if (result >= min && result <= max) {
                    isValid = true;
                }
            } catch (NumberFormatException ex) {
                System.out.println("That was not a valid entry!");
            }
        } while (!isValid);
        return result;
    }

    @Override
    public long readLong(String prompt) {
        System.out.println(prompt);
        long result = Long.parseLong(inputReader.nextLine());
        return result;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        long result = 0;
        boolean isValid = false;
        do {
            try {
                System.out.println(prompt);
                result = Long.parseLong(inputReader.nextLine());
                if (result >= min && result <= max) {
                    isValid = true;
                }
            } catch (NumberFormatException ex) {
                System.out.println("That was not a valid entry!");
            }
        } while (!isValid);
        return result;
    }
}
