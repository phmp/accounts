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
        log.info("from: " + giver + " to: " + taker + " money to transfer; " + amount + " transfer STARTED");
        synchronized (accounts.pollFirst()){
            synchronized (accounts.pollFirst()){
                checkIfGiverHasEnoughMoney(giver, amount);
                giver.subtractAmount(amount);
                taker.addAmount(amount);
            }
        }
        log.info("from: " + giver + " to: " + taker + " money to transfer; " + amount + " SUCCESS");
    }

    //sorting accounts by ID to prevent dead lock
    private TreeSet<Account> sortAccountsInLockingOrder(Account giver, Account taker) {
        TreeSet<Account> accounts = new TreeSet<>(new ByIdAccountsComparator());
        accounts.add(giver);
        accounts.add(taker);
        if (accounts.size() != 2){
            throw new TransferFailureException("Self transactions are not allowed");
        }
        return accounts;
    }

    private void checkIfGiverHasEnoughMoney(Account giver, BigInteger amount) throws TransferFailureException {
        BigInteger giverAmount = giver.getAmount();
        if (giverAmount.compareTo(amount) == -1){
            throw new TransferFailureException("Account " + giver.getId() + " has no enough money. It has " + giverAmount + ", but " + amount + " is neeed.");
        }
    }
}
