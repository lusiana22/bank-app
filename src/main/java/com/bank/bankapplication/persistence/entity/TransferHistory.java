package com.bank.bankapplication.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TRANSFER_HISTORY")
public class TransferHistory extends BaseEntity {

    @Column(name = "FROM_ACCOUNT_NUMBER")
    private String fromAccountNumber;

    @Column(name = "TO_ACCOUNT_NUMBER")
    private String toAccountNumber;

    @Column(name = "AMOUNT")
    private BigDecimal amount;
}
