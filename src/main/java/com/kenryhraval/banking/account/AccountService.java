package com.kenryhraval.banking.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class AccountService {

//    private final Bank manager = new Bank();

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }


    public double getBalance(@PathVariable long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return account.getBalance();
    }

    public long createAccount(@RequestParam double initialBalance) {
        Account account = new Account(initialBalance);
        Account saved = accountRepository.save(account);
        return saved.getId();
    }


    public void deposit(@PathVariable long id, @RequestParam double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.deposit(amount);
        accountRepository.save(account);
    }

    public void withdraw(@PathVariable long id, @RequestParam double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.withdraw(amount);
        accountRepository.save(account);
    }
}
