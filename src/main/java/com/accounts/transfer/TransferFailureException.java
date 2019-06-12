package com.accounts.transfer;

import com.accounts.storage.AccountRepositoryException;

public class TransferFailureException extends RuntimeException {

    public TransferFailureException(String message) {
        super(message);
    }

    public TransferFailureException(Exception e) {
        super(e.getMessage(), e);
    }
}
