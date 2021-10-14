package com.bank.bankapplication.persistence.repository;

import com.bank.bankapplication.persistence.entity.TransferHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferHistoryRepository extends JpaRepository<TransferHistory, Long> {

    List<TransferHistory> findByFromAccountNumberOrToAccountNumber(String fromAccountNumber, String toAccountNumber);
}
