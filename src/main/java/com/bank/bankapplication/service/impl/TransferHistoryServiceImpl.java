package com.bank.bankapplication.service.impl;

import com.bank.bankapplication.data.TransferHistoryResponse;
import com.bank.bankapplication.persistence.entity.TransferHistory;
import com.bank.bankapplication.persistence.repository.TransferHistoryRepository;
import com.bank.bankapplication.service.TransferHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransferHistoryServiceImpl implements TransferHistoryService {
    private static final Logger LOG = LoggerFactory.getLogger(TransferHistoryServiceImpl.class);
    private final TransferHistoryRepository transferHistoryRepository;

    @Autowired
    public TransferHistoryServiceImpl(TransferHistoryRepository transferHistoryRepository) {
        this.transferHistoryRepository = transferHistoryRepository;
    }

    @Override
    public List<TransferHistoryResponse> getTransferHistory(String accountNumber) {
        LOG.info("Getting transfer for account number: " + accountNumber);
        List<TransferHistory> transferHistoryList =
                transferHistoryRepository.findByFromAccountNumberOrToAccountNumber(accountNumber, accountNumber);

        List<TransferHistoryResponse> transferHistoryResponses = new ArrayList<>();
            for (TransferHistory transferHistory: transferHistoryList) {
                TransferHistoryResponse transferHistoryResponse = TransferHistoryResponse.builder()
                        .fromAccountNumber(transferHistory.getFromAccountNumber())
                        .toAccountNumber(transferHistory.getToAccountNumber())
                        .amount(transferHistory.getAmount())
                        .dateTime(transferHistory.getInsertedAt())
                        .build();
                transferHistoryResponses.add(transferHistoryResponse);
            }
        LOG.info("The transfer history: " + transferHistoryResponses);
        return transferHistoryResponses;
    }
}