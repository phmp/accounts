package com.accounts.model;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigInteger;


/**
 * Created by Paweł on 2019-06-11.
 */
public class AccountTest {

    @Test
    public void amountOnAccountShouldBeChangedAfterAdding(){
        //given
        Account account = new Account("A", new BigInteger("1"));
        //when
        account.addAmount(new BigInteger("2"));
        //then
        BigInteger amount = account.getAmount();
        Assert.assertEquals(amount, new BigInteger("3"));
    }

    @Test
    public void amountOnAccountShouldBeChangedAfterSubtracting(){
        //given
        Account account = new Account("A", new BigInteger("5"));
        //when
        account.subtractAmount(new BigInteger("2"));
        //then
        BigInteger amount = account.getAmount();
        Assert.assertEquals(amount, new BigInteger("3"));
    }

    @Test
    public void amountOnAccountShouldBeNegativeAfterSubtracting(){
        //given
        Account account = new Account("A", new BigInteger("1"));
        //when
        account.subtractAmount(new BigInteger("3"));
        //then
        BigInteger amount = account.getAmount();
        Assert.assertEquals(amount, new BigInteger("-2"));
    }

}