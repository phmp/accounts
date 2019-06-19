package com.accounts.transfer.validation;

import com.accounts.model.Account;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigInteger;

public class AccountValidatorTest {

    @DataProvider
    public static Object[][] provideCorrectAmounts() {
        return new Object[][]{
                { new BigInteger("10"), new BigInteger("10")},
                { new BigInteger("10"), new BigInteger("9")},
                { new BigInteger("10"), new BigInteger("0")}
        };
    }

    @Test(dataProvider = "provideCorrectAmounts")
    public void validationShouldPassIfGiverHasEnoughMoney(BigInteger giverAccountState, BigInteger amountToTransfer) {
        AccountValidator validator = new AccountValidator();
        Account giverAccount = new Account("dummyId", giverAccountState);
        validator.checkIfGiverHasEnoughMoney(giverAccount, amountToTransfer);
    }



}