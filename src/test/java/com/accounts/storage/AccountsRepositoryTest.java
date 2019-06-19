package com.accounts.storage;

import com.accounts.model.Account;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AccountsRepositoryTest {

    private AccountsRepository repository;

    @BeforeMethod
    public void init() {
        repository = new AccountsRepository();
    }

    @Test
    public void newRepositoryShouldBeEmpty() {
        Collection<Account> accounts = repository.getAll();
        assertThat(accounts).isEmpty();
    }

    @Test
    public void addedAccountShouldBeInRepository() {
        //given
        String dummyId = "dummy";
        BigInteger dummyAmount = new BigInteger("123");
        Account expectedAccount = new Account(dummyId, dummyAmount);

        //when
        repository.add(dummyId, dummyAmount);
        Collection<Account> actualAccounts = repository.getAll();
        //then
        assertThat(actualAccounts)
                .hasSize(1)
                .contains(expectedAccount);
    }

    @Test
    public void addedAccountShouldBeFoundById() {
        //given
        String dummyId = "dummy";
        BigInteger dummyAmount = new BigInteger("123");
        Account expectedAccount = new Account(dummyId, dummyAmount);

        //when
        repository.add(dummyId, dummyAmount);
        Account actualAccount = repository.get(dummyId);

        //then
        assertThat(actualAccount).isEqualTo(expectedAccount);
    }

    @Test
    public void manyAccountsShouldBeAbleToAdd() {
        //given
        Account account1 = new Account("A", new BigInteger("1"));
        Account account2 = new Account("B", new BigInteger("2"));
        Account account3 = new Account("C", new BigInteger("3"));

        //when
        repository.add(account1.getId(), account1.getAmount());
        repository.add(account2.getId(), account2.getAmount());
        repository.add(account3.getId(), account3.getAmount());
        Collection<Account> actualAccounts = repository.getAll();

        //then
        assertThat(actualAccounts).containsOnly(account1, account2, account3);
    }


    @Test
    public void addingExistingAccountShouldCauseException() {
        //given
        String dummyId = "dummy";
        BigInteger dummyAmount = new BigInteger("123");
        BigInteger dummyAmount2 = new BigInteger("321");

        //when
        repository.add(dummyId, dummyAmount);

        //then
        assertThatThrownBy(()->repository.add(dummyId, dummyAmount2))
                .isInstanceOf(IncorrectRequestedDataException.class);
    }

    @Test(expectedExceptions = IncorrectRequestedDataException.class)
    public void gettingNotExistingAccountIdShouldCauseException() {
        repository.get("notExisting");
    }

}