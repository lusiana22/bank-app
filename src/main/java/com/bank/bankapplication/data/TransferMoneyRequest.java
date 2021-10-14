package com.bank.bankapplication.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferMoneyRequest {

    @Schema(description = "From Account Number", minLength = 8, maxLength = 10)
    @Size(min = 10, max = 10)
    private String fromAccountNumber;

    @Schema(description = "From Account Number, minLength = 8, maxLength = 8")
    @Size(min = 10, max = 10)
    private String toAccountNumber;

    @Schema(description = "From Account Number")
    @NotNull
    private BigDecimal amount;
}