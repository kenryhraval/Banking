package com.kenryhraval.banking.controller;

import com.kenryhraval.banking.dto.*;
import com.kenryhraval.banking.model.Account;
import com.kenryhraval.banking.security.CanTransferFromAccount;
import com.kenryhraval.banking.security.IsAccountOwnerOrAdmin;
import com.kenryhraval.banking.security.IsAdmin;
import com.kenryhraval.banking.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Accounts", description = "Operations related to bank accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(
            summary = "Get all accounts",
            description = "Returns a list of all accounts in the system."
    )
    @GetMapping
    @IsAdmin
    public List<AccountResponse> getAllAccounts() {
        return accountService.getAllAccounts()
                .stream()
                .map(AccountResponse::new)
                .collect(Collectors.toList());
    }

    @Operation(
            summary = "Get my accounts",
            description = "Returns a list of accounts owned by the currently authenticated user."
    )
    @GetMapping("/me")
    public List<AccountResponse> getMyAccounts(Authentication authentication) {
        String username = authentication.getName();
        return accountService.getAccountsByUsername(username)
                .stream()
                .map(AccountResponse::new)
                .collect(Collectors.toList());
    }

    @Operation(
            summary = "Transfer money between accounts",
            description = "Transfers a specified amount from one account to another."
    )
    @CanTransferFromAccount
    @PostMapping("/{sourceAccountId}/transfer")
    public void transfer( @PathVariable Long sourceAccountId, @RequestBody TransferRequest request) {
        accountService.transfer(sourceAccountId, request);
    }

    @Operation(
            summary = "Get account balance",
            description = "Retrieves the balance of a specific account by ID."
    )
    @IsAccountOwnerOrAdmin
    @GetMapping("/{accountId}")
    public AccountResponse getAccount(@PathVariable Long accountId) {
        Account account = accountService.getAccount(accountId);
        return new AccountResponse(account);
    }

    @Operation(
            summary = "Deposit money",
            description = "Deposits a specified amount into the account with the given ID."
    )
    @IsAccountOwnerOrAdmin
    @PostMapping("/{accountId}/deposit")
    public void deposit(@PathVariable Long accountId, @RequestBody DepositRequest request) {
        accountService.deposit(accountId, request);
    }

    @Operation(
            summary = "Withdraw money",
            description = "Withdraws a specified amount from the account with the given ID."
    )
    @IsAccountOwnerOrAdmin
    @PostMapping("/{accountId}/withdraw")
    public void withdraw(@PathVariable Long accountId, @RequestBody WithdrawRequest request) {
        accountService.withdraw(accountId, request);
    }

    @Operation(
            summary = "Create a new account",
            description = "Creates a new account for the currently authenticated user with the given initial balance."
    )
    @PostMapping
    public long createAccount(@RequestBody CreateAccountRequest request, Authentication auth) {
        return accountService.createAccount(request, auth.getName());
    }

    @Operation(
            summary = "Delete account",
            description = "Deletes an account using a DTO object, only if owned by the authenticated user"
    )
    @IsAccountOwnerOrAdmin
    @DeleteMapping("/{accountId}")
    public void deleteAccount(@PathVariable Long accountId, @RequestBody DeleteAccountRequest request, Authentication authentication) {
        String username = authentication.getName();
        accountService.deleteAccount(accountId, request, username);
    }
}
