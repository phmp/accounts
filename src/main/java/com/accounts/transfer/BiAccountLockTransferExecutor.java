package com.accounts.transfer;

import com.accounts.model.Account;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.TreeSet;

@Slf4j
public class BiAccountLockTransferExecutor implements TransferExecutor{

    @Inject
    public BiAccountLockTransferExecutor() {
    }

    @Override
    public void execute(Account giver, Account taker, BigInteger amount) throws TransferFailureException {
        TreeSet<Account> accounts = sortAccountsInLockingOrder(giver, taker);
        log.info("from: {} to: {} money to transfer: {}, transfer STARTED", giver, taker, amount);
        synchronized (accounts.pollFirst()){
            synchronized (accounts.pollFirst()){
                checkIfGiverHasEnoughMoney(giver, amount);
                giver.subtractAmount(amount);
                taker.addAmount(amount);
            }
        }
        log.info("from: {} to: {} money to transfer: {}, transfer SUCCESS", giver, taker, amount);
    }

    //sorting accounts by ID to prevent dead lock
    private TreeSet<Account> sortAccountsInLockingOrder(Account giver, Account taker) {
        TreeSet<Account> accounts = new TreeSet<>(new ByIdAccountsComparator());
        accounts.add(giver);
        boolean added = accounts.add(taker);
        if (!added){
            throw new TransferFailureException("Self transactions are not allowed");
        }
        return accounts;
    }

    private void checkIfGiverHasEnoughMoney(Account giver, BigInteger amount) throws TransferFailureException {
        BigInteger giverAmount = giver.getAmount();
        if (giverAmount.compareTo(amount) < 0){
            throw new TransferFailureException("Account " + giver.getId() + " has no enough money. It has " + giverAmount + ", but " + amount + " is neeed.");
        }
    }
}
