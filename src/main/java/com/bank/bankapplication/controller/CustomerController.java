package com.bank.bankapplication.controller;

import com.bank.bankapplication.data.BankAccountRequest;
import com.bank.bankapplication.data.CustomerRequest;
import com.bank.bankapplication.data.CustomerResponse;
import com.bank.bankapplication.persistence.entity.Customer;
import com.bank.bankapplication.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(value = "api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "Create customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a customer"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Unexpected system exception")
    })
    @PostMapping(value = "/create-customer")
    public ResponseEntity<Void> createCustomer(@Validated @RequestBody CustomerRequest customerRequest) {
        Customer customer = customerService.createCustomer(customerRequest);
        URI location = URI.create(String.format("/api/customers/%s", customer.getId()));
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Retrieve customer given the customer id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved customer"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Unexpected system exception")
    })
    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable("customer-id") Long customerId) {
        return ResponseEntity.ok(customerService.getCustomer(customerId));
    }

    @Operation(summary = "Add bank account to existing customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added bank account"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Unexpected system exception")
    })
    @PostMapping("/{customer-id}/add-bank-account-to-customer")
    public ResponseEntity<BankAccountRequest> addBankAccountToCustomer(@PathVariable("customer-id") Long customerId,
                                                                       @Validated @RequestBody BankAccountRequest bankAccountRequest) {
        return ResponseEntity.ok(customerService.addBankAccountToCustomer(customerId, bankAccountRequest));
    }
}