package com.kenryhraval.banking.dto;

import com.kenryhraval.banking.model.Account;
import lombok.Data;

@Data
public class AccountResponse {
    private long id;
    private double balance;
    private String ownerUsername;

    public AccountResponse(Account account) {
        this.id = account.getId();
        this.balance = account.getBalance();
        this.ownerUsername = account.getOwner().getUsername();
    }
}
