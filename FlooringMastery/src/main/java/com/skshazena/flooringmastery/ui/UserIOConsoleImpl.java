package com.skshazena.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 28, 2020
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
    public String readStringLoopIfBlank(String prompt) {
        boolean isValid = false;
        String result = null;
        do {
            System.out.println(prompt);
            result = inputReader.nextLine();
            if (!result.isBlank()) {
                isValid = true;
            }
        } while (!isValid);
        return result;
    }

    @Override
    public String readStringLettersAndSpacesOnlyLoopIfBlank(String prompt) {
        boolean isValid = false;
        String result = null;
        do {
            System.out.println(prompt);
            result = inputReader.nextLine();
            if (!result.isBlank() && result.matches("[a-zA-Z\\s]*")) {
                isValid = true;
            }
        } while (!isValid);
        return result;
    }

    @Override
    public String readStringSingleCharacterYN(String prompt) {
        boolean isValid = false;
        String result = null;
        do {
            try {
                System.out.println(prompt);
                result = inputReader.nextLine();
                if (result.equalsIgnoreCase("y") || result.equalsIgnoreCase("n")) {
                    isValid = true;
                }
            } catch (Exception e) {
                System.out.println("That was not a valid entry!");
            }
        } while (!isValid);
        return result.toUpperCase();
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

    @Override
    public LocalDate readLocalDateFromIntegers(String prompt) {
        boolean isValid = false;
        int month = 0, date = 0, year = 0;
        LocalDate fullDate = null;
        do {
            try {
                System.out.println(prompt);
                System.out.print("Enter the numerical month: ");
                month = Integer.parseInt(inputReader.nextLine());
                System.out.print("Enter the date: ");
                date = Integer.parseInt(inputReader.nextLine());
                System.out.print("Enter the year (4 digits): ");
                year = Integer.parseInt(inputReader.nextLine());
                fullDate = LocalDate.of(year, month, date);
                isValid = true;
            } catch (NumberFormatException
                    | DateTimeException e) {
                System.out.println("That was not a valid entry");
            }
        } while (!isValid);

        return fullDate;
    }

    @Override
    public LocalDate readLocalDateWithPattern(String prompt, String pattern) {
        LocalDate result = null;
        boolean isValid = false;
        do {
            try {
                System.out.println(prompt + " in the format " + pattern);
                result = LocalDate.parse(inputReader.nextLine(), DateTimeFormatter.ofPattern(pattern));
                isValid = true;
            } catch (DateTimeParseException e) {
                System.out.println("That was not a valid entry!");
            }
        } while (!isValid);

        return result;
    }

    @Override
    public LocalDate readLocalDate(String prompt, String pattern, LocalDate min) {
        LocalDate result = null;
        boolean isValid = false;
        do {
            try {
                System.out.println(prompt + " in the format " + pattern);
                result = LocalDate.parse(inputReader.nextLine(), DateTimeFormatter.ofPattern(pattern));
                if (result.isAfter(min)) {
                    isValid = true;
                }
            } catch (DateTimeParseException ex) {
                System.out.println("That was not a valid entry!");
            }
        } while (!isValid);
        return result;
    }

    @Override
    public LocalDate readLocalDate(String prompt, String pattern, LocalDate min, LocalDate max) {
        LocalDate result = null;
        boolean isValid = false;
        do {
            try {
                System.out.println(prompt + " in the format " + pattern);
                result = LocalDate.parse(inputReader.nextLine(), DateTimeFormatter.ofPattern(pattern));
                if (result.isAfter(min) && result.isBefore(max)) {
                    isValid = true;
                }
            } catch (DateTimeParseException ex) {
                System.out.println("That was not a valid entry!");
            }
        } while (!isValid);
        return result;
    }

    @Override
    public BigDecimal readBigDecimalOptional(String prompt) {
        //needs to allow blank entries as well
        BigDecimal result = null;
        String numAsString = null;
        System.out.println(prompt);

        try {
            numAsString = inputReader.nextLine();
            if (numAsString.isBlank()) {
                return null;
            } else {
                result = new BigDecimal(numAsString);
            }
        } catch (NumberFormatException e) {
            System.out.println("That was not a valid entry!");
        }
        return result;

    }

    @Override
    public BigDecimal readBigDecimalLoop(String prompt
    ) {
        BigDecimal result = null;
        boolean isValid = false;
        do {
            System.out.println(prompt);
            try {
                result = new BigDecimal(inputReader.nextLine());
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("That was not a valid entry!");
            }
        } while (!isValid);
        return result;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal min
    ) {
        BigDecimal result = null;
        boolean isValid = false;
        do {
            try {
                System.out.println(prompt);
                result = new BigDecimal(inputReader.nextLine());
                if (result.compareTo(min) >= 0) {
                    isValid = true;
                }
            } catch (NumberFormatException ex) {
                System.out.println("That was not a valid entry!");
            }
        } while (!isValid);
        return result;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal min,
            BigDecimal max
    ) {
        BigDecimal result = null;
        boolean isValid = false;
        do {
            try {
                System.out.println(prompt);
                result = new BigDecimal(inputReader.nextLine());
                if (result.compareTo(min) >= 0 && result.compareTo(max) <= 0) {
                    isValid = true;
                }
            } catch (NumberFormatException ex) {
                System.out.println("That was not a valid entry!");
            }
        } while (!isValid);
        return result;
    }

}
