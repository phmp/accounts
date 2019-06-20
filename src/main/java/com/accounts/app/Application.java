package com.accounts.app;

import com.accounts.app.configuration.ConfigurationModule;
import com.accounts.controllers.RestController;
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
