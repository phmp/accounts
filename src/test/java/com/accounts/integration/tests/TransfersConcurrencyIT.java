package com.accounts.integration.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class TransfersConcurrencyIT extends AccountsApplicationRunner{

    @BeforeClass(dependsOnMethods = "startApp")
    public void createAccount() {
        when().get("/accounts/0/new/1000");
        when().get("/accounts/1/new/1000");
        when().get("/accounts/2/new/1000");
        checkAccounts();
    }

    private final AtomicInteger counter = new AtomicInteger(1);

    @Test(invocationCount = 900, threadPoolSize = 25)
    public void oneTransfer() {
        int from = this.counter.incrementAndGet()%3;
        int to = (from+1)%3;

        when()
                .get("/accounts/{from}/transfer/{to}/10", from, to)
        .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test(dependsOnMethods = "oneTransfer", alwaysRun = true)
    public void allAccountsShouldHaveTheSameState(){
        checkAccounts();
    }

    private void checkAccounts() {
        checkIfAccountIsCorrect("0");
        checkIfAccountIsCorrect("1");
        checkIfAccountIsCorrect("2");
    }

    private void checkIfAccountIsCorrect(String id) {
        when()
                .get("/accounts/{id}", id)
        .then()
                .statusCode(200)
                .body("amount", equalTo(1000));
    }

}
