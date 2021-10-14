package com.bank.bankapplication.persistence.repository;

import com.bank.bankapplication.data.AccountType;
import com.bank.bankapplication.persistence.entity.BankAccount;
import com.bank.bankapplication.persistence.entity.Customer;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BankAccountRepositoryTest {
    private static final String ACCOUNT_NUMBER = "546547";
    private static final AccountType ACCOUNT_TYPE = AccountType.CHECKING;
    private static final BigDecimal INITIAL_BALANCE = BigDecimal.TEN;
    private static final String FIRST_NAME = "Lusiana";
    private static final String LAST_NAME = "Velcani";
    private static final Customer CUSTOMER = Customer.builder()
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .build();
    private static final BankAccount BANK_ACCOUNT = BankAccount.builder()
            .accountNumber(ACCOUNT_NUMBER)
            .accountType(ACCOUNT_TYPE)
            .balance(INITIAL_BALANCE)
            .customer(CUSTOMER)
            .build();

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void cleanup() {
        customerRepository.deleteAll();
        bankAccountRepository.deleteAll();
    }

    @Test
    public void findByAccountNumber() {
        customerRepository.save(CUSTOMER);
        bankAccountRepository.save(BANK_ACCOUNT);
        Optional<BankAccount> bankAccount = bankAccountRepository.findByAccountNumber(ACCOUNT_NUMBER);
        MatcherAssert.assertThat(bankAccount.isPresent(), Matchers.is(Matchers.equalTo(true)));
        MatcherAssert.assertThat(bankAccount.get(), Matchers.is(Matchers.sameInstance(BANK_ACCOUNT)));
    }

    @Test
    public void findByAccountNumberNoResult() {
        Optional<BankAccount> bankAccount = bankAccountRepository.findByAccountNumber(ACCOUNT_NUMBER);
        MatcherAssert.assertThat(bankAccount.isPresent(), Matchers.is(Matchers.equalTo(false)));
    }
}