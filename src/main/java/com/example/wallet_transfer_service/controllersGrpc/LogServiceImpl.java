package com.example.wallet_transfer_service.controllersGrpc;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import io.grpc.stub.StreamObserver;

import com.example.LogGrpc.*;
import com.example.wallet_transfer_service.services.LogService;

@GRpcService
public class LogServiceImpl extends LogGrpcGrpc.LogGrpcImplBase {

    @Autowired
    LogService logService;

    @Override
    public void addActivityLog(LogRequest request, StreamObserver<EmptyResponse> responseObserver) {
        EmptyResponse response = EmptyResponse.newBuilder().build();

        try {
            // TODO: CallApi
            logService.AddActivityLog(request.getActType(), request.getActDetail(), request.getActFullDetail(),
                    request.getUserId(), request.getComname(), request.getMemo(), request.getErrCode(),
                    request.getErrDesc(), request.getPageCode(), request.getPageName());

            // TODO: Return
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}
