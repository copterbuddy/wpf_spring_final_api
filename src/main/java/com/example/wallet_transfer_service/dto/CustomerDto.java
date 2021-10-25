package com.example.wallet_transfer_service.dto;

import lombok.Data;

@Data
public class CustomerDto {

    private String custId;
    private String cifId;
    private String branch;
    private String titleTh;
    private String firstnameTh;
    private String lastnameTh;
    private String segment;
    private Boolean jointAccountStatus;
    private Boolean sensitiveCustomer;
    private String cifImage;
    private String signImage;
    public String mobileNo;

}
