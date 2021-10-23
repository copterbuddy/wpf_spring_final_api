package com.example.wallet_transfer_service.services;

import java.math.BigDecimal;

import com.example.wallet_transfer_service.dto.PreTransferRequest;
import com.example.wallet_transfer_service.dto.TransactionCoreBankResponse;
import com.example.wallet_transfer_service.dto.TransferResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;
import lombok.Data;

@Component
public class TransferService {

    private String _baseUrl = "http://gf-dev-bo-website-lb-1570887081.ap-southeast-1.elb.amazonaws.com:550/api/Wallet";

    @Autowired
    RestTemplate restTemplate;

    public TransferResponse PreTransfer(String fromWallet, String toWallet, double amount) {
        var response = new TransferResponse();
        String corebankTransType = "transfer_gf";
        String channel = "Wallet";

        try {
            // TODO: CheckParam
            if (StringUtil.isNullOrEmpty(fromWallet)) {

            }

            if (StringUtil.isNullOrEmpty(toWallet)) {

            }

            if (amount <= 0) {

            }

            PreTransferRequest preTransferRequest = new PreTransferRequest();
            preTransferRequest.setTransCode("SMTR00001");
            preTransferRequest.setCorebankTransType(corebankTransType);
            preTransferRequest.setChannel(channel);
            preTransferRequest.setFromWalletId("950102002557005");
            preTransferRequest.setToWalletId("950101000010911");
            preTransferRequest.setAmount(10);
            preTransferRequest.setFee3Code(null);
            preTransferRequest.setFee3Amount(0);

            // TODO: Get ToWallet
            var responsePreTransfer = restTemplate.postForEntity(_baseUrl + "/PreTransfer", preTransferRequest,
                    TransferResponse.class);

            if (responsePreTransfer != null && responsePreTransfer.getStatusCode().equals(HttpStatus.OK)) {
                response.setConfirmInfo(responsePreTransfer.getBody().getConfirmInfo());
                response.setReturnResult(responsePreTransfer.getBody().getReturnResult());
                response.setTransUuid(responsePreTransfer.getBody().getTransUuid());
            }

            // TODO: Pass

            // TODO: Failed

        } catch (

        Exception e) {
            // TODO: handle exception
            throw e;
        }

        return response;
    }

    public TransferResponse Transfer(String transactionToken) {
        var response = new TransferResponse();
        String corebankTransType = "transfer_gf";
        String channel = "Wallet";

        try {
            // TODO: CheckParam
            if (StringUtil.isNullOrEmpty(transactionToken)) {

            }

            PreTransferRequest preTransferRequest = new PreTransferRequest();
            preTransferRequest.setTransCode("SMTR00001");
            preTransferRequest.setCorebankTransType(corebankTransType);
            preTransferRequest.setChannel(channel);
            preTransferRequest.setFromWalletId("950102002557005");
            preTransferRequest.setToWalletId("950101000010911");
            preTransferRequest.setAmount(10);
            preTransferRequest.setFee3Code(null);
            preTransferRequest.setFee3Amount(0);

            // TODO: Get ToWallet
            var responseTransfer = restTemplate.postForEntity(_baseUrl + "/TransferComplete", preTransferRequest,
                    TransferResponse.class);

            if (responseTransfer != null && responseTransfer.getStatusCode().equals(HttpStatus.OK)) {
                response.setConfirmInfo(responseTransfer.getBody().getConfirmInfo());
                response.setReturnResult(responseTransfer.getBody().getReturnResult());
                response.setTransUuid(responseTransfer.getBody().getTransUuid());
            }

            // TODO: Pass

            // TODO: Failed

        } catch (

        Exception e) {
            // TODO: handle exception
            throw e;
        }

        return response;
    }
}
