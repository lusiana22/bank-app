package com.bank.bankapplication.controller;

import com.bank.bankapplication.controller.advice.GeneralExceptionControllerAdvice;
import com.bank.bankapplication.data.error.ErrorResponse;
import com.bank.bankapplication.exception.DataAlreadyExistsException;
import com.bank.bankapplication.exception.InsufficientFundsException;
import com.bank.bankapplication.exception.NotFoundException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GeneralExceptionControllerAdviceTest {
    private static final String UNIQUE_IDENTIFIER = "UNIQUE_IDENTIFIER";
    private static final String MESSAGE = "MESSAGE";

    @Test
    public void handleNotFoundException() {
        NotFoundException exception = new NotFoundException(MESSAGE);
        GeneralExceptionControllerAdvice advice = new GeneralExceptionControllerAdvice();
        ResponseEntity<ErrorResponse> responseEntity = advice.handleNotFoundException(exception);
        MatcherAssert.assertThat(responseEntity.getStatusCode(), Matchers.is(Matchers.equalTo(HttpStatus.NOT_FOUND)));
    }

    @Test
    public void handleInsufficientFundsException() {
        InsufficientFundsException exception = new InsufficientFundsException(MESSAGE);
        GeneralExceptionControllerAdvice advice = new GeneralExceptionControllerAdvice();
        ResponseEntity<ErrorResponse> responseEntity = advice.handleInsufficientFundsException(exception);
        MatcherAssert.assertThat(responseEntity.getStatusCode(), Matchers.is(Matchers.equalTo(HttpStatus.BAD_REQUEST)));
    }

    @Test
    public void handleDataAlreadyExistsException() {
        DataAlreadyExistsException exception = new DataAlreadyExistsException(MESSAGE);
        GeneralExceptionControllerAdvice advice = new GeneralExceptionControllerAdvice();
        ResponseEntity<ErrorResponse> responseEntity = advice.handleDataAlreadyExistsException(exception);
        MatcherAssert.assertThat(responseEntity.getStatusCode(), Matchers.is(Matchers.equalTo(HttpStatus.BAD_REQUEST)));
    }

    @Test
    public void handleRuntimeException() {
        String description = "Unexpected system exception ID: " + UNIQUE_IDENTIFIER;
        RuntimeException exception = new RuntimeException(description);
        GeneralExceptionControllerAdvice advice = new GeneralExceptionControllerAdvice();
        ResponseEntity<ErrorResponse> responseEntity = advice.handleRuntimeException(exception);
        MatcherAssert.assertThat(responseEntity.getStatusCode(), Matchers.is(Matchers.equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}