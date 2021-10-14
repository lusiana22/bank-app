package com.bank.bankapplication.data;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class BankAccountResponseTest {
    private static final String ACCOUNT_NUMBER = "465475";
    private static final AccountType ACCOUNT_TYPE = AccountType.CHECKING;
    private static final AccountType ACCOUNT_TYPE_SAVINGS = AccountType.SAVINGS;
    private static final BigDecimal BALANCE = BigDecimal.ZERO;

    @Test
    public void createObject() {
        BankAccountResponse response = BankAccountResponse.builder()
                .accountNumber(ACCOUNT_NUMBER)
                .accountType(ACCOUNT_TYPE)
                .balance(BALANCE)
                .build();
        MatcherAssert.assertThat(response.getAccountNumber(), Matchers.is(Matchers.sameInstance(ACCOUNT_NUMBER)));
        MatcherAssert.assertThat(response.getAccountType(), Matchers.is(Matchers.sameInstance(ACCOUNT_TYPE)));
        MatcherAssert.assertThat(response.getBalance(), Matchers.is(Matchers.sameInstance(BALANCE)));
    }

    @Test
    public void updateObject() {
        BankAccountResponse response = BankAccountResponse.builder()
                .accountNumber(ACCOUNT_NUMBER)
                .accountType(ACCOUNT_TYPE)
                .balance(BALANCE)
                .build();
        response.setAccountType(ACCOUNT_TYPE_SAVINGS);
        MatcherAssert.assertThat(response.getAccountType(), Matchers.is(Matchers.sameInstance(ACCOUNT_TYPE_SAVINGS)));
    }
}