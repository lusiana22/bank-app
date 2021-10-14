package com.bank.bankapplication.data;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;

public class CustomerResponseTest {
    private static final String FIRST_NAME = "Lusiana";
    private static final String UPDATED_FIRST_NAME = "Lucy";
    private static final String LAST_NAME = "Velcani";
    private static final String ACCOUNT_NUMBER = "465475";
    private static final AccountType ACCOUNT_TYPE = AccountType.CHECKING;
    private static final BigDecimal BALANCE = BigDecimal.ZERO;
    private static final BankAccountResponse BANK_ACCOUNT = BankAccountResponse.builder()
            .accountNumber(ACCOUNT_NUMBER)
            .accountType(ACCOUNT_TYPE)
            .balance(BALANCE)
            .build();

    @Test
    public void createObject() {
        CustomerResponse customerResponse = CustomerResponse.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .bankAccounts(Collections.singletonList(BANK_ACCOUNT))
                .build();
        MatcherAssert.assertThat(customerResponse.getFirstName(), Matchers.is(Matchers.sameInstance(FIRST_NAME)));
        MatcherAssert.assertThat(customerResponse.getLastName(), Matchers.is(Matchers.sameInstance(LAST_NAME)));
        MatcherAssert.assertThat(customerResponse.getBankAccounts().get(0), Matchers.is(Matchers.sameInstance(BANK_ACCOUNT)));
    }

    @Test
    public void updateObject() {
        CustomerResponse customerResponse = CustomerResponse.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .bankAccounts(Collections.singletonList(BANK_ACCOUNT))
                .build();
        customerResponse.setFirstName(UPDATED_FIRST_NAME);
        MatcherAssert.assertThat(customerResponse.getFirstName(), Matchers.is(Matchers.sameInstance(UPDATED_FIRST_NAME)));
    }
}