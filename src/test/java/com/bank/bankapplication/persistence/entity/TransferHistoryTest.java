package com.bank.bankapplication.persistence.entity;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class TransferHistoryTest {
    private static final String FROM_ACCOUNT_NUMBER = "43874";
    private static final String TO_ACCOUNT_NUMBER = "12345";
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(200);
    private static final BigDecimal NEW_AMOUNT = BigDecimal.valueOf(100);

    @Test
    public void createObject() {
        TransferHistory transferHistory = TransferHistory.builder()
                .fromAccountNumber(FROM_ACCOUNT_NUMBER)
                .toAccountNumber(TO_ACCOUNT_NUMBER)
                .amount(AMOUNT)
                .build();
        MatcherAssert.assertThat(transferHistory.getFromAccountNumber(), Matchers.is(Matchers.sameInstance(FROM_ACCOUNT_NUMBER)));
        MatcherAssert.assertThat(transferHistory.getToAccountNumber(), Matchers.is(Matchers.sameInstance(TO_ACCOUNT_NUMBER)));
        MatcherAssert.assertThat(transferHistory.getAmount(), Matchers.is(Matchers.sameInstance(AMOUNT)));
    }

    @Test
    public void updateObject() {
        TransferHistory transferHistory = TransferHistory.builder()
                .fromAccountNumber(FROM_ACCOUNT_NUMBER)
                .toAccountNumber(TO_ACCOUNT_NUMBER)
                .amount(AMOUNT)
                .build();
        transferHistory.setAmount(NEW_AMOUNT);
        MatcherAssert.assertThat(transferHistory.getAmount(), Matchers.is(Matchers.sameInstance(NEW_AMOUNT)));
    }
}