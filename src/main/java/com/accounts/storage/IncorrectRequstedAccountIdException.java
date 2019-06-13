package com.accounts.storage;

public class IncorrectRequstedAccountIdException extends RuntimeException {
    public IncorrectRequstedAccountIdException(String message) {
        super(message);
    }
}
