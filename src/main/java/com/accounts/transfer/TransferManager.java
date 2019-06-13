package com.accounts.transfer;

import com.accounts.model.Account;
import com.accounts.storage.AccountsRepository;
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

    public void transfer(String from, String to, BigInteger amount) {
        preventFromSelfTransaction(from, to);
        try {
            Account fromAccount = repository.getAccount(from);
            Account toAccount = repository.getAccount(to);
            executor.execute(fromAccount, toAccount, amount);
        } catch (Exception e) {
            throw new TransferFailureException(e);
        }
    }

    private void preventFromSelfTransaction(String from, String to) {
        if (from.equals(to)) {
            throw new TransferFailureException("Self-transfers are not allowed");
        }
    }
}
