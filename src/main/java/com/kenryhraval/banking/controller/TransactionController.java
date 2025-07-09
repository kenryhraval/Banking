package com.kenryhraval.banking.controller;

import com.kenryhraval.banking.dto.AccountResponse;
import com.kenryhraval.banking.dto.TransactionRequest;
import com.kenryhraval.banking.dto.TransactionResponse;
import com.kenryhraval.banking.model.Transaction;
import com.kenryhraval.banking.security.IsAccountOwnerOrAdmin;
import com.kenryhraval.banking.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Operation(summary = "Get all transactions for an account owned by current user")
    @IsAccountOwnerOrAdmin
    @GetMapping("/{accountId}")
    public List<TransactionResponse> getAccountTransactions(@PathVariable Long accountId, Authentication authentication) {
        return transactionService.getTransactionsForAccount(accountId, authentication.getName())
            .stream()
            .map(TransactionResponse::new)
            .collect(Collectors.toList());
    }
}
