package com.example.wallet_transfer_service.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.type.Date;


import lombok.Data;

@Entity
@Data
@Table(name = "SMTR_LOG_TRANSACTION")
public class LogTransaction {
    @Id
    private BigInteger tranId;
    private Date tranDate;
    private String tranCode;
    private int tranType;
    private String custId;
    private String cifId;
    private String mobileNo;
    private String fromBankCode;
    private String fromAccType;
    private String fromAccNo;
    private String fromAccNickName;
    private String toBankCode;
    private String toAccType;
    private String toAccNo;
    private String toAccNickName;
    private BigDecimal fee1;
    private BigDecimal amount;
    private String currencyCode;
    private int tranStatus;
    private String memo;
    private String errCode;
    private String errDesc;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;

}
