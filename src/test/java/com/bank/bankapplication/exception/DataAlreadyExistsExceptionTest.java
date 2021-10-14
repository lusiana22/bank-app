package com.bank.bankapplication.exception;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class DataAlreadyExistsExceptionTest {
    private static final String MESSAGE = "MESSAGE";

    @Test
    public void notFoundException() {
        DataAlreadyExistsException exception = new DataAlreadyExistsException(MESSAGE);
        MatcherAssert.assertThat(exception.getMessage(), Matchers.is(Matchers.equalTo(MESSAGE)));
    }
}