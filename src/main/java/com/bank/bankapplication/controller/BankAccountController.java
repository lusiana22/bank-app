package com.bank.bankapplication.controller;

import com.bank.bankapplication.data.TransferMoneyRequest;
import com.bank.bankapplication.service.BankAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/bank-accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Operation(summary = "Get Balance for a given account number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved balance"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Unexpected system exception")
    })
    @GetMapping("/{account-number}/get-balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable("account-number") String accountNumber) {
        return ResponseEntity.ok(bankAccountService.getBalance(accountNumber));
    }

    @Operation(summary = "Transfer money")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully money transfer"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Unexpected system exception")
    })
    @PutMapping("/transfer-between-accounts")
    public ResponseEntity<TransferMoneyRequest> transferBetweenAccounts(@Validated @RequestBody TransferMoneyRequest request) {
        return ResponseEntity.ok(bankAccountService.transferBetweenAccounts(request));
    }
}
