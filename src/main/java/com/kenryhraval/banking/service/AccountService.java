package com.kenryhraval.banking.service;

import com.kenryhraval.banking.dto.*;
import com.kenryhraval.banking.model.Account;
import com.kenryhraval.banking.model.User;
import com.kenryhraval.banking.repository.AccountRepository;
import com.kenryhraval.banking.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Account> getAccountsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return accountRepository.findByOwner(user);
    }

    public Account getAccount(@PathVariable long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    public long createAccount(CreateAccountRequest request, String username) {
        double initialBalance = request.getInitialBalance();

        logger.info("Creating new account with initial balance {} for user {}", initialBalance, username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Account account = new Account(initialBalance, user);
        Account saved = accountRepository.save(account);

        return saved.getId();
    }

    public void deposit(DepositRequest request) {
        double amount = request.getAmount();
        long id = request.getAccountId();

        logger.info("Depositing {} to account id={}", amount, id);

        Account account = accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Account not found"));

        try {
            account.deposit(amount);
            accountRepository.save(account);
            logger.info("Depositing complete. New balance: {}", account.getBalance());

        } catch (IllegalArgumentException e) {
            logger.error("Depositing failed for account id={}: {}", id, e.getMessage());
            throw e;
        }
    }

    public void withdraw(WithdrawRequest request) {
        double amount = request.getAmount();
        long id = request.getAccountId();

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

    public void transfer(TransferRequest request) {
        long sourceId = request.getSourceAccountId();
        long destinationId = request.getDestinationAccountId();
        double amount = request.getAmount();

        logger.info("Transferring {} from account {} to {}", amount, sourceId, destinationId);

        Account source = accountRepository.findById(sourceId)
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));
        Account destination = accountRepository.findById(destinationId)
                .orElseThrow(() -> new IllegalArgumentException("Destination account not found"));

        try {
            source.transferToAnother(destination, amount);
            accountRepository.save(source);
            accountRepository.save(destination);
            logger.info("Transfer successful. New balances: source={}, destination={}",
                    source.getBalance(), destination.getBalance());
        } catch (IllegalArgumentException e) {
            logger.error("Transfer failed: {}", e.getMessage());
            throw e;
        }
    }

    public void deleteAccount(DeleteAccountRequest request, String username) {
        long accountId = request.getAccountId();
        logger.info("User {} requested to delete account id={} (reason: {})", username, accountId, request.getReason());

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (!account.getOwner().getUsername().equals(username)) {
            logger.warn("Unauthorized delete attempt by user={} on account id={}", username, accountId);
            throw new SecurityException("You do not have permission to delete this account.");
        }

        accountRepository.delete(account);
        logger.info("Account id={} successfully deleted", accountId);
    }



}
