package com.kenryhraval.banking.dto;

import lombok.Data;

@Data
public class DepositRequest {
    private double amount;
    private String description;
}

