package com.accounts.transfer;

import com.accounts.model.Account;
import com.accounts.storage.AccountsRepository;
import com.accounts.storage.IncorrectRequestedDataException;
import com.accounts.transfer.validation.TransferRequestValidator;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class TransferManagerTest {

    private TransferManager manager;
    private AccountsRepository mockedRepository;
    private TransferExecutor mockedExecutor;
    private TransferRequestValidator spyValidator;

    @BeforeMethod
    public void init() {
        mockedRepository = Mockito.mock(AccountsRepository.class);
        mockedExecutor = Mockito.mock(TransferExecutor.class);
        spyValidator = Mockito.spy(new TransferRequestValidator());
        manager = new TransferManager(mockedRepository, mockedExecutor, spyValidator);
    }

    @Test
    public void managerShouldGetAccountsFromRepositoryAndPassToExecutor() {
        //given
        Account dummyAccount1 = new Account("1", new BigInteger("1000"));
        Account dummyAccount2 = new Account("2", new BigInteger("2000"));
        when(mockedRepository.get(dummyAccount1.getId())).thenReturn(dummyAccount1);
        when(mockedRepository.get(dummyAccount2.getId())).thenReturn(dummyAccount2);
        BigInteger transferAmount = new BigInteger("50");

        //when
        manager.transfer(dummyAccount1.getId(), dummyAccount2.getId(), transferAmount);

        //then
        verify(mockedExecutor).execute(eq(dummyAccount1), eq(dummyAccount2), eq(transferAmount));
    }

    @Test
    public void managerShouldPreventFromSelfTransaction() {
        String dummyId = "A";
        BigInteger dummyAmount = new BigInteger("1");

        assertThatThrownBy(() -> manager.transfer(dummyId, dummyId, dummyAmount))
                .isInstanceOf(IncorrectRequestedDataException.class);
        verifyZeroInteractions(mockedExecutor);
        verifyZeroInteractions(mockedRepository);
    }

    @Test
    public void managerShouldPassExceptionFromValidator(){
        Mockito.doThrow(RuntimeException.class)
                .when(spyValidator)
                .validate(any(),any(),any());
        assertThatThrownBy(() -> manager.transfer("dummy", "dummy 2", BigInteger.TEN))
                .isInstanceOf(RuntimeException.class);
        verifyZeroInteractions(mockedExecutor);
        verifyZeroInteractions(mockedRepository);
    }

    @Test
    public void managerShouldPassExceptionFromRepository(){
        when(mockedRepository.get(any())).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> manager.transfer("dummy", "dummy 2", BigInteger.TEN))
                .isInstanceOf(RuntimeException.class);
        verifyZeroInteractions(mockedExecutor);
    }

    @Test
    public void managerShouldPassExceptionFromExecutor(){
        Mockito.doThrow(RuntimeException.class)
                .when(mockedExecutor)
                .execute(any(),any(),any());
        assertThatThrownBy(() -> manager.transfer("dummy", "dummy 2", BigInteger.TEN))
                .isInstanceOf(RuntimeException.class);
    }
}