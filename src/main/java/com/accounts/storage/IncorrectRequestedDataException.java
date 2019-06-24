package com.accounts.storage;

public class IncorrectRequestedDataException extends RuntimeException {
    public IncorrectRequestedDataException(String message) {
        super(message);
    }
}
