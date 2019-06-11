package com.accounts.configuration;

import com.accounts.controllers.AccountsRepository;
import com.accounts.controllers.transfers.BiAccountLockTransferExecutor;
import com.accounts.controllers.transfers.TransferExecutor;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import java.math.BigInteger;

/**
 * Created by Paweł on 2019-06-08.
 */
public class ConfigurationModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TransferExecutor.class).to(BiAccountLockTransferExecutor.class);
    }

    @Provides
    @Singleton
    public AccountsRepository provideRepository() {
        AccountsRepository accountsRepository = new AccountsRepository();
        accountsRepository.addAccount("A1", new BigInteger("100"));
        accountsRepository.addAccount("A2", new BigInteger("100"));
        accountsRepository.addAccount("A3", new BigInteger("100"));
        accountsRepository.addAccount("A4", new BigInteger("100"));
        return accountsRepository;
    }

}
