package com.bank.bankapplication.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    @Schema(description = "Customer Id")
    private Long customerId;

    @Schema(description = "First Name")
    private String firstName;

    @Schema(description = "Last Name")
    private String lastName;

    @Schema(description = "List of Bank Accounts")
    private List<BankAccountResponse> bankAccounts;
}
