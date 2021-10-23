package com.example.wallet_transfer_service.dto;

import lombok.Data;

@Data
public class WalletResponse {
    private String walletName;
    private ReturnResult returnResult;
}
