package com.bank.bankapplication.persistence.repository;

import com.bank.bankapplication.persistence.entity.TransferHistory;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TransferHistoryRepositoryTest {
    private static final String FROM_ACCOUNT_NUMBER = "43874";
    private static final String TO_ACCOUNT_NUMBER = "12345";
    private static final String ACCOUNT_NUMBER = "12345";
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(200);
    private static final TransferHistory TRANSFER_HISTORY = TransferHistory.builder()
            .fromAccountNumber(FROM_ACCOUNT_NUMBER)
            .toAccountNumber(TO_ACCOUNT_NUMBER)
            .amount(AMOUNT)
            .build();

    @Autowired
    private TransferHistoryRepository transferHistoryRepository;

    @BeforeEach
    public void cleanup() {
        transferHistoryRepository.deleteAll();
    }

    @Test
    public void findByFromAccountNumberOrToAccountNumber() {
        transferHistoryRepository.save(TRANSFER_HISTORY);
        List<TransferHistory> transferHistoryList =
                transferHistoryRepository.findByFromAccountNumberOrToAccountNumber(ACCOUNT_NUMBER, ACCOUNT_NUMBER);
        MatcherAssert.assertThat(transferHistoryList.size(), Matchers.is(Matchers.equalTo(1)));
        MatcherAssert.assertThat(transferHistoryList.get(0), Matchers.is(Matchers.sameInstance(TRANSFER_HISTORY)));
    }

    @Test
    public void findByFromAccountNumberOrToAccountNumberNoResult() {
        List<TransferHistory> transferHistoryList =
                transferHistoryRepository.findByFromAccountNumberOrToAccountNumber(ACCOUNT_NUMBER, ACCOUNT_NUMBER);
        MatcherAssert.assertThat(transferHistoryList.size(), Matchers.is(Matchers.equalTo(0)));
    }
}