package com.kenryhraval.banking.repository;

import com.kenryhraval.banking.model.Transaction;
import com.kenryhraval.banking.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountOrderByTimestampDesc(Account account);
}
