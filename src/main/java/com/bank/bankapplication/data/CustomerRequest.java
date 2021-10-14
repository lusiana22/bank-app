package com.bank.bankapplication.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    @Schema(description = "The first name", required = true)
    @NotBlank(message = "First Name cannot be null or blank")
    private String firstName;

    @Schema(description = "The last name", required = true)
    @NotBlank(message = "Last name cannot be null or blank")
    private String lastName;

    @Schema(description = "The list of bank accounts that customer wants to open", minimum = "1", required = true)
    @NotEmpty(message = "No bank account in this request. You should provide at least 1")
    List<BankAccountRequest> bankAccountRequestList;
}
