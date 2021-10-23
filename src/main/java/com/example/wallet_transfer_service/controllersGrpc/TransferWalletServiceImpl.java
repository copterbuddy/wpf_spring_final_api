package com.example.wallet_transfer_service.controllersGrpc;

import com.example.TransferWalletGrpc.*;
import com.example.wallet_transfer_service.services.TransferService;
import com.example.wallet_transfer_service.utils.DateTimeUtil;

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
        var response = PreTransferRequest.newBuilder();

        try {
            // TODO: CallApi
            transferService.PreTransfer("fromWallet", "toWallet", 0);

            // TODO: Map To Response
            // if (custList != null && custList.getCustomerEntity() != null &&
            // custList.getReturnResult() != null
            // && custList.getCustomerEntity().size() > 0) {
            // for (CustomerDto item : custList.getCustomerEntity()) {
            // var cust =
            // CustomerEntity.newBuilder().setCustId(item.getCustId().replaceAll("\\s+",
            // ""))
            // .setCitizenId(item.getCifId().replaceAll("\\s+",
            // "")).setBranch(item.getBranch())
            // .setTitle(item.getTitleTh().replaceAll("\\s+", ""))
            // .setName(item.getFirstnameTh().replaceAll("\\s+", ""))
            // .setLastname(item.getLastnameTh().replaceAll("\\s+",
            // "")).setSegmant(item.getSegment())
            // .setJointAccountStatus(item.getJointAccountStatus())
            // .setSensitiveAccount(item.getSensitiveCustomer()).setCitizenImage(item.getCifImage())
            // .setSignImage(item.getSignImage());
            // response.addCustomerEntity(cust);
            // }

            // // TODO: ReturnResult
            // var errorEntity = ReturnResult.newBuilder();
            // if (!StringUtil.isNullOrEmpty(custList.getReturnResult().getResultCode()))
            // errorEntity.setResultCode(custList.getReturnResult().getResultCode());

            // if (!StringUtil.isNullOrEmpty(custList.getReturnResult().getResult()))
            // errorEntity.setResult(custList.getReturnResult().getResult());

            // if
            // (!StringUtil.isNullOrEmpty(custList.getReturnResult().getResultDescription()))
            // errorEntity.setResultDescription(custList.getReturnResult().getResultDescription());

            // if (!StringUtil.isNullOrEmpty(custList.getReturnResult().getErrorRefId()))
            // errorEntity.setErrorRefId(custList.getReturnResult().getErrorRefId());

            // errorEntity.setResultTimestamp(dateTimeUtil.GetTimeStamp());

            // response.setReturnResult(errorEntity);

            // } else {
            // // TODO: Handle Error
            // }

            // // TODO: Return
            responseObserver.onNext(null);
            responseObserver.onCompleted();

        } catch (Exception e) {
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
            transferService.Transfer("TransactionToken");
            
            // TODO: Map To Response
            // if (custList != null && custList.getCustomerEntity() != null &&
            // custList.getReturnResult() != null
            // && custList.getCustomerEntity().size() > 0) {
            // for (CustomerDto item : custList.getCustomerEntity()) {
            // var cust =
            // CustomerEntity.newBuilder().setCustId(item.getCustId().replaceAll("\\s+",
            // ""))
            // .setCitizenId(item.getCifId().replaceAll("\\s+",
            // "")).setBranch(item.getBranch())
            // .setTitle(item.getTitleTh().replaceAll("\\s+", ""))
            // .setName(item.getFirstnameTh().replaceAll("\\s+", ""))
            // .setLastname(item.getLastnameTh().replaceAll("\\s+",
            // "")).setSegmant(item.getSegment())
            // .setJointAccountStatus(item.getJointAccountStatus())
            // .setSensitiveAccount(item.getSensitiveCustomer()).setCitizenImage(item.getCifImage())
            // .setSignImage(item.getSignImage());
            // response.addCustomerEntity(cust);
            // }

            // // TODO: ReturnResult
            // var errorEntity = ReturnResult.newBuilder();
            // if (!StringUtil.isNullOrEmpty(custList.getReturnResult().getResultCode()))
            // errorEntity.setResultCode(custList.getReturnResult().getResultCode());

            // if (!StringUtil.isNullOrEmpty(custList.getReturnResult().getResult()))
            // errorEntity.setResult(custList.getReturnResult().getResult());

            // if
            // (!StringUtil.isNullOrEmpty(custList.getReturnResult().getResultDescription()))
            // errorEntity.setResultDescription(custList.getReturnResult().getResultDescription());

            // if (!StringUtil.isNullOrEmpty(custList.getReturnResult().getErrorRefId()))
            // errorEntity.setErrorRefId(custList.getReturnResult().getErrorRefId());

            // errorEntity.setResultTimestamp(dateTimeUtil.GetTimeStamp());

            // response.setReturnResult(errorEntity);

            // } else {
            // // TODO: Handle Error
            // }

            // // TODO: Return
            responseObserver.onNext(null);
            responseObserver.onCompleted();

        } catch (Exception e) {
            // TODO: handle exception
            responseObserver.onError(
                    Status.INTERNAL.withDescription("getBankListController : Cannot Connect to Service Because : " + e)
                            .asRuntimeException());
        }
    }
}
