package com.example.wallet_transfer_service.model;

import java.util.Base64;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "SMTR_MST_BANK")
public class Bank {

    @Id
    public String bankCode;
    public String bankNameTh;
    public String bankNameEn;
    public String bankAbbr;
    public int seqNo;
    public String imgPath;
    public int status;
    public Date createdDate;
    public String createdBy;
    public Date updatedDate;
    public String updatedBy;
}
