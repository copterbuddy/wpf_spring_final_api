package com.example.wallet_transfer_service.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Transactional
@Slf4j
public class SystemConfigService {

    public String GetBankService() {
        var res = "";
        // List<Bank> bankList = bankRepository.findAll();
        // log.info("kunanonLog.Bsl bankList = {}", bankList);

        return res;
    }

}
