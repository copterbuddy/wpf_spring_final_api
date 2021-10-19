package com.example.wallet_transfer_service.controllersGrpc;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;

import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.example.helloworld.HelloRequest.Builder;
import com.example.systemConfigGrpc.*;
import com.example.systemConfigGrpc.BankListResponse.BankList;
import com.example.systemConfigGrpc.BankListResponse.ReturnResult;
import com.example.wallet_transfer_service.dto.BankDto;
import com.example.wallet_transfer_service.dto.BankListDto;
import com.example.wallet_transfer_service.model.Bank;
import com.example.wallet_transfer_service.services.SystemConfigService;
import com.example.wallet_transfer_service.utils.DateTimeUtil;
import com.google.protobuf.Timestamp;

@GRpcService
@Slf4j
public class SystemConfigServiceImpl extends GetBankListServiceGrpc.GetBankListServiceImplBase {

    @Autowired
    SystemConfigService systemConfigService;

    @Autowired
    DateTimeUtil dateTimeUtil;

    @Override
    public void getBankList(EmptyRequest request, StreamObserver<BankListResponse> responseObserver) {
        var response = BankListResponse.newBuilder();

        try {

            // TODO: CallApi
            BankListDto resultList = systemConfigService.GetBankService();

            // TODO: Map To BankList
            if (resultList != null && resultList.getBankList() != null && resultList.getReturnResult() != null
                    && resultList.getBankList().size() > 0) {
                for (BankDto item : resultList.getBankList()) {
                    var bank = BankList.newBuilder().setBankCode(item.getBankCode()).setBankName(item.getBankNameTh())
                            .setBankImage(item.getImgPath());
                    response.addBankList(bank);
                }

                var errorEntity = ReturnResult.newBuilder();
                errorEntity.setResultCode(resultList.getReturnResult().getResultCode());
                errorEntity.setResult(resultList.getReturnResult().getResult());
                errorEntity.setResultDescription(resultList.getReturnResult().getResultDescription());
                errorEntity.setErrorRefId(resultList.getReturnResult().getErrorRefId());
                errorEntity.setResultTimestamp(dateTimeUtil.GetTimeStamp());

                response.setReturnResult(errorEntity);

            } else {
                // TODO: Handle Error
            }

            // TODO: Return
            responseObserver.onNext(response.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            // TODO: handle exception
            responseObserver.onError(e);
        }

    }
}
