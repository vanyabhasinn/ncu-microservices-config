package com.ncu.library.authservice.exception;

/**
 * Thrown when a required database entry is not found.
 */
public class DBNotFoundException extends RuntimeException {

    public DBNotFoundException(String message) {
        super(message);
    }
}
