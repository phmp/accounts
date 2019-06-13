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
        additionalCheck();
    }

    private final AtomicInteger firstCounter = new AtomicInteger(1);
    private final AtomicInteger secondCounter = new AtomicInteger(2);

    @Test(invocationCount = 150, threadPoolSize = 15)
    public void oneTransfer() {
        int from = this.firstCounter.incrementAndGet()%2;
        int to = this.secondCounter.incrementAndGet()%2;

        when()
                .get("/accounts/{firstCounter}/transfer/{to}/10", from, to)
        .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test(dependsOnMethods = "oneTransfer")
    public void additionalCheck(){
        when()
                .get("/accounts")
        .then()
                .statusCode(200)
                .body("[0].id", equalTo("0"))
                .body("[0].amount", equalTo(1000))
                .body("[1].id", equalTo("1"))
                .body("[1].amount", equalTo(1000));
    }

}
