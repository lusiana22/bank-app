package com.bank.bankapplication.service;

import com.bank.bankapplication.data.TransferHistoryResponse;
import com.bank.bankapplication.persistence.entity.TransferHistory;
import com.bank.bankapplication.persistence.repository.TransferHistoryRepository;
import com.bank.bankapplication.service.impl.TransferHistoryServiceImpl;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TransferHistoryServiceTest {
    private static final String ACCOUNT_NUMBER_REQUEST = "75847584";
    private static final String FROM_ACCOUNT_NUMBER = "75847584";
    private static final String TO_ACCOUNT_NUMBER = "83984398";
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(200);
    private static final TransferHistory TRANSFER_HISTORY = TransferHistory.builder()
            .fromAccountNumber(FROM_ACCOUNT_NUMBER)
            .toAccountNumber(TO_ACCOUNT_NUMBER)
            .amount(AMOUNT)
            .build();


    private TransferHistoryRepository transferHistoryRepository;

    @RegisterExtension
    Mockery context = new JUnit5Mockery();

    @BeforeEach
    public void setUp() {
        transferHistoryRepository = context.mock(TransferHistoryRepository.class);
    }

    @Test
    public void getTransferHistory() {
        context.checking(new Expectations() {
            {
                oneOf(transferHistoryRepository).findByFromAccountNumberOrToAccountNumber(ACCOUNT_NUMBER_REQUEST, ACCOUNT_NUMBER_REQUEST);
                will(returnValue(Collections.singletonList(TRANSFER_HISTORY)));
            }
        });
        TransferHistoryService service = new TransferHistoryServiceImpl(transferHistoryRepository);
        List<TransferHistoryResponse> transferHistoryList = service.getTransferHistory(ACCOUNT_NUMBER_REQUEST);
        assertThat(transferHistoryList.get(0).getFromAccountNumber(), is(equalTo(TRANSFER_HISTORY.getFromAccountNumber())));
        assertThat(transferHistoryList.get(0).getToAccountNumber(), is(equalTo(TRANSFER_HISTORY.getToAccountNumber())));
        assertThat(transferHistoryList.get(0).getAmount(), is(equalTo(TRANSFER_HISTORY.getAmount())));
    }

    @Test
    public void getTransferHistoryNoResult() {
        context.checking(new Expectations() {
            {
                oneOf(transferHistoryRepository).findByFromAccountNumberOrToAccountNumber(ACCOUNT_NUMBER_REQUEST, ACCOUNT_NUMBER_REQUEST);
                will(returnValue(Collections.emptyList()));
            }
        });
        TransferHistoryService service = new TransferHistoryServiceImpl(transferHistoryRepository);
        List<TransferHistoryResponse> transferHistoryList = service.getTransferHistory(ACCOUNT_NUMBER_REQUEST);
        assertThat(transferHistoryList.size(), is(equalTo(0)));
    }
}