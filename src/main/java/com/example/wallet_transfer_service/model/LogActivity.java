package com.example.wallet_transfer_service.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Entity
@Data
@Table(name = "SMTR_LOG_ACTIVITY")
@EntityListeners(AuditingEntityListener.class)
public class LogActivity {

    @Id
    private int actId;
    private int actType;
    private Date actDate;
    private String actDetail;
    private String actFullDetail;
    private String userId;
    private String comname;
    private Integer tranId;
    private Integer tranType;
    private String tranCode;
    private String tranBankFrom;
    private String tranBankTo;
    private String memo;
    private String errCode;
    private String errDesc;
    private Integer actStatus;
    private String pageCode;
    private String pageName;
    @CreatedDate
    private String createdDate;
    @CreatedBy
    private String createdBy;

}
