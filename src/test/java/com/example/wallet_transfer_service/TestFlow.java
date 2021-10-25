package com.example.wallet_transfer_service;

import com.example.CustomerWalletGrpc.CustomerWalletServiceGrpc;
import com.example.CustomerWalletGrpc.SearchCustomerRequest;
import com.example.LogGrpc.LogGrpc;
import com.example.LogGrpc.LogGrpcGrpc;
import com.example.LogGrpc.LogRequest;
import com.example.wallet_transfer_service.dto.CustomerListDto;

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
public class TestFlow {

    // interface ClientInfo {
    // String EmployeeId = "11588";
    // String ComName = "My-PC";
    // }

    // @Test
    // @Order(1)
    // void SearchCustomerList() {
    // var channel =
    // ManagedChannelBuilder.forTarget("localhost:9090").usePlaintext().build();
    // var service = CustomerWalletServiceGrpc.newBlockingStub(channel);

    // var request =
    // SearchCustomerRequest.newBuilder().setSearchType("1").setSearchText("1100800745551").build();
    // var response = service.searchCustomer(request);

    // CustomerListDto responseEnt = new CustomerListDto();

    // log.info("kunanonLog-Grpc-Client SearchCustomerList={}", response);
    // Assertions.assertEquals(response.getCustomerEntityCount(), 2);
    // }

    // @Test
    // @Order(2)
    // void AddActivityLogForCustList() {
    // var channel =
    // ManagedChannelBuilder.forTarget("localhost:9090").usePlaintext().build();
    // var service = LogGrpcGrpc.newBlockingStub(channel);

    // var request =
    // LogRequest.newBuilder().setActType(1).setActDetail("SearchCustomer").setActFullDetail("json")
    // .setUserId(ClientInfo.EmployeeId).setComname(ClientInfo.ComName).setMemo(null).setErrCode("200")
    // .setErrDesc("200
    // Success").setPageCode("PAGE001").setPageName("TRANSFER_PAGE").build();
    // var response = service.addActivityLog(request);

    // // Assertions.assertEquals(response.getCustomerEntityCount(), 2);
    // }
}
