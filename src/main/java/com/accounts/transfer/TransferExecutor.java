package com.accounts.transfer;

import com.accounts.model.Account;

import java.math.BigInteger;

public interface TransferExecutor {

    void execute(Account from, Account to, BigInteger amount) throws TransferFailureException;
}
