package com.bank.bankapplication.qa;

import com.bank.bankapplication.CoinmeCodingChallengeOowfpzApplication;
import com.bank.bankapplication.data.AccountType;
import com.bank.bankapplication.data.BankAccountRequest;
import com.bank.bankapplication.data.CustomerRequest;
import com.bank.bankapplication.data.CustomerResponse;
import com.bank.bankapplication.persistence.entity.BankAccount;
import com.bank.bankapplication.persistence.entity.Customer;
import com.bank.bankapplication.persistence.repository.BankAccountRepository;
import com.bank.bankapplication.persistence.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoinmeCodingChallengeOowfpzApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerIntegrationTest {
    private static final String FIRST_NAME = "Lusiana";
    private static final String LAST_NAME = "LastName";
    private static final AccountType ACCOUNT_TYPE = AccountType.CHECKING;
    private static final String ACCOUNT_NUMBER = "4837483";
    private static final BigDecimal BALANCE = BigDecimal.valueOf(100.0);
    private static final BankAccountRequest BANK_ACCOUNT_REQUEST = BankAccountRequest.builder()
            .accountType(ACCOUNT_TYPE)
            .accountNumber(ACCOUNT_NUMBER)
            .balance(BALANCE)
            .build();
    private static final String CREATE_CUSTOMER_URI = "/api/customers/create-customer";
    private static final String GET_CUSTOMER_URI = "/api/customers/";

    private final HttpHeaders headers = new HttpHeaders();

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Before
    public void setUp() {
        bankAccountRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    public void createCustomer() {
        //build the request
        CustomerRequest customerRequest = CustomerRequest.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .bankAccountRequestList(Collections.singletonList(BANK_ACCOUNT_REQUEST))
                .build();

        //make the call
        HttpEntity<CustomerRequest> entity = new HttpEntity<>(customerRequest, headers);
        ResponseEntity<Void> response = testRestTemplate.exchange(formFullURLWithPort(CREATE_CUSTOMER_URI),
                HttpMethod.POST, entity, Void.class);

        //make assertions to make sure that the result is what we expect
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(null);

        //find the customer saved in DB and make assertions that the result is what we expect
        List<Customer> customer = customerRepository.findAll();
        assertThat(customer.get(0).getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(customer.get(0).getLastName()).isEqualTo(LAST_NAME);
    }

    @Test
    public void getCustomer() {
        //save the customer in DB
        Customer customer = Customer.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
        customerRepository.save(customer);
        //get the customer id from db to make the call later
        Long customerId = customerRepository.findAll().get(0).getId();

        //save the bank account in DB
        BankAccount bankAccount = BankAccount.builder()
                .accountType(ACCOUNT_TYPE)
                .accountNumber(ACCOUNT_NUMBER)
                .balance(BALANCE)
                .customer(customer)
                .build();
        bankAccountRepository.save(bankAccount);

        //make the call
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<CustomerResponse> response = testRestTemplate.exchange(formFullURLWithPort(GET_CUSTOMER_URI + customerId),
                HttpMethod.GET, entity, CustomerResponse.class);

        //make assertions to make sure that the result is what we expect
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(response.getBody().getLastName()).isEqualTo(LAST_NAME);
        assertThat(response.getBody().getBankAccounts().get(0).getAccountType()).isEqualTo(ACCOUNT_TYPE);
        assertThat(response.getBody().getBankAccounts().get(0).getAccountNumber()).isEqualTo(ACCOUNT_NUMBER);
    }

    private String formFullURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}