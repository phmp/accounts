package com.accounts.integration.tests;

import com.accounts.app.Application;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class AccountsApplicationRunner {

    @BeforeClass
    public void startApp() throws InterruptedException {
        new Application().start();
        //TODO waiting for app
        Thread.sleep(1000);
        RestAssured.baseURI = "http://localhost:4567/";
    }

}
