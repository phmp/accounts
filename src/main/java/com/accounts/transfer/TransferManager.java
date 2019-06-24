package com.accounts.transfer;

import com.accounts.model.Account;
import com.accounts.storage.AccountsRepository;
import com.accounts.transfer.validation.TransferRequestValidator;
import com.google.inject.Inject;

import java.math.BigInteger;

public class TransferManager {

    private AccountsRepository repository;
    private TransferExecutor executor;
    private TransferRequestValidator validator;

    @Inject
    public TransferManager(AccountsRepository repository, TransferExecutor executor, TransferRequestValidator validator) {
        this.repository = repository;
        this.executor = executor;
        this.validator = validator;
    }

    public void transfer(String from, String to, BigInteger amount) {
        validator.validate(from, to, amount);
        Account fromAccount = repository.get(from);
        Account toAccount = repository.get(to);
        executor.execute(fromAccount, toAccount, amount);
    }

}
