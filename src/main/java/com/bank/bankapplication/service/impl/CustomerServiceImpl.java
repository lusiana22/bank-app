package com.bank.bankapplication.service.impl;

import com.bank.bankapplication.data.AccountType;
import com.bank.bankapplication.data.BankAccountRequest;
import com.bank.bankapplication.data.BankAccountResponse;
import com.bank.bankapplication.data.CustomerRequest;
import com.bank.bankapplication.data.CustomerResponse;
import com.bank.bankapplication.exception.DataAlreadyExistsException;
import com.bank.bankapplication.exception.NotFoundException;
import com.bank.bankapplication.persistence.entity.BankAccount;
import com.bank.bankapplication.persistence.entity.Customer;
import com.bank.bankapplication.persistence.repository.BankAccountRepository;
import com.bank.bankapplication.persistence.repository.CustomerRepository;
import com.bank.bankapplication.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerServiceImpl implements CustomerService {
    private static final Logger LOG = LoggerFactory.getLogger(BankAccountServiceImpl.class);
    private static final String CHECKING_ACCOUNT_PREFIX = "6330";
    private static final String SAVINGS_ACCOUNT_PREFIX = "5246";

    private final CustomerRepository customerRepository;
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository) {
        this.customerRepository = customerRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public Customer createCustomer(CustomerRequest customerRequest) {
        LOG.info("Processing new customer: " + customerRequest);

        //check if we have different account types specified on the request
        if (!areAccountsDistinct(customerRequest.getBankAccountRequestList())) {
            throw new DataAlreadyExistsException("The customer cannot have 2 same account types");
        }
        Customer customer = Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .build();
        customerRepository.save(customer);
        LOG.info("Customer saved: " + customer);

        for (BankAccountRequest bankAccount: customerRequest.getBankAccountRequestList()) {
            BankAccount bankAccountEntity = BankAccount.builder()
                    .accountNumber(createAccountNumber(bankAccount))
                    .accountType(bankAccount.getAccountType())
                    .balance(bankAccount.getBalance())
                    .customer(customer)
                    .build();
            bankAccountRepository.save(bankAccountEntity);
            LOG.info("Bank Account saved: " + bankAccount);
        }
        return customer;
    }

    boolean areAccountsDistinct(List<BankAccountRequest> bankAccountRequests) {
        return bankAccountRequests.stream().map(BankAccountRequest::getAccountType).distinct().count() == bankAccountRequests.size();
    }

    @Override
    public BankAccountRequest addBankAccountToCustomer(Long customerId, BankAccountRequest bankAccountRequest) {
        LOG.info("Adding a new bank account to customer: " + customerId);
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                    new NotFoundException("Could not find customer with id: " + customerId));

        List<BankAccount> bankAccountList = bankAccountRepository.findByCustomerId(customerId);
        List<AccountType> existingAccountTypes = new ArrayList<>();
        if (!bankAccountList.isEmpty()) {
            for (BankAccount bankAccount : bankAccountList) {
                existingAccountTypes.add(bankAccount.getAccountType());
                LOG.info("Existing account types " + existingAccountTypes);
            }
        }
        if (existingAccountTypes.contains(bankAccountRequest.getAccountType())) {
            throw new DataAlreadyExistsException("The customer cannot have the same account type again");
        }

        BankAccount bankAccount = BankAccount.builder()
            .accountNumber(createAccountNumber(bankAccountRequest))
            .accountType(bankAccountRequest.getAccountType())
            .balance(bankAccountRequest.getBalance())
            .customer(customer)
            .build();
        bankAccountRepository.save(bankAccount);
        LOG.info("New bank account added successfully: " + bankAccount);
        return bankAccountRequest;
    }

    @Override
    public CustomerResponse getCustomer(Long customerId) {
        LOG.info("Retrieve data for customer id : " + customerId);
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                new NotFoundException("Could not find customer with id: " + customerId));
        List<BankAccount> bankAccountList = bankAccountRepository.findByCustomerId(customerId);
        List<BankAccountResponse> bankAccountResponses = new ArrayList<>();
        for (BankAccount bankAccount : bankAccountList) {
            BankAccountResponse response = BankAccountResponse.builder()
                    .accountNumber(bankAccount.getAccountNumber())
                    .accountType(bankAccount.getAccountType())
                    .balance(bankAccount.getBalance())
                    .build();
            bankAccountResponses.add(response);
        }
        return CustomerResponse.builder()
                .customerId(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .bankAccounts(bankAccountResponses)
                .build();
    }

    private String createAccountNumber(BankAccountRequest bankAccount) {
        String accountNumber = CHECKING_ACCOUNT_PREFIX + bankAccount.getAccountNumber();
        if (bankAccount.getAccountType().equals(AccountType.SAVINGS)) {
            accountNumber = SAVINGS_ACCOUNT_PREFIX + bankAccount.getAccountNumber();
        }
        return accountNumber;
    }
}