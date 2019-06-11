package com.accounts.controllers.transfers;

public class TransferFailureException extends RuntimeException {

    public TransferFailureException(String message) {
        super(message);
    }
}
