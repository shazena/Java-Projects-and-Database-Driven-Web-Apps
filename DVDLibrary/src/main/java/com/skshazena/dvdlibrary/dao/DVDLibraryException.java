package com.skshazena.dvdlibrary.dao;

/**
 * Custom Exceptions made for DVD Library
 *
 * @author Shazena May 14, 2020
 */
public class DVDLibraryException extends Exception {

    public DVDLibraryException(String message) {
        super(message);
    }

    public DVDLibraryException(String message, Throwable cause) {
        super(message, cause);
    }
}
