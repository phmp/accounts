package com.accounts.integration.tests;

import com.accounts.app.Application;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import spark.Spark;

public class AccountsApplicationRunner {

    @BeforeClass
    public void startApp() {
        new Application().start();
        Spark.awaitInitialization();
    }

    @BeforeSuite
    public void initConfiguration() {
        RestAssured.baseURI = "http://localhost:4567/";
    }

}
