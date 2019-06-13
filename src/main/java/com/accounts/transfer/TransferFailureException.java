package com.accounts.transfer;

public class TransferFailureException extends RuntimeException {

    public TransferFailureException(String message) {
        super(message);
    }

    public TransferFailureException(Exception e) {
        super(e.getMessage(), e);
    }
}
