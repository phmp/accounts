package com.accounts.controllers.transfers;

import com.accounts.model.Account;

import java.math.BigInteger;

public interface TransferExecutor {

    void transfer(Account from, Account to, BigInteger amount) throws TransferFailureException;
}
