package com.example.wallet_transfer_service.model;

import java.util.Base64;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Emb
@Table(name = "[SMTR_MST_BANK]")
public class Bank {

    public String bank_code;
    public String bank_name_th;
    public String bank_name_en;
    public String bank_abbr;
    public int seq_no;
    public Base64 img_path;
    public int status;
    public Date created_date;
    public String created_by;
    public Date updated_date;
    public String updated_by;
}
