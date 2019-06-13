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
        try {
            Account fromAccount = repository.get(from);
            Account toAccount = repository.get(to);
            executor.execute(fromAccount, toAccount, amount);
        } catch (Exception e) {
            throw new TransferFailureException(e);
        }
    }

}
