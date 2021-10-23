package com.example.wallet_transfer_service.dto;

import java.util.List;

import lombok.Data;

@Data
public class CustomerListDto {
    private List<CustomerDto> customerEntity;
    private ReturnResult returnResult;

}
