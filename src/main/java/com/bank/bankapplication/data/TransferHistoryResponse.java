package com.bank.bankapplication.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferHistoryResponse {

    @Schema(description = "From Account Number")
    private String fromAccountNumber;

    @Schema(description = "To Account Number")
    private String toAccountNumber;

    @Schema(description = "Amount transferred")
    private BigDecimal amount;

    @Schema(description = "Date Of Transfer")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;
}