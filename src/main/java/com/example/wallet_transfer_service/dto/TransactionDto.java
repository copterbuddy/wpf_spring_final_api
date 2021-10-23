package com.example.wallet_transfer_service.dto;

import lombok.Data;

@Data
public class TransactionDto {
    String transCode;
    String fromWalletId;
    String fromWalletName;
    String toWalletId;
    String toWalletName;
    double amount;
    double fee3Amount;
    String bankCode;
    String timeStamp;
    String memo;
    String transactionToken;

}
