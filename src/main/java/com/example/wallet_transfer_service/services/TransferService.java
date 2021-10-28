package com.example.wallet_transfer_service.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import javax.transaction.Transactional;

import com.example.TransferWalletGrpc.TransferRequest;
import com.example.wallet_transfer_service.dto.BankDto;
import com.example.wallet_transfer_service.dto.BankListDto;
import com.example.wallet_transfer_service.dto.CustomerDto;
import com.example.wallet_transfer_service.dto.CustomerListDto;
import com.example.wallet_transfer_service.dto.PreTransferRequest;
import com.example.wallet_transfer_service.dto.TransferDtoResponse;
import com.example.wallet_transfer_service.dto.WalletDto;
import com.example.wallet_transfer_service.dto.WalletListResponse;
import com.example.wallet_transfer_service.model.Customer;
import com.example.wallet_transfer_service.model.LogTransaction;
import com.example.wallet_transfer_service.model.RunningID;
import com.example.wallet_transfer_service.repository.LogTransactionRepository;
import com.example.wallet_transfer_service.repository.RunningIdRepository;
import com.example.wallet_transfer_service.utils.ErrorUtil;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;

@Component
@Transactional
public class TransferService {

    private String _baseUrl = "http://gf-dev-bo-website-lb-1570887081.ap-southeast-1.elb.amazonaws.com:550/api/Wallet";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RunningIdRepository runningIdRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    LogService logService;

    @Autowired
    SystemConfigService systemConfigService;

    @Autowired
    ErrorUtil errorUtil;

    public TransferDtoResponse PreTransfer(String fromWalletId, String toWalletId, String bankCode, double amount,
            String memo, String comName, String userId) {
        var response = new TransferDtoResponse();
        String corebankTransType = "transfer_gf";
        String channel = "Wallet";
        boolean isProcess = true;
        Gson gson = new Gson();

        try {
            // TODO: CheckParam
            if (isProcess)
                if (StringUtil.isNullOrEmpty(fromWalletId)) {
                    isProcess = false;
                    response.setReturnResult(errorUtil.Error400());
                }

            if (isProcess)
                if (StringUtil.isNullOrEmpty(toWalletId)) {
                    isProcess = false;
                    response.setReturnResult(errorUtil.Error400());
                }

            if (isProcess)
                if (StringUtil.isNullOrEmpty(bankCode)) {
                    isProcess = false;
                    response.setReturnResult(errorUtil.Error400());
                }

            if (isProcess)
                if (amount <= 0) {
                    isProcess = false;
                    response.setReturnResult(errorUtil.Error400());
                }

            if (isProcess)
                if (StringUtil.isNullOrEmpty(memo)) {
                    isProcess = false;
                    response.setReturnResult(errorUtil.Error400());
                }

            if (isProcess)
                if (StringUtil.isNullOrEmpty(comName)) {
                    isProcess = false;
                    response.setReturnResult(errorUtil.Error400());
                }

            if (isProcess) {
                RunningID runIdEntity = new RunningID();
                runIdEntity = runningIdRepository.findByRunningType("TRAN_RUNNING_ID");

                PreTransferRequest preTransferRequest = new PreTransferRequest();
                preTransferRequest.setTransCode(runIdEntity.getPrefix() + Integer.parseInt(runIdEntity.getYearRunning())
                        + Integer.parseInt(runIdEntity.getMonthRunning())
                        + Integer.parseInt(runIdEntity.getDayRunning()) + runIdEntity.getLastRunningId());
                preTransferRequest.setCorebankTransType(corebankTransType);
                preTransferRequest.setChannel(channel);
                preTransferRequest.setFromWalletId(fromWalletId);
                preTransferRequest.setToWalletId(toWalletId);
                preTransferRequest.setAmount(amount);
                String fee3Code = GetFee3Code(bankCode);
                double fee3Amount = GetFee3Amount(bankCode);

                if (fee3Code != null)
                    preTransferRequest.setFee3Code(fee3Code);

                preTransferRequest.setFee3Amount(fee3Amount);

                RunningID newRunId = runIdEntity;
                newRunId.setLastRunningId(runIdEntity.getLastRunningId() + 1);
                runningIdRepository.save(newRunId);

                // TODO: Get PreTransfer
                var responsePreTransfer = restTemplate.postForEntity(_baseUrl + "/PreTransfer", preTransferRequest,
                        TransferDtoResponse.class);

                if (responsePreTransfer != null && responsePreTransfer.getStatusCode().equals(HttpStatus.OK)) {
                    var responseFulllDetail = gson.toJson(responsePreTransfer);
                    logService.AddActivityLog(4, "PreTransfer", responseFulllDetail, userId, comName, memo,
                            responsePreTransfer.getBody().getReturnResult().getResultCode(),
                            responsePreTransfer.getBody().getReturnResult().getResultDescription(), "PAGE002",
                            "PRETRANSFER_PAGE", null, null, null, null, null, Date.from(Instant.now()), userId);

                    // Gen TransactionToken
                    String transactionToken = md5hashing(
                            userId + responsePreTransfer.getBody().getReturnResult().getResultTimeStamp());

                    if (!StringUtil.isNullOrEmpty(response.getTransUuid())) {
                        response.setTransUuid(responsePreTransfer.getBody().getTransUuid());
                    }

                    if (responsePreTransfer.getBody().getConfirmInfo() != null) {
                        response.setConfirmInfo(responsePreTransfer.getBody().getConfirmInfo());
                        response.getConfirmInfo().setTransactionToken(transactionToken);
                        response.getConfirmInfo().setToBankCode(bankCode);
                        response.getConfirmInfo().setMemo(memo);
                        response.getConfirmInfo().setFee3Amount(preTransferRequest.getFee3Amount());// fee
                        response.getConfirmInfo().setFee3Code(preTransferRequest.getFee3Code());// fee

                        if (responsePreTransfer.getBody().getReturnResult() != null)
                            response.setReturnResult(responsePreTransfer.getBody().getReturnResult());

                        response.getReturnResult()
                                .setResultTimeStamp(responsePreTransfer.getBody().getConfirmInfo().getTimeStamp());
                    }

                    var actFullDetail = gson.toJson(response);

                    // Save to redis
                    if (!StringUtil.isNullOrEmpty(actFullDetail))
                        redisTemplate.opsForValue().set(transactionToken, actFullDetail, Duration.ofMinutes(30));

                } else {
                    response.setReturnResult(errorUtil.Error402());
                }
            }

        } catch (

        Exception e) {
            // TODO: handle exception
            throw e;
        }

        return response;
    }

    public TransferDtoResponse Transfer(String transactionToken, String comName, String userId, String citizenId) {
        var response = new TransferDtoResponse();
        String corebankTransType = "transfer_gf";
        String channel = "Wallet";

        try {
            // TODO: CheckParam
            if (StringUtil.isNullOrEmpty(transactionToken)) {

            }

            Object preTransferRequestObject = redisTemplate.opsForValue().get(transactionToken);
            if (preTransferRequestObject == null) {
                // TODO: Error

            }
            String preTransferRequestString = preTransferRequestObject.toString();

            Gson gson = new Gson();
            var preTransferRequest = new TransferDtoResponse();
            preTransferRequest = gson.fromJson(preTransferRequestString, TransferDtoResponse.class);

            var transferRequest = new PreTransferRequest();
            transferRequest.setTransCode(preTransferRequest.getConfirmInfo().getTransCode());
            transferRequest.setCorebankTransType(corebankTransType);
            transferRequest.setChannel(channel);
            transferRequest.setFromWalletId(preTransferRequest.getConfirmInfo().getFromWalletId());
            transferRequest.setToWalletId(preTransferRequest.getConfirmInfo().getToWalletId());
            transferRequest.setAmount(preTransferRequest.getConfirmInfo().getAmount());
            transferRequest.setFee3Code(preTransferRequest.getConfirmInfo().getFee3Code());
            transferRequest.setFee3Amount(preTransferRequest.getConfirmInfo().getFee3Amount());

            // TODO: Get Transfer
            var responseTransfer = restTemplate.postForEntity(_baseUrl + "/TransferComplete", transferRequest,
                    TransferDtoResponse.class);

            if (responseTransfer != null) {
                responseTransfer.getBody().getConfirmInfo()
                        .setToBankCode(preTransferRequest.getConfirmInfo().getToBankCode());
                var shortInfo = responseTransfer.getBody().getConfirmInfo();
                var shortErr = responseTransfer.getBody().getReturnResult();

                var responseFulllDetail = gson.toJson(responseTransfer);

                // TODO: GetInfo
                CustomerListDto custList = customerService.GetCustomerListNew(citizenId, "1", comName, userId, false);

                CustomerDto useCust = null;
                for (CustomerDto item : custList.getCustomerEntity()) {
                    WalletListResponse walletList = customerService.GetFromWallet(item.getCustId());

                    for (WalletDto wallet : walletList.getWalletList()) {
                        if (wallet.getWalletId().equals(shortInfo.getFromWalletId())) {
                            useCust = item;
                            break;
                        }
                    }
                    break;
                }

                var tranCode = "";
                BankListDto getBankService = systemConfigService.GetBankService();
                for (BankDto item : getBankService.getBankList()) {
                    if (item.getBankCode().equals(shortInfo.getToBankCode())) {
                        tranCode = item.getBankAbbr();
                        break;
                    }
                }

                // TODO: AddTransLog
                int addTransactionLog = logService.AddTransactionLog(shortInfo.getTimeStamp(), shortInfo.getTransCode(),
                        95, useCust.getCustId(), useCust.getMobileNo(), citizenId, "STMR",
                        GetWalletint(shortInfo.getFromWalletType()), shortInfo.getFromWalletId(),
                        shortInfo.getFromWalletName(), shortInfo.getFromWalletName(), tranCode,
                        GetWalletint(shortInfo.getToWalletType()), shortInfo.getToWalletId(),
                        shortInfo.getToWalletName(), shortInfo.getToWalletName(), shortInfo.getFeeCost(),
                        shortInfo.getAmount(), shortInfo.getToWalletCurrency(), 1,
                        preTransferRequest.getConfirmInfo().getMemo(), shortErr.getResultCode(),
                        shortErr.getResultDescription(), userId, shortInfo.getTimeStamp(), userId,
                        shortInfo.getTimeStamp());

                LogTransaction tranlogData = logService.getLogByTranId(addTransactionLog);

                // TODO: AddActLog
                logService.AddActivityLog(5, "CompleteTransfer", responseFulllDetail, userId, comName,
                        preTransferRequest.getConfirmInfo().getMemo(),
                        responseTransfer.getBody().getReturnResult().getResultCode(),
                        responseTransfer.getBody().getReturnResult().getResultDescription(), "PAGE003",
                        "COMPLETETRANSFER_PAGE", tranlogData.getTranId().intValue(), tranlogData.getTranType(),
                        tranlogData.getTranCode(), tranlogData.getFromBankCode(), tranlogData.getToBankCode(),
                        Date.from(Instant.now()), userId);

                // Integer tranId,
                // Integer tranType
                // String tranCode
                // String tranBankFrom
                // String tranBankTo

                if (responseTransfer != null && responseTransfer.getStatusCode().equals(HttpStatus.OK)) {
                    response.setConfirmInfo(responseTransfer.getBody().getConfirmInfo());
                    response.getConfirmInfo().setMemo(preTransferRequest.getConfirmInfo().getMemo());
                    response.setReturnResult(responseTransfer.getBody().getReturnResult());
                    response.setTransUuid(responseTransfer.getBody().getTransUuid());
                }
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

    public static String md5hashing(String text) {
        String hashtext = null;
        try {
            String plaintext = text;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(plaintext.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            hashtext = bigInt.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
        } catch (Exception e1) {
            // TODO: handle exception
        }
        return hashtext;
    }

    public String GetFee3Code(String code) {
        String res = "";
        switch (code) {
        case "025":
        case "006":
        case "004":
            res = "A025";
            break;

        case "002":
        case "011":
        case "014":
        case "017":
        case "022":
        case "024":
        case "030":
        case "032":
        case "033":
        case "065":
        case "066":
            res = null;
            break;
        default:
            break;
        }

        return res;
    }

    public double GetFee3Amount(String code) {
        double res = 0;
        switch (code) {
        case "025":
            res = 10;
            break;
        case "006":
            res = 25;
            break;
        case "004":
            res = 50;
            break;

        case "002":
        case "011":
        case "014":
        case "017":
        case "022":
        case "024":
        case "030":
        case "032":
        case "033":
        case "065":
        case "066":
            res = 0;
            break;
        default:
            break;
        }
        return res;
    }

    public int GetWalletint(String walletType) {
        int walletInt = 0;
        switch (walletType) {
        case "UNKNOW_WALLET_TYPE":
            walletInt = 0;
            break;
        case "SAVING_DEPOSIT_ACCOUNT":
            walletInt = 1;
            break;
        case "FIXED_DEPOSIT_ACCOUNT":
            walletInt = 2;
            break;
        case "LOAN_ACCOUNT":
            walletInt = 3;
            break;
        case "RSM":
            walletInt = 10;
            break;
        case "GF":
            walletInt = 11;
            break;
        case "EF":
            walletInt = 12;
            break;
        case "RF":
            walletInt = 13;
            break;
        case "VF":
            walletInt = 14;
            break;
        case "KPLUS":
            walletInt = 15;
            break;
        case "BOONTERM":
            walletInt = 16;
            break;
        case "SCB":
            walletInt = 17;
            break;

        default:
            walletInt = 0;
            break;
        }
        return walletInt;
    }
}
