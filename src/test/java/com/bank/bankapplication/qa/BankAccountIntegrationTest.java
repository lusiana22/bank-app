package com.bank.bankapplication.qa;

import com.bank.bankapplication.CoinmeCodingChallengeOowfpzApplication;
import com.bank.bankapplication.data.AccountType;
import com.bank.bankapplication.data.TransferMoneyRequest;
import com.bank.bankapplication.persistence.entity.BankAccount;
import com.bank.bankapplication.persistence.entity.Customer;
import com.bank.bankapplication.persistence.entity.TransferHistory;
import com.bank.bankapplication.persistence.repository.BankAccountRepository;
import com.bank.bankapplication.persistence.repository.CustomerRepository;
import com.bank.bankapplication.persistence.repository.TransferHistoryRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoinmeCodingChallengeOowfpzApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BankAccountIntegrationTest {
    private static final String FIRST_NAME = "Lusiana";
    private static final String LAST_NAME = "LastName";
    private static final AccountType ACCOUNT_TYPE_CHECKING = AccountType.CHECKING;
    private static final AccountType ACCOUNT_TYPE_SAVINGS = AccountType.SAVINGS;
    private static final String ACCOUNT_NUMBER_CHECKING = "1234567897";
    private static final String ACCOUNT_NUMBER_SAVINGS = "9876543219";
    private static final BigDecimal BALANCE = BigDecimal.valueOf(100);
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(10);
    private static final String GET_BALANCE_URI = "/api/bank-accounts/";
    private static final String TRANSFER_MONEY_URI = "/api/bank-accounts/transfer-between-accounts";

    private final HttpHeaders headers = new HttpHeaders();

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransferHistoryRepository transferHistoryRepository;

    @Before
    public void setUp() {
        transferHistoryRepository.deleteAll();
        bankAccountRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    public void getBalance() {
        //save the customer in DB
        Customer customer = Customer.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
        customerRepository.save(customer);

        //save the bank account in DB
        BankAccount bankAccount = BankAccount.builder()
                .accountType(ACCOUNT_TYPE_CHECKING)
                .accountNumber(ACCOUNT_NUMBER_CHECKING)
                .balance(BALANCE)
                .customer(customer)
                .build();
        bankAccountRepository.save(bankAccount);

        //make the call
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<BigDecimal> response = testRestTemplate.exchange(
                formFullURLWithPort(GET_BALANCE_URI + ACCOUNT_NUMBER_CHECKING + "/get-balance"),
                HttpMethod.GET, entity, BigDecimal.class);

        //make assertions to make sure that the result is what we expect
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("100.00");
    }

    @Test
    public void transferMoney() {
        //save the customer in DB
        Customer customer = Customer.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
        customerRepository.save(customer);

        //save the bank account in DB
        BankAccount fromBankAccount = BankAccount.builder()
                .accountType(ACCOUNT_TYPE_CHECKING)
                .accountNumber(ACCOUNT_NUMBER_CHECKING)
                .balance(BALANCE)
                .customer(customer)
                .build();
        bankAccountRepository.save(fromBankAccount);

        //save the bank account in DB
        BankAccount toBankAccount = BankAccount.builder()
                .accountType(ACCOUNT_TYPE_SAVINGS)
                .accountNumber(ACCOUNT_NUMBER_SAVINGS)
                .balance(BALANCE)
                .customer(customer)
                .build();
        bankAccountRepository.save(toBankAccount);

        //build the request
        TransferMoneyRequest transferMoneyRequest = TransferMoneyRequest.builder()
                .fromAccountNumber(ACCOUNT_NUMBER_CHECKING)
                .toAccountNumber(ACCOUNT_NUMBER_SAVINGS)
                .amount(AMOUNT)
                .build();

        //make the call
        HttpEntity<TransferMoneyRequest> entity = new HttpEntity<>(transferMoneyRequest, headers);
        ResponseEntity<TransferMoneyRequest> response = testRestTemplate.exchange(formFullURLWithPort(TRANSFER_MONEY_URI),
                HttpMethod.PUT, entity, TransferMoneyRequest.class);

        //make assertions to make sure that the result is what we expect
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotEqualTo(null);

        //find the transfer history saved in DB and make assertions that the result is what we expect
        List<TransferHistory> transferHistoryList = transferHistoryRepository.findAll();
        assertThat(transferHistoryList.size()).isEqualTo(1);
        assertThat(transferHistoryList.get(0).getFromAccountNumber()).isEqualTo(ACCOUNT_NUMBER_CHECKING);
        assertThat(transferHistoryList.get(0).getToAccountNumber()).isEqualTo(ACCOUNT_NUMBER_SAVINGS);
        assertThat(transferHistoryList.get(0).getAmount()).isEqualTo("10.00");
    }

    private String formFullURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}