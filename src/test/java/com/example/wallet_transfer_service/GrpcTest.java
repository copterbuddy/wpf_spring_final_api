package com.example.wallet_transfer_service;

import com.example.helloworld.HelloRequest;
import com.example.helloworld.HelloWorldServiceGrpc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GrpcTest {

    @Test
    @Order(1)
    void Hello() {
        var channel = ManagedChannelBuilder.forTarget("localhost:9090").usePlaintext().build();
        var service = HelloWorldServiceGrpc.newBlockingStub(channel);

        var req = HelloRequest.newBuilder().setText("Cop").build();
        var res = service.hello(req);

        log.info("kunanonLog-grpc-Client hello={}", res.getText());
        Assertions.assertEquals("hello,Cop", res.getText());
    }
}
