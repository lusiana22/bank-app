package com.bank.bankapplication.persistence.entity;

import com.bank.bankapplication.data.AccountType;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class BankAccountTest {
    private static final String ACCOUNT_NUMBER = "546547";
    private static final AccountType ACCOUNT_TYPE = AccountType.CHECKING;
    private static final BigDecimal INITIAL_BALANCE = BigDecimal.TEN;
    private static final BigDecimal NEW_INITIAL_BALANCE = BigDecimal.valueOf(100);
    private static final String FIRST_NAME = "Lusiana";
    private static final String LAST_NAME = "Velcani";
    private static final Customer CUSTOMER = Customer.builder()
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .build();

    @Test
    public void createObject() {
        BankAccount bankAccount = BankAccount.builder()
                .accountNumber(ACCOUNT_NUMBER)
                .accountType(ACCOUNT_TYPE)
                .balance(INITIAL_BALANCE)
                .customer(CUSTOMER)
                .build();
        MatcherAssert.assertThat(bankAccount.getAccountNumber(), Matchers.is(Matchers.sameInstance(ACCOUNT_NUMBER)));
        MatcherAssert.assertThat(bankAccount.getAccountType(), Matchers.is(Matchers.sameInstance(ACCOUNT_TYPE)));
        MatcherAssert.assertThat(bankAccount.getBalance(), Matchers.is(Matchers.sameInstance(INITIAL_BALANCE)));
        MatcherAssert.assertThat(bankAccount.getCustomer(), Matchers.is(Matchers.sameInstance(CUSTOMER)));
    }

    @Test
    public void updateObject() {
        BankAccount bankAccount = BankAccount.builder()
                .accountNumber(ACCOUNT_NUMBER)
                .accountType(ACCOUNT_TYPE)
                .balance(INITIAL_BALANCE)
                .customer(CUSTOMER)
                .build();
        bankAccount.setBalance(NEW_INITIAL_BALANCE);
        MatcherAssert.assertThat(bankAccount.getBalance(), Matchers.is(Matchers.sameInstance(NEW_INITIAL_BALANCE)));
    }
}