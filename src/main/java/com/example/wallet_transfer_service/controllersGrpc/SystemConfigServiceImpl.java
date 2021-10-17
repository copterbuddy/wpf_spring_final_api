package com.example.wallet_transfer_service.controllersGrpc;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import com.example.systemConfigGrpc.*;
import com.example.wallet_transfer_service.services.SystemConfigService;

@GRpcService
@Slf4j
public class SystemConfigServiceImpl extends GetBankListServiceGrpc.GetBankListServiceImplBase {

    @Autowired
    SystemConfigService systemConfigService;


    @Override
    public void getBankList(EmptyRequest request, StreamObserver<BankListResponse> responseObserver) {


        var res = BankListResponse.newBuilder().setText("").build();

        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
}
