package com.example.wallet_transfer_service.dto;

import lombok.Data;

@Data
public class PreTransferRequest {
    private String transCode;
    private String corebankTransType;
    private String channel;
    private String fromWalletId;
    private String toWalletId;
    private double amount;
    private String fee3Code;
    private double fee3Amount;
}
