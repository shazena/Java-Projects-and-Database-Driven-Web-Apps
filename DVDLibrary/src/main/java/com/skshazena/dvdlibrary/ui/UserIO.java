package com.skshazena.dvdlibrary.ui;

/**
 * UserIO Interface - How can the console interact with the user. What can it
 * show the user. What can it take in from the user.
 *
 * @author Shazena May 14, 2020
 */
public interface UserIO {

    void print(String message);

    String readString(String prompt);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    double readDouble(String prompt);

    double readDouble(String prompt, double min, double max);

    float readFloat(String prompt);

    float readFloat(String prompt, float min, float max);

    long readLong(String prompt);

    long readLong(String prompt, long min, long max);
}
