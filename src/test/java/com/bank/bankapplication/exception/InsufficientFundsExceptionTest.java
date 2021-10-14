package com.bank.bankapplication.exception;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class InsufficientFundsExceptionTest {
    private static final String MESSAGE = "MESSAGE";

    @Test
    public void notFoundException() {
        InsufficientFundsException exception = new InsufficientFundsException(MESSAGE);
        MatcherAssert.assertThat(exception.getMessage(), Matchers.is(Matchers.equalTo(MESSAGE)));
    }
}