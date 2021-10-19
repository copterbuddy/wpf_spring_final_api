package com.example.wallet_transfer_service.dto;

import java.util.List;

import lombok.Data;

@Data
public class BankListDto {
    private List<BankDto> bankList;
    private ReturnResult returnResult;
}