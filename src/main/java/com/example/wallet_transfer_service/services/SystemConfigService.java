package com.example.wallet_transfer_service.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.example.wallet_transfer_service.dto.BankDto;
import com.example.wallet_transfer_service.dto.BankListDto;
import com.example.wallet_transfer_service.model.Bank;
import com.example.wallet_transfer_service.repository.BankRepository;
import com.example.wallet_transfer_service.utils.ErrorUtil;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Transactional
@Slf4j
public class SystemConfigService {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private ErrorUtil errorUtil;

    public BankListDto GetBankService() {

        // TODO: Create Response
        BankListDto response = new BankListDto();

        // TODO: Get BankList
        List<Bank> bankList = bankRepository.findAll();

        // TODO: Pass
        if (bankList != null && bankList.size() > 0) {
            ModelMapper mapper = new ModelMapper();
            List<BankDto> bankListDto = bankList.stream().map(o -> mapper.map(o, BankDto.class))
                    .collect(Collectors.toList());

            response.setBankList(bankListDto);

            response.setReturnResult(errorUtil.SuccessResult());

        }

        // TODO: Failed
        log.info("kunanonLog.Bsl bankList.size = {}", bankList.size());

        return response;
    }

}
