package com.kenryhraval.banking.service;

import com.kenryhraval.banking.dto.TransactionResponse;
import com.kenryhraval.banking.model.Account;
import com.kenryhraval.banking.model.Transaction;
import com.kenryhraval.banking.model.User;
import com.kenryhraval.banking.repository.AccountRepository;
import com.kenryhraval.banking.repository.TransactionRepository;
import com.kenryhraval.banking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepo;
    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private UserRepository userRepo;

    public List<Transaction> getTransactionsForAccount(Long accountId, String username) {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        if (!account.getOwner().getUsername().equals(username)) {
            throw new SecurityException("Unauthorized access to account");
        }
        return transactionRepo.findByAccountOrderByTimestampDesc(account);
    }
}
