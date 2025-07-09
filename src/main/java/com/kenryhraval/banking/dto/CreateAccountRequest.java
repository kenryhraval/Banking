package com.kenryhraval.banking.dto;

import lombok.Data;

@Data
public class CreateAccountRequest {
    private double initialBalance;
    private String description;
}
