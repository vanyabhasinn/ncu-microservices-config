package com.ncu.library.borrowservice.exception;

public class DatabaseNotAvailableException extends RuntimeException {
    public DatabaseNotAvailableException(String message) {
        super(message);
    }
}
