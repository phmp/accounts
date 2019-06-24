package com.accounts.integration.tests;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class AccountOperationIT extends AccountsApplicationRunner {

    @Test
    public void createAccount() {
        when()
                .get("/accounts/A1/new/100")
        .then()
                .statusCode(201)
                .body("id", equalTo("A1"))
                .body("amount", equalTo(100));
    }

    @Test(dependsOnMethods = "createAccount")
    public void getAccounts(){
        when()
            .get("/accounts")
        .then()
            .statusCode(200)
            .body("[0].id", equalTo("A1"))
            .body("[0].amount", equalTo(100));
    }

    @Test(dependsOnMethods = "createAccount")
    public void getAccount(){
        when()
            .get("/accounts/A1")
        .then()
            .statusCode(200)
            .body("id", equalTo("A1"))
            .body("amount", equalTo(100));
    }

    @Test(dependsOnMethods = "createAccount")
    public void createExistingAccount() {
        when()
                .get("/accounts/A1/new/200")
        .then()
                .statusCode(400);
    }
}
