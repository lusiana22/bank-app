package com.bank.bankapplication.data;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferHistoryResponseTest {
    private static final String FROM_ACCOUNT_NUMBER = "5847584";
    private static final String TO_ACCOUNT_NUMBER = "75487584";
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(200);
    private static final BigDecimal NEW_AMOUNT = BigDecimal.valueOf(100);
    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2021, 10, 9, 8, 53, 3);

    @Test
    public void createObject() {
        TransferHistoryResponse transferHistoryResponse = TransferHistoryResponse.builder()
                .fromAccountNumber(FROM_ACCOUNT_NUMBER)
                .toAccountNumber(TO_ACCOUNT_NUMBER)
                .amount(AMOUNT)
                .dateTime(DATE_TIME)
                .build();
        MatcherAssert.assertThat(transferHistoryResponse.getFromAccountNumber(), Matchers.is(Matchers.sameInstance(FROM_ACCOUNT_NUMBER)));
        MatcherAssert.assertThat(transferHistoryResponse.getToAccountNumber(), Matchers.is(Matchers.sameInstance(TO_ACCOUNT_NUMBER)));
        MatcherAssert.assertThat(transferHistoryResponse.getAmount(), Matchers.is(Matchers.sameInstance(AMOUNT)));
        MatcherAssert.assertThat(transferHistoryResponse.getDateTime(), Matchers.is(Matchers.sameInstance(DATE_TIME)));
    }

    @Test
    public void updateObject() {
        TransferHistoryResponse transferHistoryResponse = TransferHistoryResponse.builder()
                .fromAccountNumber(FROM_ACCOUNT_NUMBER)
                .toAccountNumber(TO_ACCOUNT_NUMBER)
                .amount(AMOUNT)
                .dateTime(DATE_TIME)
                .build();
        transferHistoryResponse.setAmount(NEW_AMOUNT);
        MatcherAssert.assertThat(transferHistoryResponse.getAmount(), Matchers.is(Matchers.sameInstance(NEW_AMOUNT)));
    }
}