package com.accounts.transfer.solutions.one;

import com.accounts.model.Account;

import java.util.Comparator;

public class ByIdAccountsComparator implements Comparator<Account> {

    @Override
    public int compare(Account o1, Account o2) {
        return o1.getId().compareTo(o2.getId());
    }
}
