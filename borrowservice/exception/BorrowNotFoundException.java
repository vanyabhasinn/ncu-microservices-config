package com.ncu.library.borrowservice.exception;

public class BorrowNotFoundException extends RuntimeException {
    public BorrowNotFoundException(String message) {
        super(message);
    }
}
