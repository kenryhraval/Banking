package com.kenryhraval.banking.dto;

import lombok.Data;

@Data
public class DepositRequest {
    private long accountId;
    private double amount;
}

