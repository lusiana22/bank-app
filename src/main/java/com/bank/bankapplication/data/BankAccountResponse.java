package com.bank.bankapplication.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountResponse {

    @Schema(description = "Account Number")
    private String accountNumber;

    @Schema(description = "Account Type")
    private AccountType accountType;

    @Schema(description = "Balance")
    private BigDecimal balance;
}
