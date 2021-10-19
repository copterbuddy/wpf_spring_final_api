package com.example.wallet_transfer_service.utils;

import com.example.wallet_transfer_service.dto.ReturnResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ErrorUtil {

    @Autowired
    DateTimeUtil dateTimeUtil;

    public ReturnResult SuccessResult() {
        ReturnResult result = new ReturnResult();
        result.setResultCode("0000");
        result.setResult("0000 Success");
        result.setResultDescription("");
        result.setErrorRefId("");
        // result.setResultTimeStamp(dateTimeUtil.GetDateTimeNow());
        return result;
    }
}
