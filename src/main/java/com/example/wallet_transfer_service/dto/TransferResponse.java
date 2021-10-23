package com.example.wallet_transfer_service.dto;

import lombok.Data;

@Data
public class TransferResponse {
    private String transUuid;
    private ConfirmInfo confirmInfo;
    private ReturnResult returnResult;
}
