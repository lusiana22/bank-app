package com.bank.bankapplication.service;

import com.bank.bankapplication.data.TransferMoneyRequest;

import java.math.BigDecimal;

public interface BankAccountService {
    BigDecimal getBalance(String accountNumber);
    TransferMoneyRequest transferBetweenAccounts(TransferMoneyRequest request);
}