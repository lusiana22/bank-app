package com.bank.bankapplication.data;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class BankAccountRequestTest {
    private static final AccountType CHECKING_ACCOUNT_TYPE = AccountType.CHECKING;
    private static final AccountType SAVINGS_ACCOUNT_TYPE = AccountType.SAVINGS;
    private static final String ACCOUNT_NUMBER = "4837483";
    private static final BigDecimal BALANCE = BigDecimal.TEN;

    @Test
    public void createObject() {
        BankAccountRequest bankAccountRequest = BankAccountRequest.builder()
                .accountNumber(ACCOUNT_NUMBER)
                .accountType(CHECKING_ACCOUNT_TYPE)
                .balance(BALANCE)
                .build();
        MatcherAssert.assertThat(bankAccountRequest.getAccountNumber(), Matchers.is(Matchers.sameInstance(ACCOUNT_NUMBER)));
        MatcherAssert.assertThat(bankAccountRequest.getAccountType(), Matchers.is(Matchers.sameInstance(CHECKING_ACCOUNT_TYPE)));
        MatcherAssert.assertThat(bankAccountRequest.getBalance(), Matchers.is(Matchers.sameInstance(BALANCE)));
    }

    @Test
    public void updateObject() {
        BankAccountRequest bankAccountRequest = BankAccountRequest.builder()
                .accountNumber(ACCOUNT_NUMBER)
                .accountType(CHECKING_ACCOUNT_TYPE)
                .balance(BALANCE)
                .build();
        bankAccountRequest.setAccountType(SAVINGS_ACCOUNT_TYPE);
        MatcherAssert.assertThat(bankAccountRequest.getAccountType(), Matchers.is(Matchers.sameInstance(SAVINGS_ACCOUNT_TYPE)));
    }
}
