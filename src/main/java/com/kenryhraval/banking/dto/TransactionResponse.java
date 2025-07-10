package com.kenryhraval.banking.dto;

import com.kenryhraval.banking.enums.TransactionType;
import com.kenryhraval.banking.model.Transaction;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TransactionResponse {
//    private Long accountId;
    private TransactionType type;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String description;
    private BigDecimal balanceAfter;
    private Long relatedAccountId;

    public TransactionResponse(Transaction transaction) {
//        this.accountId = transaction.getAccount().getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.timestamp = transaction.getTimestamp();
        this.description = transaction.getDescription();
        this.balanceAfter = transaction.getBalanceAfter();
        this.relatedAccountId = (transaction.getRelatedAccount() != null) ? transaction.getRelatedAccount().getId() : null;
    }
}
