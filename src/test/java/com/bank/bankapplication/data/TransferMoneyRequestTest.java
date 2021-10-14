package com.bank.bankapplication.data;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class TransferMoneyRequestTest {
    private static final String FROM_ACCOUNT_NUMBER = "5847584";
    private static final String TO_ACCOUNT_NUMBER = "75487584";
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(200);

    @Test
    public void createObject() {
        TransferMoneyRequest transferMoneyRequest = TransferMoneyRequest.builder()
                .fromAccountNumber(FROM_ACCOUNT_NUMBER)
                .toAccountNumber(TO_ACCOUNT_NUMBER)
                .amount(AMOUNT)
                .build();
        MatcherAssert.assertThat(transferMoneyRequest.getFromAccountNumber(), Matchers.is(Matchers.sameInstance(FROM_ACCOUNT_NUMBER)));
        MatcherAssert.assertThat(transferMoneyRequest.getToAccountNumber(), Matchers.is(Matchers.sameInstance(TO_ACCOUNT_NUMBER)));
        MatcherAssert.assertThat(transferMoneyRequest.getAmount(), Matchers.is(Matchers.sameInstance(AMOUNT)));
    }

    @Test
    public void updateObject() {
        TransferMoneyRequest transferMoneyRequest = TransferMoneyRequest.builder()
                .fromAccountNumber(FROM_ACCOUNT_NUMBER)
                .toAccountNumber(TO_ACCOUNT_NUMBER)
                .amount(AMOUNT)
                .build();
        transferMoneyRequest.setAmount(BigDecimal.TEN);
        MatcherAssert.assertThat(transferMoneyRequest.getAmount(), Matchers.is(Matchers.sameInstance(BigDecimal.TEN)));
    }
}