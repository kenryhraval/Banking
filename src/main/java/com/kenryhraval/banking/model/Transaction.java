package com.kenryhraval.banking.model;

import com.kenryhraval.banking.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private BigDecimal amount;

    private LocalDateTime timestamp;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_account_id")
    private Account relatedAccount;

    private BigDecimal balanceAfter;

    public Transaction(
            Account account,
            TransactionType type,
            BigDecimal amount,
            String description,
            Account relatedAccount,
            BigDecimal balanceAfter
    ) {
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.description = description;
        this.relatedAccount = relatedAccount;
        this.balanceAfter = balanceAfter;
    }

    public Transaction(
            Account account,
            TransactionType type,
            BigDecimal amount,
            String description,
            BigDecimal balanceAfter
    ) {
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.description = description;
        this.relatedAccount = null;
        this.balanceAfter = balanceAfter;
    }
}
