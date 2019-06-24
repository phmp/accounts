package com.accounts.storage;

import com.accounts.model.Account;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccountsRepository {

    private Map<String, Account> accounts = new ConcurrentHashMap<>();

    public AccountsRepository() {
    }

    public Collection<Account> getAll() {
        return accounts.values();
    }

    public Account get(String id) {
        Account account = accounts.get(id);
        if (account == null) {
            throw new IncorrectRequestedDataException("Account " + id + " does not exist.");
        } else {
            return account;
        }
    }

    public void add(String id, BigInteger amount) {
        Account old = accounts.putIfAbsent(id, new Account(id, amount));
        if (old != null) {
            throw new IncorrectRequestedDataException("Account " + id + " already exists.");
        }
    }
}
