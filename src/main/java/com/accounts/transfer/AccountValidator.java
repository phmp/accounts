package com.accounts.transfer;

import com.accounts.model.Account;

import java.math.BigInteger;

public class AccountValidator {

    public void checkIfGiverHasEnoughMoney(Account giver, BigInteger amount) throws TransferFailureException{
        BigInteger giverAmount = giver.getAmount();
        if (giverAmount.compareTo(amount) < 0){
            throw new TransferFailureException("Account " + giver.getId() + " has no enough money. It has " + giverAmount + ", but " + amount + " is neeed.");
        }
    }
}
