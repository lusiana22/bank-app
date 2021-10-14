package com.bank.bankapplication.data.error;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class ErrorTypeTest {
    private static final String NOT_FOUND = "NOT_FOUND";
    private static final String INVALID_REQUEST_DATA = "INVALID_REQUEST_DATA";
    private static final String UNEXPECTED_ERROR = "UNEXPECTED_ERROR";
    private static final String DATA_ALREADY_EXISTS = "DATA_ALREADY_EXISTS";

    @Test
    public void getName() {
        MatcherAssert.assertThat(ErrorType.NOT_FOUND.name(), Matchers.is(Matchers.equalTo(NOT_FOUND)));
        MatcherAssert.assertThat(ErrorType.INVALID_REQUEST_DATA.name(), Matchers.is(Matchers.equalTo(INVALID_REQUEST_DATA)));
        MatcherAssert.assertThat(ErrorType.UNEXPECTED_ERROR.name(), Matchers.is(Matchers.equalTo(UNEXPECTED_ERROR)));
        MatcherAssert.assertThat(ErrorType.DATA_ALREADY_EXISTS.name(), Matchers.is(Matchers.equalTo(DATA_ALREADY_EXISTS)));
    }

    @Test
    public void fromValue(){
        MatcherAssert.assertThat(ErrorType.NOT_FOUND, Matchers.is(Matchers.equalTo(ErrorType.fromValue(NOT_FOUND))));
        MatcherAssert.assertThat(ErrorType.INVALID_REQUEST_DATA, Matchers.is(Matchers.equalTo(ErrorType.fromValue(INVALID_REQUEST_DATA))));
        MatcherAssert.assertThat(ErrorType.UNEXPECTED_ERROR, Matchers.is(Matchers.equalTo(ErrorType.fromValue(UNEXPECTED_ERROR))));
        MatcherAssert.assertThat(ErrorType.DATA_ALREADY_EXISTS, Matchers.is(Matchers.equalTo(ErrorType.fromValue(DATA_ALREADY_EXISTS))));
    }
}