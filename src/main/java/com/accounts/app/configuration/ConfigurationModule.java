package com.accounts.app.configuration;

import com.accounts.storage.AccountsRepository;
import com.accounts.transfer.TransferExecutor;
import com.accounts.transfer.solutions.one.TwoAccountsBlockingTransferExecutor;
import com.accounts.transfer.solutions.two.AmountBlockingTransferExecutor;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

/**
 * Created by Paweł on 2019-06-08.
 */
public class ConfigurationModule extends AbstractModule {

    @Override
    protected void configure() {
//        bind(TransferExecutor.class).to(TwoAccountsBlockingTransferExecutor.class);
        bind(TransferExecutor.class).to(AmountBlockingTransferExecutor.class);
    }

    @Provides
    @Singleton
    public AccountsRepository createRepository() {
        return new AccountsRepository();
    }

}
