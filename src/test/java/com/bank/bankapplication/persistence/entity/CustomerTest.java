package com.bank.bankapplication.persistence.entity;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class CustomerTest {
    private static final String FIRST_NAME = "Lusiana";
    private static final String UPDATED_FIRST_NAME = "Lucy";
    private static final String LAST_NAME = "Velcani";
    private static final String UPDATED_LAST_NAME = "Vel";

    @Test
    public void createObject() {
        Customer customer = Customer.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
        MatcherAssert.assertThat(customer.getFirstName(), Matchers.is(Matchers.sameInstance(FIRST_NAME)));
        MatcherAssert.assertThat(customer.getLastName(), Matchers.is(Matchers.sameInstance(LAST_NAME)));
    }

    @Test
    public void updateObject() {
        Customer customer = Customer.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
        customer.setFirstName(UPDATED_FIRST_NAME);
        customer.setLastName(UPDATED_LAST_NAME);
        MatcherAssert.assertThat(customer.getFirstName(), Matchers.is(Matchers.sameInstance(UPDATED_FIRST_NAME)));
        MatcherAssert.assertThat(customer.getLastName(), Matchers.is(Matchers.sameInstance(UPDATED_LAST_NAME)));

    }
}