package com.accounts.app.configuration;

import com.accounts.storage.AccountsRepository;
import com.accounts.transfer.BiAccountLockTransferExecutor;
import com.accounts.transfer.TransferExecutor;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

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
    public AccountsRepository createRepository() {
        return new AccountsRepository();
    }

}
