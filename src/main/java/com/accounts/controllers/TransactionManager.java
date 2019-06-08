package com.accounts.controllers;

import com.google.inject.Inject;

import java.math.BigInteger;

/**
 * Created by Paweł on 2019-06-08.
 */
public class TransactionManager {

    private AccountsRepository repository;

    public AccountsRepository getRepository() {
        return repository;
    }

    @Inject
    public TransactionManager(AccountsRepository repository) {
        this.repository = repository;
    }

    public void transfer(String from, String to, BigInteger amount){

    }
}
