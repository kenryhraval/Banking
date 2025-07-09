package com.kenryhraval.banking.dto;

import lombok.Data;

@Data
public class WithdrawRequest {
    private long accountId;
    private double amount;
}
