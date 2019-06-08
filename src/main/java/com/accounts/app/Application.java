package com.accounts.app;

import com.accounts.configuration.ConfigurationModule;
import com.accounts.configuration.RestController;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Application {

    Injector injector;

    public Application() {
        this.injector = Guice.createInjector(new ConfigurationModule());
    }

    public void start() {
        RestController restController = injector.getInstance(RestController.class);
        restController.setupEndpoints();
    }
}
