package com.accounts.controllers;

import com.accounts.controllers.transfers.TransferExecutor;
import com.accounts.model.Account;
import com.google.inject.Inject;

import java.math.BigInteger;

public class TransferManager {

    private AccountsRepository repository;
    private TransferExecutor executor;

    @Inject
    public TransferManager(AccountsRepository repository, TransferExecutor excecutor) {
        this.repository = repository;
        this.executor = excecutor;
    }

    public void transfer(String from, String to, BigInteger amount){
        Account fromAccount = repository.getAccount(from);
        Account toAccount = repository.getAccount(to);
        executor.transfer(fromAccount, toAccount, amount);
    }
}
