package com.example.wallet_transfer_service.dto;

import lombok.Data;

@Data
public class TransactionCoreBankResponse {
    TransactionDto confirmInfo;
    ReturnResult returnResult;
}
