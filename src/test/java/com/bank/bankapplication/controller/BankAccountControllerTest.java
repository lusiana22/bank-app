package com.bank.bankapplication.controller;

import com.bank.bankapplication.data.TransferMoneyRequest;
import com.bank.bankapplication.service.BankAccountService;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class BankAccountControllerTest {
    private static final String ACCOUNT_NUMBER = "5487548";
    private static final String FROM_ACCOUNT_NUMBER = "12172671";
    private static final String TO_ACCOUNT_NUMBER = "8989384";
    private static final BigDecimal BALANCE = BigDecimal.TEN;
    private static final BigDecimal AMOUNT = BigDecimal.TEN;
    private static final TransferMoneyRequest TRANSFER_MONEY_REQUEST = TransferMoneyRequest.builder()
            .fromAccountNumber(FROM_ACCOUNT_NUMBER)
            .toAccountNumber(TO_ACCOUNT_NUMBER)
            .amount(AMOUNT)
            .build();
    private BankAccountService bankAccountService;

    @RegisterExtension
    Mockery context = new JUnit5Mockery();

    @BeforeEach
    public void setUp() {
        bankAccountService = context.mock(BankAccountService.class);
    }

    @Test
    public void getBalance() {
        context.checking(new Expectations() {
            {
                oneOf(bankAccountService).getBalance(ACCOUNT_NUMBER);
                will(returnValue(BALANCE));
            }
        });
        BankAccountController controller = new BankAccountController(bankAccountService);
        ResponseEntity<BigDecimal> responseEntity = controller.getBalance(ACCOUNT_NUMBER);
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.OK)));
        assertThat(responseEntity.getStatusCodeValue(), is(equalTo(200)));
        assertThat(responseEntity.getBody(), is(equalTo(BALANCE)));
    }

    @Test
    public void transferBetweenAccounts() {
        context.checking(new Expectations() {
            {
                oneOf(bankAccountService).transferBetweenAccounts(TRANSFER_MONEY_REQUEST);
                will(returnValue(TRANSFER_MONEY_REQUEST));
            }
        });
        BankAccountController controller = new BankAccountController(bankAccountService);
        ResponseEntity<TransferMoneyRequest> responseEntity = controller.transferBetweenAccounts(TRANSFER_MONEY_REQUEST);
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.OK)));
        assertThat(responseEntity.getStatusCodeValue(), is(equalTo(200)));
        assertThat(responseEntity.getBody(), is(equalTo(TRANSFER_MONEY_REQUEST)));
    }
}