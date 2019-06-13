package com.accounts.model;

import java.math.BigInteger;

public class Account {

    private final String id;
    private BigInteger amount;

    public Account(String id, BigInteger amount) {
        this.id = id;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void addAmount(BigInteger value) {
        amount = amount.add(value);
    }

    public void subtractAmount(BigInteger value) {
        amount = amount.subtract(value);
    }

    @Override
    public String toString() {
        return "{'" + id + "': " + amount + "$}";
    }
}
