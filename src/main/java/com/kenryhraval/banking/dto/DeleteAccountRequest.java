package com.kenryhraval.banking.dto;

import lombok.Data;

@Data
public class DeleteAccountRequest {
    private long accountId;
    private String reason; // Optional, just to illustrate extensibility
}
