package com.example.wallet_transfer_service.controllersGrpc;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;

import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;
import io.grpc.stub.StreamObserver;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
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
import com.example.wallet_transfer_service.utils.ErrorUtil;
import com.google.protobuf.Timestamp;

@GRpcService
@Slf4j
public class SystemConfigServiceImpl extends GetBankListServiceGrpc.GetBankListServiceImplBase {

    @Autowired
    SystemConfigService systemConfigService;

    @Autowired
    DateTimeUtil dateTimeUtil;

    @Autowired
    ErrorUtil errorUtil;

    @Override
    public void getBankList(EmptyRequest request, StreamObserver<BankListResponse> responseObserver) {
        var response = BankListResponse.newBuilder();

        try {
            // TODO: CallApi
            BankListDto resultList = systemConfigService.GetBankService();

            // TODO: Map To BankList
            if (resultList != null) {
                if (resultList.getBankList() != null && resultList.getBankList().size() > 0) {
                    for (BankDto item : resultList.getBankList()) {
                        var bank = BankList.newBuilder().setBankCode(item.getBankCode())
                                .setBankName(item.getBankNameTh()).setBankImage(item.getImgPath());
                        response.addBankList(bank);
                    }
                }

                if (resultList.getReturnResult() != null) {
                    var errorEntity = ReturnResult.newBuilder();
                    if (!StringUtil.isNullOrEmpty(resultList.getReturnResult().getResultCode()))
                        errorEntity.setResultCode(resultList.getReturnResult().getResultCode());

                    if (!StringUtil.isNullOrEmpty(resultList.getReturnResult().getResult()))
                        errorEntity.setResult(resultList.getReturnResult().getResult());

                    if (!StringUtil.isNullOrEmpty(resultList.getReturnResult().getResultDescription()))
                        errorEntity.setResultDescription(resultList.getReturnResult().getResultDescription());

                    if (!StringUtil.isNullOrEmpty(resultList.getReturnResult().getErrorRefId()))
                        errorEntity.setErrorRefId(resultList.getReturnResult().getErrorRefId());

                    response.setReturnResult(errorEntity);
                }

            } else {
                // TODO: Handle Error
                var err = ReturnResult.newBuilder().setResultCode("500").setResult("lost connect")
                        .setResultDescription("ไม่สามารถเชื่อมต่อได้ กรุณาติดต่ผู้ให้บริการ");

                response.setReturnResult(err);
            }

            // TODO: Return
            responseObserver.onNext(response.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            // TODO: handle exception
            responseObserver.onError(
                    Status.INTERNAL.withDescription("getBankListController : Cannot Connect to Service Because : " + e)
                            .asRuntimeException());
        }

    }
}
