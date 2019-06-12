package com.accounts.storage;

import com.accounts.model.Account;
import com.accounts.transfer.TransferFailureException;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AccountsRepository {

    private Map<String, Account> accounts = new ConcurrentHashMap<>();

    public AccountsRepository() {
    }

    public Collection<Account> getAccounts() {
        return accounts.values();
    }

    public Account getAccount(String id) {
        Account account = accounts.get(id);
        if (account == null) {
            throw new AccountRepositoryException("Account " + id + " does not exist.");
        } else {
            return account;
        }
    }

    public void addAccount(String id, BigInteger amount) {
        Account old = accounts.putIfAbsent(id, new Account(id, amount));
        if (old != null) {
            throw new AccountRepositoryException("Account " + id + " already exists.");
        }
    }

    public void addAccount(String id) {
        this.addAccount(id, BigInteger.ZERO);
    }

}
