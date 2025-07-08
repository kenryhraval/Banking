package com.kenryhraval.banking.controller;

import com.kenryhraval.banking.dto.TransferRequest;
import com.kenryhraval.banking.model.Account;
import com.kenryhraval.banking.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Accounts", description = "Operations related to bank accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/all")
    @Operation(
            summary = "Get all accounts",
            description = "Returns a list of all accounts in the system."
    )
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/")
    @Operation(
            summary = "Get my accounts",
            description = "Returns a list of accounts owned by the currently authenticated user."
    )
    public List<Account> getMyAccounts(Authentication authentication) {
        String username = authentication.getName();
        return accountService.getAccountsByUsername(username);
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Get account balance",
            description = "Retrieves the balance of a specific account by ID."
    )
    public double getBalance(@PathVariable int id) {
        return accountService.getBalance(id);
    }

    @PostMapping("/create")
    @Operation(
            summary = "Create a new account",
            description = "Creates a new account for the currently authenticated user with the given initial balance."
    )
    public long createAccount(@RequestParam double initialBalance, Authentication authentication) {
        String username = authentication.getName();
        return accountService.createAccount(initialBalance, username);
    }


    @PostMapping("/deposit")
    @Operation(
            summary = "Deposit money",
            description = "Deposits a specified amount into the account with the given ID."
    )
    public void deposit(@RequestParam int id, @RequestParam double amount) {
        accountService.deposit(id, amount);
    }

    @PostMapping("/withdraw")
    @Operation(
            summary = "Withdraw money",
            description = "Withdraws a specified amount from the account with the given ID."
    )
    public void withdraw(@RequestParam int id, @RequestParam double amount) {
        accountService.withdraw(id, amount);
    }

    @PostMapping("/transfer")
    @Operation(
            summary = "Transfer money between accounts",
            description = "Transfers a specified amount from one account to another."
    )
    public void transfer(@RequestBody TransferRequest request) {
        accountService.transfer(
                request.getSourceAccountId(),
                request.getDestinationAccountId(),
                request.getAmount()
        );
    }

}
