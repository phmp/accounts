package com.accounts.integration.tests;


import com.accounts.app.Application;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class AccountOperationIT {

    @BeforeClass
    public void startApp() throws InterruptedException {
        new Application().start();
        //TODO waiting for app
        Thread.sleep(1000);
        RestAssured.baseURI = "http://localhost:4567/";
    }

    @Test(dependsOnMethods = "createdAccountShouldBeReturned")
    public void appShouldReturn200forAccountsRequest(){
        when()
            .get("/accounts")
        .then()
            .statusCode(200)
            .body("$[0].id", equalTo("A1"))
        ;
    }

    @Test
    public void createdAccountShouldBeReturned() {
        when()
                .get("/accounts/A1/new/100")
        .then()
                .statusCode(201)
                .body("id", equalTo("A1"))
        ;
    }
}
