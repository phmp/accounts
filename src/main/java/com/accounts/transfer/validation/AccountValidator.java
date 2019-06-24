package com.accounts.transfer.validation;

import com.accounts.model.Account;
import com.accounts.transfer.TransferFailureException;

import java.math.BigInteger;

public class AccountValidator {

    public void checkIfGiverHasEnoughMoney(Account giver, BigInteger amount){
        BigInteger giverAmount = giver.getAmount();
        if (giverAmount.compareTo(amount) < 0){
            throw new TransferFailureException("Account " + giver.getId() + " has no enough money. It has " + giverAmount + ", but " + amount + " is neeed.");
        }
    }
}
