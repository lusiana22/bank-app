package com.bank.bankapplication.data;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;

public class CustomerRequestTest {
    private static final String FIRST_NAME = "Lusiana";
    private static final String UPDATED_FIRST_NAME = "Lucy";
    private static final String LAST_NAME = "LastName";
    private static final AccountType ACCOUNT_TYPE = AccountType.CHECKING;
    private static final String ACCOUNT_NUMBER = "4837483";
    private static final BigDecimal BALANCE = BigDecimal.TEN;
    private static final BankAccountRequest BANK_ACCOUNT_REQUEST = BankAccountRequest.builder()
            .accountType(ACCOUNT_TYPE)
            .accountNumber(ACCOUNT_NUMBER)
            .balance(BALANCE)
            .build();

    @Test
    public void createObject() {
        CustomerRequest customerRequest = CustomerRequest.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .bankAccountRequestList(Collections.singletonList(BANK_ACCOUNT_REQUEST))
                .build();
        MatcherAssert.assertThat(customerRequest.getFirstName(), Matchers.is(Matchers.sameInstance(FIRST_NAME)));
        MatcherAssert.assertThat(customerRequest.getLastName(), Matchers.is(Matchers.sameInstance(LAST_NAME)));
        MatcherAssert.assertThat(customerRequest.getBankAccountRequestList().get(0),
                Matchers.is(Matchers.sameInstance(BANK_ACCOUNT_REQUEST)));
    }

    @Test
    public void updateObject() {
        CustomerRequest customerRequest = CustomerRequest.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .bankAccountRequestList(Collections.singletonList(BANK_ACCOUNT_REQUEST))
                .build();
        customerRequest.setFirstName(UPDATED_FIRST_NAME);
        MatcherAssert.assertThat(customerRequest.getFirstName(), Matchers.is(Matchers.sameInstance(UPDATED_FIRST_NAME)));
    }
}