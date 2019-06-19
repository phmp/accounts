package com.accounts.model;

import java.math.BigInteger;
import java.util.Objects;

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

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public void subtractAmount(BigInteger value) {
        amount = amount.subtract(value);
    }

    @Override
    public String toString() {
        return "{'" + id + "': " + amount + "$}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (!Objects.equals(id, account.id)) return false;
        return Objects.equals(amount, account.amount);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }
}
