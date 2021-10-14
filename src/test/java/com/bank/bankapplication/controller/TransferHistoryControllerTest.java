package com.bank.bankapplication.controller;

import com.bank.bankapplication.data.TransferHistoryResponse;
import com.bank.bankapplication.service.TransferHistoryService;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TransferHistoryControllerTest {
    private static final String ACCOUNT_NUMBER = "5845784";
    private static final String FROM_ACCOUNT_NUMBER = "5845784";
    private static final String TO_ACCOUNT_NUMBER = "75487584";
    private static final BigDecimal AMOUNT = BigDecimal.TEN;
    private static final TransferHistoryResponse TRANSFER_HISTORY_RESPONSE = TransferHistoryResponse.builder()
            .fromAccountNumber(FROM_ACCOUNT_NUMBER)
            .toAccountNumber(TO_ACCOUNT_NUMBER)
            .amount(AMOUNT)
            .dateTime(LocalDateTime.now())
            .build();
    private TransferHistoryService transferHistoryService;

    @RegisterExtension
    Mockery context = new JUnit5Mockery();

    @BeforeEach
    public void setUp() {
        transferHistoryService = context.mock(TransferHistoryService.class);
    }

    @Test
    public void getTransferHistory() {
        context.checking(new Expectations() {
            {
                oneOf(transferHistoryService).getTransferHistory(ACCOUNT_NUMBER);
                will(returnValue(Collections.singletonList(TRANSFER_HISTORY_RESPONSE)));
            }
        });
        TransferHistoryController controller = new TransferHistoryController(transferHistoryService);
        ResponseEntity<List<TransferHistoryResponse>> responseEntity = controller.getTransferHistory(ACCOUNT_NUMBER);
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.OK)));
        assertThat(responseEntity.getStatusCodeValue(), is(equalTo(200)));
        assertThat(responseEntity.getBody(), is(equalTo(Collections.singletonList(TRANSFER_HISTORY_RESPONSE))));
    }
}