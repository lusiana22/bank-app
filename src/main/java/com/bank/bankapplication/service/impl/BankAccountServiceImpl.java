package com.bank.bankapplication.service.impl;

import com.bank.bankapplication.data.TransferMoneyRequest;
import com.bank.bankapplication.exception.InsufficientFundsException;
import com.bank.bankapplication.exception.NotFoundException;
import com.bank.bankapplication.persistence.entity.BankAccount;
import com.bank.bankapplication.persistence.entity.TransferHistory;
import com.bank.bankapplication.persistence.repository.BankAccountRepository;
import com.bank.bankapplication.persistence.repository.TransferHistoryRepository;
import com.bank.bankapplication.service.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BankAccountServiceImpl implements BankAccountService {
    private static final Logger LOG = LoggerFactory.getLogger(BankAccountServiceImpl.class);
    private final BankAccountRepository bankAccountRepository;
    private final TransferHistoryRepository transferHistoryRepository;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository,
                                  TransferHistoryRepository transferHistoryRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.transferHistoryRepository = transferHistoryRepository;
    }

    @Override
    public BigDecimal getBalance(String accountNumber) {
        LOG.info("Getting balance for account number: " + accountNumber);
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(accountNumber).orElseThrow(() ->
                new NotFoundException("Could not find bank account with account number: " + accountNumber));
        return bankAccount.getBalance();
    }

    @Override
    public TransferMoneyRequest transferBetweenAccounts(TransferMoneyRequest request) {
        LOG.info("Transfering money from: " + request.getFromAccountNumber() + " to: " + request.getToAccountNumber());
        BankAccount fromAccount = bankAccountRepository.findByAccountNumber(request.getFromAccountNumber()).orElseThrow(() ->
                new NotFoundException("Could not find bank account with account number: " + request.getFromAccountNumber()));
        BankAccount toAccount = bankAccountRepository.findByAccountNumber(request.getToAccountNumber()).orElseThrow(() ->
                new NotFoundException("Could not find bank account with account number: " + request.getToAccountNumber()));
        if (request.getAmount().compareTo(fromAccount.getBalance()) <= 0) {
            fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
            toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));
        } else {
            LOG.info("Insufficient funds: " + request.getAmount());
            throw new InsufficientFundsException("Insufficient funds for this transfer: " + fromAccount.getBalance());
        }
        bankAccountRepository.save(fromAccount);
        bankAccountRepository.save(toAccount);
        TransferHistory transferHistory = TransferHistory.builder()
                .fromAccountNumber(fromAccount.getAccountNumber())
                .toAccountNumber(toAccount.getAccountNumber())
                .amount(request.getAmount())
                .build();
        transferHistoryRepository.save(transferHistory);
        LOG.info("Transfer History saved: " + transferHistory);
        return request;
    }
}
