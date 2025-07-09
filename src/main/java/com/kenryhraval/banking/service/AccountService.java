package com.kenryhraval.banking.service;

import com.kenryhraval.banking.dto.*;
import com.kenryhraval.banking.exception.AccountNotFoundException;
import com.kenryhraval.banking.exception.UserNotFoundException;
import com.kenryhraval.banking.model.Account;
import com.kenryhraval.banking.model.Transaction;
import com.kenryhraval.banking.model.User;
import com.kenryhraval.banking.repository.AccountRepository;
import com.kenryhraval.banking.repository.TransactionRepository;
import com.kenryhraval.banking.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kenryhraval.banking.enums.TransactionType;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Account> getAccountsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return accountRepository.findByOwner(user);
    }

    public Account getAccount(long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    public long createAccount(CreateAccountRequest request, String username) {
        double initialBalance = request.getInitialBalance();
        String description = request.getDescription();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        Account account = new Account(initialBalance, user);
        Account saved = accountRepository.save(account);

        Transaction tx = new Transaction(
                account,
                TransactionType.CREATE,
                BigDecimal.valueOf(initialBalance),
                description,
                BigDecimal.valueOf(initialBalance)
        );
        transactionRepository.save(tx);

        return saved.getId();
    }

    public void deposit(long id, DepositRequest request) {
        double amount = request.getAmount();
        String description = request.getDescription();

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));


        account.deposit(amount);
        accountRepository.save(account);

        Transaction tx = new Transaction(
                account,
                TransactionType.DEPOSIT,
                BigDecimal.valueOf(amount),
                description,
                BigDecimal.valueOf(account.getBalance())
        );
        transactionRepository.save(tx);

    }

    public void withdraw(long id, WithdrawRequest request) {
        double amount = request.getAmount();
        String description = request.getDescription();

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        account.withdraw(amount);
        accountRepository.save(account);

        Transaction tx = new Transaction(
                account,
                TransactionType.WITHDRAW,
                BigDecimal.valueOf(amount),
                description,
                BigDecimal.valueOf(account.getBalance())
        );
        transactionRepository.save(tx);

    }


    public void transfer(long sourceId, TransferRequest request) {
        long destinationId = request.getDestinationAccountId();
        double amount = request.getAmount();
        String description = request.getDescription();

        Account source = accountRepository.findById(sourceId)
                .orElseThrow(() -> new AccountNotFoundException(sourceId));
        Account destination = accountRepository.findById(destinationId)
                .orElseThrow(() -> new AccountNotFoundException(destinationId));


        source.transferToAnother(destination, amount);
        accountRepository.save(source);
        accountRepository.save(destination);

        Transaction tx1 = new Transaction(
                source,
                TransactionType.TRANSFER_OUT,
                BigDecimal.valueOf(amount),
                description,
                destination,
                BigDecimal.valueOf(source.getBalance())
        );
        Transaction tx2 = new Transaction(
                destination,
                TransactionType.TRANSFER_IN,
                BigDecimal.valueOf(amount),
                description,
                source,
                BigDecimal.valueOf(destination.getBalance())
        );
        transactionRepository.save(tx1);
        transactionRepository.save(tx2);

    }

    public void deleteAccount(long id, DeleteAccountRequest request, String username) {
        String description = request.getDescription();

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        account.setDeleted(true);
        accountRepository.save(account);

        Transaction tx = new Transaction(
                account,
                TransactionType.WITHDRAW,
                BigDecimal.valueOf(0),
                description,
                BigDecimal.valueOf(0)
        );
        transactionRepository.save(tx);
    }

    public boolean isOwner(Long accountId, String username) {
        Account account = accountRepository.findById(accountId).orElse(null);
        return account != null && account.getOwner().getUsername().equals(username);
    }




}
