package com.bank.bankapplication.service;

import com.bank.bankapplication.data.BankAccountRequest;
import com.bank.bankapplication.data.CustomerRequest;
import com.bank.bankapplication.data.CustomerResponse;
import com.bank.bankapplication.persistence.entity.Customer;

public interface CustomerService {
    Customer createCustomer(CustomerRequest customerRequest);
    CustomerResponse getCustomer(Long customerId);
    BankAccountRequest addBankAccountToCustomer(Long customerId, BankAccountRequest bankAccountRequest);
}
