package com.example.wallet_transfer_service.model;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "SMTR_MST_CUSTOMER")
public class Customer {
    @Id
    public String custId;
    public String cifId;
    public String titleTh;
    public String firstnameTh;
    public String lastnameTh;
    public String titleEn;
    public String firstnameEn;
    public String lastnameEn;
    public Date dateOfBirth;
    public String email;
    public String mobileNo;
    public String address;
    public String gender;
    public String cifImage;
    public String signImage;
    public Boolean isDelete;
    public String passportNo;
    public String registerBranchCode;
    public String segment;
    public Boolean jointAccountStatus;
    public Boolean sensitiveCustomer;
    public Date createdDate;
    public String createdBy;
    public Date updatedDate;
    public String updatedBy;
}
