package com.kenryhraval.banking.dto;

import lombok.Data;

@Data
public class WithdrawRequest {
    private double amount;
    private String description;
}
