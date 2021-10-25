package com.example.wallet_transfer_service.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "SMTR_LOG_TRANSACTION")
public class LogTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger tranId;
    private Date tranDate;
    private String tranCode;
    private int tranType;
    private String custId;
    private String cifId;
    private String mobileNo;
    private String fromBankCode;
    private int fromAccType;
    private String fromAccNo;
    private String fromAccName;
    private String fromAccNickName;
    private String toBankCode;
    private int toAccType;
    private String toAccNo;
    private String toAccName;
    private String toAccNickName;
    private double fee1;
    private double amount;
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
