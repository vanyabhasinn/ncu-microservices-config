package com.ncu.library.authservice.exception;

/**
 * Thrown when login credentials are invalid.
 */
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
