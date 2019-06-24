package com.accounts.transfer.solutions.one;

import com.accounts.model.Account;
import com.accounts.storage.IncorrectRequestedDataException;
import com.accounts.transfer.TransferExecutor;
import com.accounts.transfer.TransferFailureException;
import com.accounts.transfer.validation.AccountValidator;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.TreeSet;

@Slf4j
public class TwoAccountsBlockingTransferExecutor implements TransferExecutor {

    private AccountValidator validator;

    @Inject
    public TwoAccountsBlockingTransferExecutor(AccountValidator validator) {
        this.validator = validator;
    }

    @Override
    public void execute(Account giver, Account taker, BigInteger amount) {
        TreeSet<Account> accounts = sortAccountsInLockingOrder(giver, taker);
        log.info("from: {} to: {} money to transfer: {}, transfer STARTED", giver, taker, amount);
        synchronized (accounts.pollFirst()){
            synchronized (accounts.pollFirst()){
                validator.checkIfGiverHasEnoughMoney(giver, amount);
                transfer(giver, taker, amount);
            }
        }
        log.info("from: {} to: {} money to transfer: {}, transfer SUCCESS", giver, taker, amount);
    }

    private void transfer(Account giver, Account taker, BigInteger amount) {
        BigInteger giverState = giver.getAmount();
        BigInteger takerState = taker.getAmount();
        try {
            giver.subtractAmount(amount);
            taker.addAmount(amount);
        }catch (Exception e){ //rollback changes
            giver.setAmount(giverState);
            taker.setAmount(takerState);
            throw new TransferFailureException("from: {} to: {} money to transfer: {}, transfer FAILED");
        }
    }

    /* sorting accounts by ID to prevent dead lock */
    private TreeSet<Account> sortAccountsInLockingOrder(Account giver, Account taker) {
        TreeSet<Account> accounts = new TreeSet<>(new ByIdAccountsComparator());
        boolean added1 = accounts.add(giver);
        boolean added2 = accounts.add(taker);
        boolean anyIsNotAdded = !added1 || !added2;
        if (anyIsNotAdded){
            throw new IncorrectRequestedDataException("Self transactions are not allowed");
        }
        return accounts;
    }

}
