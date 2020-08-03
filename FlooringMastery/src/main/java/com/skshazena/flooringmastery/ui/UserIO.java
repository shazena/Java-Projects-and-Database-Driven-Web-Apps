package com.skshazena.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 28, 2020
 */
public interface UserIO {

    void print(String message);

    String readString(String prompt);

    String readStringLoopIfBlank(String prompt);

    String readStringLettersAndSpacesOnlyLoopIfBlank(String prompt);

    String readStringSingleCharacterYN(String prompt);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    double readDouble(String prompt);

    double readDouble(String prompt, double min, double max);

    float readFloat(String prompt);

    float readFloat(String prompt, float min, float max);

    long readLong(String prompt);

    long readLong(String prompt, long min, long max);

    LocalDate readLocalDateFromIntegers(String prompt);

    LocalDate readLocalDateWithPattern(String prompt, String pattern);

    LocalDate readLocalDate(String prompt, String pattern, LocalDate min);

    LocalDate readLocalDate(String prompt, String pattern, LocalDate min, LocalDate max);

    BigDecimal readBigDecimalOptional(String prompt);

    BigDecimal readBigDecimalLoop(String prompt);

    BigDecimal readBigDecimal(String prompt, BigDecimal min);

    BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max);
}
