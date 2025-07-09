package com.kenryhraval.banking.dto;

import lombok.Data;

@Data
public class TransferRequest {
    private long destinationAccountId;
    private double amount;
    private String description;
}
