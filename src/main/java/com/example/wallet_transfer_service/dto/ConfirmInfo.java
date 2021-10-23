package com.example.wallet_transfer_service.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class ConfirmInfo {
    private String corebankTransType;
    private String transCode;
    private String fromWalletName;
    private String fromWalletId;
    private String fromBankCode;
    private String fromWalletType;
    private String fromWalletCurrency;
    private String toWalletName;
    private String toWalletId;
    private String toBankCode;
    private String toWalletCurrency;
    private String toWalletType;
    private BigDecimal amount;
    private BigDecimal feeCost;
    private Date timeStamp;
}
