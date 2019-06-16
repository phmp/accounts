package com.accounts.integration.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class TranfersConcurrencyIT extends AccountsApplicationRunner{

    @BeforeClass(dependsOnMethods = "startApp")
    public void createAccount() {
        when().get("/accounts/0/new/1000");
        when().get("/accounts/1/new/1000");
        checkAccounts();
    }

    private final AtomicInteger firstCounter = new AtomicInteger(1);
    private final AtomicInteger secondCounter = new AtomicInteger(2);

    @Test(invocationCount = 300, threadPoolSize = 10)
    public void oneTransfer() {
        int from = this.firstCounter.incrementAndGet()%2;
        int to = this.secondCounter.incrementAndGet()%2;

        when()
                .get("/accounts/{from}/transfer/{to}/10", from, to)
        .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test(dependsOnMethods = "oneTransfer", alwaysRun = true)
    public void checkAccounts(){
        checkIfAccountIsCorrect("0");
        checkIfAccountIsCorrect("1");
    }

    private void checkIfAccountIsCorrect(String id) {
        when()
                .get("/accounts/{id}", id)
        .then()
                .statusCode(200)
                .body("amount", equalTo(1000));
    }

}
