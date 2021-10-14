package com.bank.bankapplication.service;

import com.bank.bankapplication.data.AccountType;
import com.bank.bankapplication.data.BankAccountRequest;
import com.bank.bankapplication.data.CustomerRequest;
import com.bank.bankapplication.data.CustomerResponse;
import com.bank.bankapplication.exception.DataAlreadyExistsException;
import com.bank.bankapplication.exception.NotFoundException;
import com.bank.bankapplication.persistence.entity.BankAccount;
import com.bank.bankapplication.persistence.entity.Customer;
import com.bank.bankapplication.persistence.repository.BankAccountRepository;
import com.bank.bankapplication.persistence.repository.CustomerRepository;
import com.bank.bankapplication.service.impl.CustomerServiceImpl;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CustomerServiceTest {
    private static final Long CUSTOMER_ID = 222L;
    private static final String FIRST_NAME = "Lusiana";
    private static final String LAST_NAME = "Velcani";
    private static final BigDecimal BALANCE = BigDecimal.TEN;
    private static final Customer CUSTOMER = Customer.builder()
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .build();
    private static final String ACCOUNT_NUMBER = "6330546547";
    private static final String ACCOUNT_NUMBER_REQUEST = "546547";
    private static final String NEW_ACCOUNT_NUMBER_REQUEST = "5246546547";
    private static final AccountType ACCOUNT_TYPE = AccountType.CHECKING;
    private static final AccountType ACCOUNT_TYPE_SAVINGS = AccountType.SAVINGS;
    private static final BigDecimal INITIAL_BALANCE = BigDecimal.TEN;
    private static final BankAccount BANK_ACCOUNT = BankAccount.builder()
            .accountNumber(ACCOUNT_NUMBER)
            .accountType(ACCOUNT_TYPE)
            .balance(INITIAL_BALANCE)
            .customer(CUSTOMER)
            .build();
    private static final BankAccount NEW_BANK_ACCOUNT = BankAccount.builder()
            .accountNumber(NEW_ACCOUNT_NUMBER_REQUEST)
            .accountType(ACCOUNT_TYPE_SAVINGS)
            .balance(INITIAL_BALANCE)
            .customer(CUSTOMER)
            .build();
    private static final BankAccountRequest BANK_ACCOUNT_REQUEST = BankAccountRequest.builder()
            .accountType(ACCOUNT_TYPE)
            .accountNumber(ACCOUNT_NUMBER_REQUEST)
            .balance(BALANCE)
            .build();
    private static final BankAccountRequest NEW_BANK_ACCOUNT_REQUEST = BankAccountRequest.builder()
            .accountType(ACCOUNT_TYPE_SAVINGS)
            .accountNumber(ACCOUNT_NUMBER_REQUEST)
            .balance(BALANCE)
            .build();
    private static final CustomerRequest CUSTOMER_REQUEST = CustomerRequest.builder()
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .bankAccountRequestList(Collections.singletonList(BANK_ACCOUNT_REQUEST))
            .build();

    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;

    @RegisterExtension
    Mockery context = new JUnit5Mockery();

    @BeforeEach
    public void setUp() {
        customerRepository = context.mock(CustomerRepository.class);
        bankAccountRepository = context.mock(BankAccountRepository.class);
    }

    @Test
    public void createCustomerSuccessfully() {
        context.checking(new Expectations() {
            {
                oneOf(customerRepository).save(CUSTOMER);
                oneOf(bankAccountRepository).save(BANK_ACCOUNT);
            }
        });
        CustomerService service = new CustomerServiceImpl(customerRepository, bankAccountRepository);
        Customer customer = service.createCustomer(CUSTOMER_REQUEST);
        assertThat(customer.getFirstName(), is(equalTo(CUSTOMER.getFirstName())));
        assertThat(customer.getLastName(), is(equalTo(CUSTOMER.getLastName())));
    }

    @Test
    public void getCustomer() {
        context.checking(new Expectations() {
            {
                oneOf(customerRepository).findById(CUSTOMER_ID);
                will(returnValue(Optional.of(CUSTOMER)));
                oneOf(bankAccountRepository).findByCustomerId(CUSTOMER_ID);
                will(returnValue(Collections.singletonList(BANK_ACCOUNT)));
            }
        });
        CustomerService service = new CustomerServiceImpl(customerRepository, bankAccountRepository);
        CustomerResponse customerResponse = service.getCustomer(CUSTOMER_ID);
        assertThat(customerResponse.getFirstName(), is(equalTo(CUSTOMER.getFirstName())));
        assertThat(customerResponse.getLastName(), is(equalTo(CUSTOMER.getLastName())));
    }

    @Test
    public void getCustomerNotFound() {
        context.checking(new Expectations() {
            {
                oneOf(customerRepository).findById(CUSTOMER_ID);
                will(returnValue(Optional.empty()));
            }
        });
        CustomerService service = new CustomerServiceImpl(customerRepository, bankAccountRepository);
        Assertions.assertThrows(NotFoundException.class, () ->
                service.addBankAccountToCustomer(CUSTOMER_ID, BANK_ACCOUNT_REQUEST));
    }

    @Test
    public void addBankAccountToCustomer() {
        context.checking(new Expectations() {
            {
                oneOf(customerRepository).findById(CUSTOMER_ID);
                will(returnValue(Optional.of(CUSTOMER)));
                oneOf(bankAccountRepository).findByCustomerId(CUSTOMER_ID);
                will(returnValue(Collections.singletonList(BANK_ACCOUNT)));
                oneOf(bankAccountRepository).save(NEW_BANK_ACCOUNT);
            }
        });
        CustomerService service = new CustomerServiceImpl(customerRepository, bankAccountRepository);
        service.addBankAccountToCustomer(CUSTOMER_ID, NEW_BANK_ACCOUNT_REQUEST);
    }

    @Test
    public void addSameBankAccountToCustomer() {
        context.checking(new Expectations() {
            {
                oneOf(customerRepository).findById(CUSTOMER_ID);
                will(returnValue(Optional.of(CUSTOMER)));
                oneOf(bankAccountRepository).findByCustomerId(CUSTOMER_ID);
                will(returnValue(Collections.singletonList(BANK_ACCOUNT)));
            }
        });
        CustomerService service = new CustomerServiceImpl(customerRepository, bankAccountRepository);
        Assertions.assertThrows(DataAlreadyExistsException.class, () ->
                service.addBankAccountToCustomer(CUSTOMER_ID, BANK_ACCOUNT_REQUEST));
    }

    @Test
    public void addBankAccountToCustomerNotFound() {
        context.checking(new Expectations() {
            {
                oneOf(customerRepository).findById(CUSTOMER_ID);
                will(returnValue(Optional.empty()));
            }
        });
        CustomerService service = new CustomerServiceImpl(customerRepository, bankAccountRepository);
        Assertions.assertThrows(NotFoundException.class, () ->
            service.addBankAccountToCustomer(CUSTOMER_ID, BANK_ACCOUNT_REQUEST));
    }
}