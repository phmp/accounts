package com.accounts.model;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigInteger;


/**
 * Created by Paweł on 2019-06-11.
 */
public class AccountTest {

    @Test
    public void test(){
        Account account = new Account("A", new BigInteger("10"));
        account.addAmount(new BigInteger("5"));
        account.subtractAmount(new BigInteger("6"));
        BigInteger amount = account.getAmount();
        Assert.assertEquals(amount, new BigInteger("9"));
    }

}