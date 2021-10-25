package com.example.wallet_transfer_service.model;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "SMTR_RUNNING_ID")
public class RunningID {
    @Id
    private String runningType;
    private String prefix;
    private String suffix;
    private String dayRunning;
    private String monthRunning;
    private String yearRunning;
    // @Id
    private Integer lastRunningId;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private int minRunningLength;
    private int maxRunningLength;
    private int currentRunningLength;
}
