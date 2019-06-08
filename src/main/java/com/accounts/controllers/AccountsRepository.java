package com.accounts.controllers;

import com.accounts.model.Account;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

public class AccountsRepository {

    private List<Account> accounts = new LinkedList<>();

    public AccountsRepository() {}

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(String id, BigInteger amount){
        accounts.add(new Account(id, amount));
    }

    public void addAccount(String id){
        accounts.add(new Account(id));
    }

}
