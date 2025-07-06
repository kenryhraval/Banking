package com.kenryhraval.banking.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/")
    public List<Account> getAccounts() {
        return accountService.getAccounts();
    }

    @GetMapping("/{id}")
    public double getBalance(@PathVariable int id) {
        return accountService.getBalance(id);
    }

    @PostMapping("/create")
    public long createAccount(@RequestParam double initialBalance) {
        return accountService.createAccount(initialBalance);
    }


    @PostMapping("/{id}/deposit")
    public void deposit(@PathVariable int id, @RequestParam double amount) {
        accountService.deposit(id, amount);
    }

    @PostMapping("/{id}/withdraw")
    public void withdraw(@PathVariable int id, @RequestParam double amount) {
        accountService.withdraw(id, amount);
    }
}
