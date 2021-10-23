package com.example.wallet_transfer_service.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class WalletDto {
    private String walletId;
    private String userId;
    private String cardNo;
    private String walletName;
    private String walletType;
    private BigDecimal balance;
    private BigDecimal availableBalance;
    private String walletCurrency;
    private String bankCode;
    private String walletStatus;
    private String createDate;
    private ReturnResult returnResult;
}
