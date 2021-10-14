package com.bank.bankapplication.controller;

import com.bank.bankapplication.data.AccountType;
import com.bank.bankapplication.data.BankAccountRequest;
import com.bank.bankapplication.data.CustomerRequest;
import com.bank.bankapplication.data.CustomerResponse;
import com.bank.bankapplication.persistence.entity.Customer;
import com.bank.bankapplication.service.CustomerService;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CustomerControllerTest {
    private static final Long CUSTOMER_ID = 22L;
    private static final String FIRST_NAME = "Lusiana";
    private static final String LAST_NAME = "Velcani";
    private static final Customer CUSTOMER = Customer.builder()
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .build();
    private static final CustomerResponse CUSTOMER_RESPONSE = CustomerResponse.builder()
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .build();
    private static final String ACCOUNT_NUMBER_REQUEST = "546547";
    private static final AccountType ACCOUNT_TYPE = AccountType.CHECKING;
    private static final BigDecimal BALANCE = BigDecimal.TEN;
    private static final BankAccountRequest BANK_ACCOUNT_REQUEST = BankAccountRequest.builder()
            .accountType(ACCOUNT_TYPE)
            .accountNumber(ACCOUNT_NUMBER_REQUEST)
            .balance(BALANCE)
            .build();
    private static final CustomerRequest CUSTOMER_REQUEST = CustomerRequest.builder()
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .bankAccountRequestList(Collections.singletonList(BANK_ACCOUNT_REQUEST))
            .build();
    private CustomerService customerService;

    @RegisterExtension
    Mockery context = new JUnit5Mockery();

    @BeforeEach
    public void setUp() {
        customerService = context.mock(CustomerService.class);
    }

    @Test
    public void createCustomer() {
        context.checking(new Expectations() {
            {
                oneOf(customerService).createCustomer(CUSTOMER_REQUEST);
                will(returnValue(CUSTOMER));
            }
        });
        CustomerController controller = new CustomerController(customerService);
        ResponseEntity<Void> responseEntity = controller.createCustomer(CUSTOMER_REQUEST);
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.CREATED)));
        assertThat(responseEntity.getStatusCodeValue(), is(equalTo(201)));
        assertThat(responseEntity.getBody(), is(equalTo(null)));
    }

    @Test
    public void getCustomer() {
        context.checking(new Expectations() {
            {
                oneOf(customerService).getCustomer(CUSTOMER_ID);
                will(returnValue(CUSTOMER_RESPONSE));
            }
        });
        CustomerController controller = new CustomerController(customerService);
        ResponseEntity<CustomerResponse> responseEntity = controller.getCustomer(CUSTOMER_ID);
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.OK)));
        assertThat(responseEntity.getStatusCodeValue(), is(equalTo(200)));
        assertThat(responseEntity.getBody(), is(equalTo(CUSTOMER_RESPONSE)));
    }

    @Test
    public void addBankAccountToCustomer() {
        context.checking(new Expectations() {
            {
                oneOf(customerService).addBankAccountToCustomer(CUSTOMER_ID, BANK_ACCOUNT_REQUEST);
                will(returnValue(BANK_ACCOUNT_REQUEST));
            }
        });
        CustomerController controller = new CustomerController(customerService);
        ResponseEntity<BankAccountRequest> responseEntity = controller.addBankAccountToCustomer(CUSTOMER_ID, BANK_ACCOUNT_REQUEST);
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.OK)));
        assertThat(responseEntity.getStatusCodeValue(), is(equalTo(200)));
        assertThat(responseEntity.getBody(), is(equalTo(BANK_ACCOUNT_REQUEST)));
    }
}