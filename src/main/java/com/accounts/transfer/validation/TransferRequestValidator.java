package com.accounts.transfer.validation;

import com.accounts.storage.IncorrectRequestedDataException;
import com.google.inject.Inject;

import java.math.BigInteger;

public class TransferRequestValidator {

    @Inject
    public TransferRequestValidator() {
    }

    public void validate(String from, String to, BigInteger amount){
        preventFromSelfTransaction(from, to);
        preventFromNegativeAmount(amount);
    }

    private void preventFromNegativeAmount(BigInteger amount) {
        if (amount.compareTo(BigInteger.ZERO) < 0 ) {
            throw new IncorrectRequestedDataException("Transfers with negative amount are not allowed");
        }
    }


    private void preventFromSelfTransaction(String from, String to) {
        if (from.equals(to)) {
            throw new IncorrectRequestedDataException("Self-transfers are not allowed");
        }
    }
}
