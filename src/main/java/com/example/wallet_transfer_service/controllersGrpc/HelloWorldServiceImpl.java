package com.example.wallet_transfer_service.controllersGrpc;

import org.lognet.springboot.grpc.GRpcService;

import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;
import io.grpc.stub.StreamObserver;

import com.example.helloworld.*;
import lombok.extern.slf4j.Slf4j;

@GRpcService
@Slf4j
public class HelloWorldServiceImpl extends HelloWorldServiceGrpc.HelloWorldServiceImplBase {

    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        var reqText = "hello,";

        if (StringUtil.isNullOrEmpty(request.getText())) {
            reqText = null;
        } else {
            reqText = reqText + request.getText();
        }
        var res = HelloResponse.newBuilder().setText(reqText).build();

        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
}
