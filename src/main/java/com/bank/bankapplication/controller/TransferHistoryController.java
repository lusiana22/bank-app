package com.bank.bankapplication.controller;

import com.bank.bankapplication.data.TransferHistoryResponse;
import com.bank.bankapplication.service.TransferHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transfer-history")
public class TransferHistoryController {

    private final TransferHistoryService transferHistoryService;

    @Autowired
    public TransferHistoryController(TransferHistoryService transferHistoryService) {
        this.transferHistoryService = transferHistoryService;
    }

    @Operation(summary = "Retrieve transfer history for a given account number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved transfer history"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Unexpected system exception")
    })
    @GetMapping("/{account-number}/get-transfer-history")
    public ResponseEntity<List<TransferHistoryResponse>> getTransferHistory(@PathVariable("account-number") String accountNumber) {
        return ResponseEntity.ok(transferHistoryService.getTransferHistory(accountNumber));
    }
}