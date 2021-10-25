package com.example.wallet_transfer_service.services;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import com.example.wallet_transfer_service.model.LogActivity;
import com.example.wallet_transfer_service.model.LogTransaction;
import com.example.wallet_transfer_service.model.RunningID;
import com.example.wallet_transfer_service.repository.LogActivityRepository;
import com.example.wallet_transfer_service.repository.LogTransactionRepository;
import com.example.wallet_transfer_service.repository.RunningIdRepository;
import com.example.wallet_transfer_service.utils.DateTimeUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;

@Component
@Transactional
public class LogService {

    @Autowired
    RunningIdRepository runningIdRepository;

    @Autowired
    LogActivityRepository logActivityRepository;

    @Autowired
    LogTransactionRepository logTransactionRepository;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    DateTimeUtil dateTimeUtil;

    public void AddActivityLog(int actType, String actDetail, String actFullDetail, String userId, String comname,
            String memo, String errCode, String errDesc, String pageCode, String pageName, Integer tranId,
            Integer tranType, String tranCode, String tranBankFrom, String tranBankTo, Date createdDate,
            String createdBy) {

        try {
            LogActivity model = new LogActivity();
            model.setActType(actType);
            model.setActDate(Timestamp.valueOf(LocalDateTime.now()));

            if (!StringUtil.isNullOrEmpty(actDetail))
                model.setActDetail(actDetail);
            if (!StringUtil.isNullOrEmpty(actFullDetail))
                model.setActFullDetail(actFullDetail);
            if (!StringUtil.isNullOrEmpty(userId))
                model.setUserId(userId);
            if (!StringUtil.isNullOrEmpty(comname))
                model.setComname(comname);

            if (!StringUtil.isNullOrEmpty(memo))
                model.setMemo(memo);

            if (!StringUtil.isNullOrEmpty(errCode))
                model.setErrCode(errCode);
            if (!StringUtil.isNullOrEmpty(errDesc))
                model.setErrDesc(errDesc);
            if (!StringUtil.isNullOrEmpty(pageCode))
                model.setPageCode(pageCode);
            if (!StringUtil.isNullOrEmpty(pageName))
                model.setPageName(pageName);

            if (tranId != null)
                model.setTranId(tranId);
            if (tranType != null)
                model.setTranType(tranType);
            if (!StringUtil.isNullOrEmpty(tranCode))
                model.setTranCode(tranCode);
            if (!StringUtil.isNullOrEmpty(tranBankFrom))
                model.setTranBankFrom(tranBankFrom);
            if (!StringUtil.isNullOrEmpty(tranBankTo))
                model.setTranBankTo(tranBankTo);

            if (!StringUtil.isNullOrEmpty(errCode))
                model.setActStatus(CheckActStatus(errCode));
            if (!StringUtil.isNullOrEmpty(createdBy))
                model.setCreatedBy(createdBy);
            if (createdDate != null)
                model.setCreatedDate(createdDate);

            logActivityRepository.save(model);

        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        }
    }

    public int AddTransactionLog(Date tranDate, String tranCode, int tranType, String custId, String citizenId,
            String mobileNo, String fromBankCode, int fromAccType, String fromAccNo, String fromAccNickName,
            String fromAccName, String toBankCode, int toAccType, String toAccNo, String toAccNickName,
            String toAccName, double fee, double amount, String currencyCode, int tranStatus, String memo,
            String errCode, String errDesc, String createdBy, Date createdDate, String updatedBy, Date updatedDate) {

        int tranId = 0;
        try {

            LogTransaction logt = new LogTransaction();
            logt.setTranDate(tranDate);
            logt.setTranCode(tranCode);
            logt.setTranType(tranType);
            logt.setCustId(custId);
            logt.setCifId(citizenId);
            logt.setMobileNo(mobileNo);
            logt.setFromBankCode(fromBankCode);
            logt.setFromAccType(fromAccType);
            logt.setFromAccNo(fromAccNo);
            logt.setFromAccNickName(fromAccNickName);
            logt.setFromAccName(fromAccName);
            logt.setToBankCode(toBankCode);
            logt.setToAccType(toAccType);
            logt.setToAccNo(toAccNo);
            logt.setToAccNickName(toAccNickName);
            logt.setToAccName(toAccName);
            logt.setFee1(fee);
            logt.setAmount(amount);
            logt.setCurrencyCode(currencyCode);
            logt.setTranStatus(tranStatus);
            logt.setMemo(memo);
            logt.setErrCode(errCode);
            logt.setErrDesc(errDesc);
            logt.setCreatedBy(createdBy);
            logt.setCreatedDate(createdDate);
            logt.setUpdatedBy(updatedBy);
            logt.setUpdatedDate(updatedDate);

            logt.setFromAccNickName(fromAccNickName);

            LogTransaction save = logTransactionRepository.save(logt);
            tranId = save.getTranId().intValue();
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        }

        return tranId;
    }

    public int CheckActStatus(String errCode) {
        int actStatus = 0;
        switch (errCode) {
        case "200":
            actStatus = 1;
            break;

        default:
            actStatus = 2;
            break;
        }

        return actStatus;
    }
}
