package com.example.wallet_transfer_service.controllersGrpc;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

import com.example.TransferWalletGrpc.*;
import com.example.wallet_transfer_service.dto.TransferDtoResponse;
import com.example.wallet_transfer_service.services.TransferService;
import com.example.wallet_transfer_service.utils.DateTimeUtil;
import com.google.protobuf.Timestamp;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import io.grpc.Status;
import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@GRpcService
@Slf4j
public class TransferWalletServiceImpl extends TransferWalletServiceGrpc.TransferWalletServiceImplBase {

    @Autowired
    DateTimeUtil dateTimeUtil;

    @Autowired
    TransferService transferService;

    @Override
    public void preTansferWallet(PreTransferRequest request, StreamObserver<PreTransferResponse> responseObserver) {
        var response = PreTransferResponse.newBuilder();

        try {
            boolean isProcess = true;
            // TODO: Check Param
            if (isProcess)
                if (StringUtil.isNullOrEmpty(request.getFromWalletId())) {
                    isProcess = false;
                    var err = ReturnResult.newBuilder().setResultCode("400").setResult("invalid parameter")
                            .setResultDescription("กรุณากรอกข้อมูลให้ถูกต้อง");
                    response.setReturnResult(err);
                }

            if (isProcess)
                if (StringUtil.isNullOrEmpty(request.getToWalletId())) {
                    isProcess = false;
                    var err = ReturnResult.newBuilder().setResultCode("400").setResult("invalid parameter")
                            .setResultDescription("กรุณากรอกข้อมูลให้ถูกต้อง");
                    response.setReturnResult(err);
                }

            if (isProcess)
                if (StringUtil.isNullOrEmpty(request.getBankCode())) {
                    isProcess = false;
                    var err = ReturnResult.newBuilder().setResultCode("400").setResult("invalid parameter")
                            .setResultDescription("กรุณากรอกข้อมูลให้ถูกต้อง");
                    response.setReturnResult(err);
                }

            if (isProcess)
                if (request.getAmount() > 0) {
                    isProcess = false;
                    var err = ReturnResult.newBuilder().setResultCode("400").setResult("invalid parameter")
                            .setResultDescription("กรุณากรอกข้อมูลให้ถูกต้อง");
                    response.setReturnResult(err);
                }

            if (isProcess)
                if (StringUtil.isNullOrEmpty(request.getMemo())) {
                    isProcess = false;
                    var err = ReturnResult.newBuilder().setResultCode("400").setResult("invalid parameter")
                            .setResultDescription("กรุณากรอกข้อมูลให้ถูกต้อง");
                    response.setReturnResult(err);
                }

            if (isProcess)
                if (StringUtil.isNullOrEmpty(request.getComName())) {
                    isProcess = false;
                    var err = ReturnResult.newBuilder().setResultCode("401").setResult("unauthorize")
                            .setResultDescription("การยืนยันตัวตนผิดพลาด");
                    response.setReturnResult(err);
                }

            if (isProcess)
                if (StringUtil.isNullOrEmpty(request.getUserId())) {
                    isProcess = false;
                    var err = ReturnResult.newBuilder().setResultCode("401").setResult("unauthorize")
                            .setResultDescription("การยืนยันตัวตนผิดพลาด");
                    response.setReturnResult(err);
                }

            // TODO: CallApi
            if (isProcess) {

                TransferDtoResponse preTransfer = transferService.PreTransfer(request.getFromWalletId(),
                        request.getToWalletId(), request.getBankCode(), request.getAmount(), request.getMemo(),
                        request.getComName(), request.getUserId());

                // TODO: Map To Response
                if (preTransfer != null) {

                    if (preTransfer.getConfirmInfo() != null) {

                        Date date = preTransfer.getConfirmInfo().getTimeStamp();
                        Instant instant = date.toInstant();
                        Timestamp timestamp = Timestamp.newBuilder().setSeconds(instant.getEpochSecond())
                                .setNanos(instant.getNano()).build();

                        var preResponse = TransactionEntity.newBuilder()
                                .setFromWalletId(preTransfer.getConfirmInfo().getFromWalletId())
                                .setToWalletId(preTransfer.getConfirmInfo().getToWalletId())
                                .setToWalletName(preTransfer.getConfirmInfo().getToWalletName())
                                .setAmount(preTransfer.getConfirmInfo().getAmount())
                                .setFee3Amount(preTransfer.getConfirmInfo().getFeeCost())
                                .setBankCode(preTransfer.getConfirmInfo().getToBankCode()).setTimeStamp(timestamp)
                                .setTransactionToken(preTransfer.getConfirmInfo().getTransactionToken());
                        response.setTransactionEntity(preResponse);
                    }

                    if (preTransfer.getReturnResult() != null) {
                        // TODO: ReturnResult
                        var errorEntity = ReturnResult.newBuilder();
                        if (!StringUtil.isNullOrEmpty(preTransfer.getReturnResult().getResultCode()))
                            errorEntity.setResultCode(preTransfer.getReturnResult().getResultCode());

                        if (!StringUtil.isNullOrEmpty(preTransfer.getReturnResult().getResult()))
                            errorEntity.setResult(preTransfer.getReturnResult().getResult());

                        if (!StringUtil.isNullOrEmpty(preTransfer.getReturnResult().getResultDescription()))
                            errorEntity.setResultDescription(preTransfer.getReturnResult().getResultDescription());

                        if (!StringUtil.isNullOrEmpty(preTransfer.getReturnResult().getErrorRefId()))
                            errorEntity.setErrorRefId(preTransfer.getReturnResult().getErrorRefId());

                        errorEntity.setResultTimestamp(dateTimeUtil.GetTimeStamp());

                        response.setReturnResult(errorEntity);
                    }

                } else {
                    // TODO: Handle Error
                    var err = ReturnResult.newBuilder().setResultCode("500").setResult("lost connect")
                            .setResultDescription("ไม่สามารถเชื่อมต่อได้ กรุณาติดต่ผู้ให้บริการ");

                    response.setReturnResult(err);
                }
            }

            // TODO: Return
            responseObserver.onNext(response.build());
            responseObserver.onCompleted();

        } catch (

        Exception e) {
            // TODO: handle exception
            responseObserver.onError(Status.INTERNAL
                    .withDescription("preTansferWalletController : Cannot Connect to Service Because : " + e)
                    .asRuntimeException());
        }
    }

    @Override
    public void transferWallet(TransferRequest request, StreamObserver<TransferResponse> responseObserver) {
        var response = TransferResponse.newBuilder();
        boolean isProcess = true;

        try {
            // TODO: Check Param
            if (StringUtil.isNullOrEmpty(request.getTransactionToken())) {
                isProcess = false;
                var err = ReturnResult.newBuilder().setResultCode("400").setResult("invalid parameter")
                        .setResultDescription("กรุณากรอกข้อมูลให้ถูกต้อง");
                response.setReturnResult(err);
            }

            if (isProcess)
                if (StringUtil.isNullOrEmpty(request.getComName())) {
                    isProcess = false;
                    var err = ReturnResult.newBuilder().setResultCode("401").setResult("unauthorize")
                            .setResultDescription("การยืนยันตัวตนผิดพลาด");
                    response.setReturnResult(err);
                }

            if (isProcess)
                if (StringUtil.isNullOrEmpty(request.getUserId())) {
                    isProcess = false;
                    var err = ReturnResult.newBuilder().setResultCode("401").setResult("unauthorize")
                            .setResultDescription("การยืนยันตัวตนผิดพลาด");
                    response.setReturnResult(err);
                }

            // TODO: CallApi
            if (isProcess) {
                TransferDtoResponse transfer = transferService.Transfer(request.getTransactionToken(),
                        request.getComName(), request.getUserId(), request.getCitizenId());

                // TODO: Map To Response
                if (transfer != null) {

                    if (transfer.getConfirmInfo() != null) {
                        Date date = transfer.getConfirmInfo().getTimeStamp();
                        Instant instant = date.toInstant();
                        Timestamp timestamp = Timestamp.newBuilder().setSeconds(instant.getEpochSecond())
                                .setNanos(instant.getNano()).build();

                        var transferResponse = TransactionEntity.newBuilder()
                                .setTransCode(transfer.getConfirmInfo().getTransCode())
                                .setFromWalletName(transfer.getConfirmInfo().getFromWalletName())
                                .setFromWalletId(transfer.getConfirmInfo().getFromWalletId())
                                .setToWalletName(transfer.getConfirmInfo().getToWalletName())
                                .setToWalletId(transfer.getConfirmInfo().getToWalletId())
                                .setAmount(transfer.getConfirmInfo().getAmount())
                                .setFee3Amount(transfer.getConfirmInfo().getFeeCost())
                                .setBankCode(transfer.getConfirmInfo().getToBankCode()).setTimeStamp(timestamp)
                                .setMemo(transfer.getConfirmInfo().getMemo());

                        response.setTransactionEntity(transferResponse);
                    }

                    if (transfer.getReturnResult() != null) {
                        // TODO: ReturnResult
                        var errorEntity = ReturnResult.newBuilder();
                        if (!StringUtil.isNullOrEmpty(transfer.getReturnResult().getResultCode()))
                            errorEntity.setResultCode(transfer.getReturnResult().getResultCode());

                        if (!StringUtil.isNullOrEmpty(transfer.getReturnResult().getResult()))
                            errorEntity.setResult(transfer.getReturnResult().getResult());

                        if (!StringUtil.isNullOrEmpty(transfer.getReturnResult().getResultDescription()))
                            errorEntity.setResultDescription(transfer.getReturnResult().getResultDescription());

                        if (!StringUtil.isNullOrEmpty(transfer.getReturnResult().getErrorRefId()))
                            errorEntity.setErrorRefId(transfer.getReturnResult().getErrorRefId());

                        errorEntity.setResultTimestamp(dateTimeUtil.GetTimeStamp());

                        response.setReturnResult(errorEntity);
                    }

                } else {
                    // TODO: Handle Error
                    var err = ReturnResult.newBuilder().setResultCode("500").setResult("lost connect")
                            .setResultDescription("ไม่สามารถเชื่อมต่อได้ กรุณาติดต่ผู้ให้บริการ");

                    response.setReturnResult(err);
                }
            }

            // TODO: Return
            responseObserver.onNext(response.build());
            responseObserver.onCompleted();

        } catch (

        Exception e) {
            // TODO: handle exception
            responseObserver.onError(Status.INTERNAL
                    .withDescription("transferWalletController : Cannot Connect to Service Because : " + e)
                    .asRuntimeException());
        }
    }
}
