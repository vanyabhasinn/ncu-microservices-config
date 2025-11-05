package com.ncu.library.borrowservice.exception;

public class DuplicateBorrowRecordException extends RuntimeException {
    public DuplicateBorrowRecordException(String message) {
        super(message);
    }
}
