package com.kenryhraval.banking.account;

import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class AccountService {

    private static final Logger logger = LogManager.getLogger(AccountService.class);
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
        logger.info("Creating new account with initial balance {}", initialBalance);
        Account account = new Account(initialBalance);
        Account saved = accountRepository.save(account);
        return saved.getId();
    }


    public void deposit(@PathVariable long id, @RequestParam double amount) {
        logger.info("Depositing {} to account id={}", amount, id);

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        try {
            account.deposit(amount);
            accountRepository.save(account);
            logger.info("Depositing complete. New balance: {}", account.getBalance());
        } catch (IllegalArgumentException e) {
            logger.error("Depositing failed for account id={}: {}", id, e.getMessage());
            throw e;
        }
    }

    public void withdraw(@PathVariable long id, @RequestParam double amount) {
        logger.info("Withdrawing {} from account id={}", amount, id);

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        try {
            account.withdraw(amount);
            accountRepository.save(account);
            logger.info("Withdrawal complete. New balance: {}", account.getBalance());
        } catch (IllegalArgumentException e) {
            logger.error("Withdrawal failed for account id={}: {}", id, e.getMessage());
            throw e;
        }
    }
}
