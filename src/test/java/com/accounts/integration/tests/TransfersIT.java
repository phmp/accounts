package com.accounts.integration.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class TransfersIT extends AccountsApplicationRunner{

    @BeforeClass(dependsOnMethods = "startApp")
    public void createAccount() {
        when().get("/accounts/T1/new/100");
        when().get("/accounts/T2/new/200");
        when().get("/accounts/T3/new/300");
    }

    @Test
    public void simpleTransfer() {
        when()
                .get("/accounts/T1/transfer/T2/10")
        .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    public void transferMoreThenIsOnAccount() {
        when()
                .get("/accounts/T1/transfer/T2/1000")
        .then()
                .statusCode(500)
                .body("success", equalTo(false));
    }

    @Test
    public void transferToNotExistingAccount() {
        when()
                .get("/accounts/T1/transfer/X/10")
        .then()
                .statusCode(400);
    }

    @Test
    public void transferFromNotExistingAccount() {
        when()
                .get("/accounts/X/transfer/T2/10")
        .then()
                .statusCode(400);
    }

    @Test
    public void transferToTheSameAccount() {
        when()
                .get("/accounts/T1/transfer/T1/10")
        .then()
                .statusCode(400);
    }

    @Test
    public void transferNegativeAmount() {
        when()
                .get("/accounts/T1/transfer/T2/-10")
        .then()
                .statusCode(400);
    }

}
