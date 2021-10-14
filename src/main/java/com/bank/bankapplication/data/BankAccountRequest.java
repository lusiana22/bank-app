package com.bank.bankapplication.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountRequest {

    @Schema(description = "Account Type which can be either SAVINGS or CHECKING", required = true)
    @NotNull
    private AccountType accountType;

    @Schema(description = "The Account Number", minLength = 5, maxLength = 5, required = true)
    @Size(min = 6, max = 6, message = "The account number should have 5 numbers")
    private String accountNumber;

    @Schema(description = "The Initial Balance")
    @DecimalMin("0.00")
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;
}