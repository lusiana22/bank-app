package com.bank.bankapplication.service;

import com.bank.bankapplication.data.TransferHistoryResponse;

import java.util.List;

public interface TransferHistoryService {
    List<TransferHistoryResponse> getTransferHistory(String accountNumber);
}
