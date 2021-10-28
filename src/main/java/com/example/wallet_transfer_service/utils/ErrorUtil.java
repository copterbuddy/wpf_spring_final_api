package com.example.wallet_transfer_service.utils;

import java.time.Instant;

import com.example.wallet_transfer_service.dto.ReturnResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ErrorUtil {

    @Autowired
    DateTimeUtil dateTimeUtil;

    public ReturnResult SuccessResult() {
        ReturnResult result = new ReturnResult();
        result.setResultCode("200");
        result.setResult("Success");
        result.setResultDescription("Success");
        return result;
    }

    public ReturnResult Error400() {
        ReturnResult result = new ReturnResult();
        result.setResultCode("400");
        result.setResult("unauthorize");
        result.setResultDescription("กรุณากรอกข้อมูลให้ถูกต้อง");
        return result;
    }

    public ReturnResult Error401() {
        ReturnResult result = new ReturnResult();
        result.setResultCode("401");
        result.setResult("unauthorize");
        result.setResultDescription("การยืนยันตัวตนผิดพลาด");
        return result;
    }

    public ReturnResult Error402() {
        ReturnResult result = new ReturnResult();
        result.setResultCode("402");
        result.setResult("error transaction");
        result.setResultDescription("ไม่สามารถทำรายการได้ กรุณาติดต่ผู้ให้บริการ");
        return result;
    }

    public ReturnResult Error500() {
        ReturnResult result = new ReturnResult();
        result.setResultCode("500");
        result.setResult("Lost Connect");
        result.setResultDescription("ไม่สามารถเชื่อมต่อข้อมูลได้ กรุณาติดต่ผู้ให้บริการ");
        return result;
    }
}
