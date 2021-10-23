package com.example.wallet_transfer_service.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import com.example.wallet_transfer_service.model.LogActivity;
import com.example.wallet_transfer_service.model.RunningID;
import com.example.wallet_transfer_service.repository.LogActivityRepository;
import com.example.wallet_transfer_service.repository.RunningIdRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class LogService {

    @Autowired
    RunningIdRepository runningIdRepository;

    @Autowired
    LogActivityRepository logActivityRepository;

    public void GetRunningId() {
        Optional<RunningID> findById = runningIdRepository.findById("TRAN_RUNNING_ID");
    }

    public void AddActivityLog(int actType, String actDetail, String actFullDetail, String userId, String comname,
            String memo, String errCode, String errDesc, String pageCode, String pageName) {

        try {
            LogActivity model = new LogActivity();
            model.setActType(actType);
            model.setActDetail(actDetail);
            model.setActFullDetail(actFullDetail);
            model.setUserId(userId);
            model.setComname(comname);
            model.setMemo(memo);
            model.setErrCode(errCode);
            model.setErrDesc(errDesc);
            model.setPageCode(pageCode);
            model.setPageName(pageName);
            model.setActDate(Timestamp.valueOf(LocalDateTime.now()));
            // model.setTranId(null);
            // model.setTranType(null);
            // model.setTransCode(null);
            // model.setTranBankFrom(null);
            // model.setTranBankTo(null);
            model.setActStatus(CheckActStatus(errCode));
            // private String createdDate;
            // private String createdBy;
            logActivityRepository.save(model);
            var aaa = "";

        } catch (Exception e) {
            // TODO: handle exception
        }
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
