package com.accounts.model;

import java.math.BigInteger;

public class Account {

    private final String id;
    private BigInteger amount;

    public Account(String id, BigInteger amount) {
        this.id = id;
        this.amount = amount;
    }

    public Account(String id) {
        this(id, BigInteger.ZERO);
    }

    public String getId() {
        return id;
    }

    public BigInteger getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "{ " + id + " : " + amount + "$ }";
    }
}
