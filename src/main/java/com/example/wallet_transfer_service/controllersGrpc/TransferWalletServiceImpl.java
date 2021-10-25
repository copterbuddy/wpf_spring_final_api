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
            // TODO: CallApi
            TransferDtoResponse preTransfer = transferService.PreTransfer(request.getFromWalletId(),
                    request.getToWalletId(), request.getBankCode(), request.getAmount(), request.getMemo(),
                    request.getComName(), request.getUserId());

            // TODO: Map To Response
            if (preTransfer != null && preTransfer.getConfirmInfo() != null && preTransfer.getReturnResult() != null) {

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

            } else {
                // TODO: Handle Error
            }

            // TODO: Return
            responseObserver.onNext(response.build());
            responseObserver.onCompleted();

        } catch (

        Exception e) {
            // TODO: handle exception
            responseObserver.onError(
                    Status.INTERNAL.withDescription("getBankListController : Cannot Connect to Service Because : " + e)
                            .asRuntimeException());
        }
    }

    @Override
    public void transferWallet(TransferRequest request, StreamObserver<TransferResponse> responseObserver) {
        var response = TransferResponse.newBuilder();

        try {
            // TODO: CallApi
            TransferDtoResponse transfer = transferService.Transfer(request.getTransactionToken(), request.getComName(),
                    request.getUserId(), request.getCitizenId());

            // TODO: Map To Response
            if (transfer != null && transfer.getConfirmInfo() != null && transfer.getReturnResult() != null) {

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
                        .setBankCode(transfer.getConfirmInfo().getToBankCode()).setTimeStamp(timestamp);

                response.setTransactionEntity(transferResponse);

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

            } else {
                // TODO: Handle Error
            }

            // TODO: Return
            responseObserver.onNext(response.build());
            responseObserver.onCompleted();

        } catch (

        Exception e) {
            // TODO: handle exception
            responseObserver.onError(
                    Status.INTERNAL.withDescription("getBankListController : Cannot Connect to Service Because : " + e)
                            .asRuntimeException());
        }
    }
}
