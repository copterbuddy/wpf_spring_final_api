package com.example.wallet_transfer_service.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ReturnResult {
    private String result;
    private String resultCode;
    private String resultDescription;
    private String errorRefId;
    private Date resultTimeStamp;
}