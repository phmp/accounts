package com.accounts.controllers;

import com.accounts.controllers.transfers.TransferFailureException;
import com.accounts.model.Account;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class AccountsRepository {

    private List<Account> accounts = new LinkedList<>();

    public AccountsRepository() {
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Account getAccount(String id) {
        Optional<Account> optional = accounts.stream()
                .filter(account -> account.getId().equals(id))
                .findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new TransferFailureException("Account " + id + " does not exist.");
    }

    public void addAccount(String id, BigInteger amount) {
        if (accounts.stream().anyMatch(account -> account.getId().equals(id))){
            throw new TransferFailureException("Account " + id + " already exists.");
        }
        accounts.add(new Account(id, amount));
    }

    public void addAccount(String id) {
        this.addAccount(id, BigInteger.ZERO);
    }

}
