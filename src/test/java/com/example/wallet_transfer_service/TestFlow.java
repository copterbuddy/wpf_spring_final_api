package com.example.wallet_transfer_service;

import com.example.CustomerWalletGrpc.CustomerWalletServiceGrpc;
import com.example.CustomerWalletGrpc.SearchCustomerRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestFlow {

    void GetCustomerList() {
        // var channel =
        // ManagedChannelBuilder.forTarget("localhost:9090").usePlaintext().build();
        // var service = GetBankListServiceGrpc.newBlockingStub(channel);

        // var request = EmptyRequest.newBuilder().build();
        // var res = service.getBankList(request);

        // log.info("kunanonLog-grpc-Client hello={}", res.getText());
        // Assertions.assertEquals("hello,Cop", res.getText());
    }

    void SearchCustomerList() {
        var channel = ManagedChannelBuilder.forTarget("localhost:9090").usePlaintext().build();
        var service = CustomerWalletServiceGrpc.newBlockingStub(channel);

        var request = SearchCustomerRequest.newBuilder().setSearchType("1").setSearchText("value").build();
        var response = service.searchCustomer(request);

        log.info("kunanonLog-Grpc-Client SearchCustomerList={}", response);
        Assertions.assertEquals(response.getCustomerEntityCount(), 2);
    }
}
