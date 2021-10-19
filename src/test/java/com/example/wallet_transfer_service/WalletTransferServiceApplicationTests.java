package com.example.wallet_transfer_service;

import com.example.systemConfigGrpc.EmptyRequest;
import com.example.systemConfigGrpc.GetBankListServiceGrpc;

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
class WalletTransferServiceApplicationTests {

	@Test
	@Order(1)
	void GetListBank() {
		// var channel =
		// ManagedChannelBuilder.forTarget("localhost:9090").usePlaintext().build();
		// var service = GetBankListServiceGrpc.newBlockingStub(channel);

		// var request = EmptyRequest.newBuilder().build();
		// var res = service.getBankList(request);

		// log.info("kunanonLog-grpc-Client hello={}", res.getText());
		// Assertions.assertEquals("hello,Cop", res.getText());
	}

}
