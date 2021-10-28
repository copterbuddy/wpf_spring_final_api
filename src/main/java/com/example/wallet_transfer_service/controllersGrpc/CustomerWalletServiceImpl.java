package com.example.wallet_transfer_service.controllersGrpc;

import com.example.CustomerWalletGrpc.CustomerWalletServiceGrpc;
import com.example.CustomerWalletGrpc.GetFromWalletRequest;
import com.example.CustomerWalletGrpc.GetFromWalletResponse;
import com.example.CustomerWalletGrpc.GetToWalletRequest;
import com.example.CustomerWalletGrpc.GetToWalletResponse;
import com.example.CustomerWalletGrpc.ReturnResult;
import com.example.CustomerWalletGrpc.GetFromWalletResponse.WalletList;
import com.example.CustomerWalletGrpc.SearchCustomerRequest;
import com.example.CustomerWalletGrpc.SearchCustomerResponse;
import com.example.CustomerWalletGrpc.SearchCustomerResponse.CustomerEntity;
import com.example.wallet_transfer_service.dto.CustomerDto;
import com.example.wallet_transfer_service.dto.CustomerListDto;
import com.example.wallet_transfer_service.dto.WalletDto;
import com.example.wallet_transfer_service.dto.WalletListResponse;
import com.example.wallet_transfer_service.services.CustomerService;
import com.example.wallet_transfer_service.services.LogService;
import com.example.wallet_transfer_service.utils.DateTimeUtil;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import io.grpc.Status;
import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@GRpcService
@Slf4j
public class CustomerWalletServiceImpl extends CustomerWalletServiceGrpc.CustomerWalletServiceImplBase {

    @Autowired
    DateTimeUtil dateTimeUtil;

    @Autowired
    CustomerService customerService;

    @Autowired
    LogService logService;

    @Override
    public void searchCustomer(SearchCustomerRequest request, StreamObserver<SearchCustomerResponse> responseObserver) {
        var response = SearchCustomerResponse.newBuilder();

        try {
            boolean isProcess = true;

            // TODO: Validate Parameter
            if (isProcess)
                if (StringUtil.isNullOrEmpty(request.getSearchText())) {
                    isProcess = false;
                    var err = ReturnResult.newBuilder().setResultCode("400").setResult("invalid parameter")
                            .setResultDescription("กรุณากรอกข้อมูลให้ถูกต้อง");

                    response.setReturnResult(err);
                }

            if (isProcess)
                if (StringUtil.isNullOrEmpty(request.getSearchType())) {
                    isProcess = false;
                    var err = ReturnResult.newBuilder().setResultCode("400").setResult("invalid parameter")
                            .setResultDescription("กรุณากรอกข้อมูลให้ถูกต้อง");

                    response.setReturnResult(err);
                }

            if (isProcess)
                if (StringUtil.isNullOrEmpty(request.getComName())) {
                    isProcess = false;
                    var err = ReturnResult.newBuilder().setResultCode("401").setResult("unauthorize")
                            .setResultDescription("การยืนยันตัวตนผิดพลาด");

                    response.setReturnResult(err);
                }

            if (isProcess)
                if (StringUtil.isNullOrEmpty(request.getUserId())) {
                    isProcess = false;
                    var err = ReturnResult.newBuilder().setResultCode("401").setResult("unauthorize")
                            .setResultDescription("การยืนยันตัวตนผิดพลาด");

                    response.setReturnResult(err);
                }

            // TODO: CallApi

            if (isProcess) {
                CustomerListDto custList = customerService.GetCustomerList(request.getSearchText(),
                        request.getSearchType(), request.getComName(), request.getUserId());

                // TODO: Map To Response
                if (custList != null) {
                    if (custList.getCustomerEntity() != null && custList.getCustomerEntity().size() > 0) {
                        for (CustomerDto item : custList.getCustomerEntity()) {
                            var cust = CustomerEntity.newBuilder().setCustId(item.getCustId().replaceAll("\\s+", ""))
                                    .setCitizenId(item.getCifId().replaceAll("\\s+", "")).setBranch(item.getBranch())
                                    .setTitle(item.getTitleTh().replaceAll("\\s+", ""))
                                    .setName(item.getFirstnameTh().replaceAll("\\s+", ""))
                                    .setLastname(item.getLastnameTh().replaceAll("\\s+", ""))
                                    .setSegmant(item.getSegment()).setJointAccountStatus(item.getJointAccountStatus())
                                    .setSensitiveAccount(item.getSensitiveCustomer())
                                    .setCitizenImage(item.getCifImage()).setSignImage(item.getSignImage())
                                    .setMobileNo((item.getMobileNo())).setAddress(item.getAddress());

                            response.addCustomerEntity(cust);
                        }
                    }

                    if (custList.getReturnResult() != null) {
                        // TODO: ReturnResult
                        var errorEntity = ReturnResult.newBuilder();
                        if (!StringUtil.isNullOrEmpty(custList.getReturnResult().getResultCode()))
                            errorEntity.setResultCode(custList.getReturnResult().getResultCode());

                        if (!StringUtil.isNullOrEmpty(custList.getReturnResult().getResult()))
                            errorEntity.setResult(custList.getReturnResult().getResult());

                        if (!StringUtil.isNullOrEmpty(custList.getReturnResult().getResultDescription()))
                            errorEntity.setResultDescription(custList.getReturnResult().getResultDescription());

                        if (!StringUtil.isNullOrEmpty(custList.getReturnResult().getErrorRefId()))
                            errorEntity.setErrorRefId(custList.getReturnResult().getErrorRefId());

                        errorEntity.setResultTimestamp(dateTimeUtil.GetTimeStamp());

                        response.setReturnResult(errorEntity);
                    }

                }

            }

            // TODO: Return
            responseObserver.onNext(response.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            // TODO: handle exception
            responseObserver.onError(Status.INTERNAL
                    .withDescription("searchCustomerController : Cannot Connect to Service Because : " + e)
                    .asRuntimeException());
        }
    }

    @Override
    public void getFromWallet(GetFromWalletRequest request, StreamObserver<GetFromWalletResponse> responseObserver) {
        var response = GetFromWalletResponse.newBuilder();

        try {
            boolean isProcess = true;

            // TODO: Validate Parameter
            if (isProcess)
                if (StringUtil.isNullOrEmpty(request.getCustId())) {
                    isProcess = false;
                    var err = ReturnResult.newBuilder().setResultCode("401").setResult("unauthorize")
                            .setResultDescription("การยืนยันตัวตนผิดพลาด");

                    response.setReturnResult(err);
                }

            // TODO: CallApi
            if (isProcess) {
                WalletListResponse walletList = customerService.GetFromWallet(request.getCustId());

                // TODO: Map To Response
                if (walletList != null) {
                    if (walletList.getWalletList() != null && walletList.getWalletList().size() > 0) {
                        for (WalletDto item : walletList.getWalletList()) {
                            var wallet = WalletList.newBuilder().setWalletId(item.getWalletId())
                                    .setWalletName(item.getWalletName()).setBalance(item.getBalance().doubleValue())
                                    .setBankCode(item.getBankCode());
                            response.addWalletList(wallet);
                        }
                    }

                    if (walletList.getReturnResult() != null) {
                        // TODO: ReturnResult
                        var errorEntity = ReturnResult.newBuilder();
                        if (!StringUtil.isNullOrEmpty(walletList.getReturnResult().getResultCode()))
                            errorEntity.setResultCode(walletList.getReturnResult().getResultCode());

                        if (!StringUtil.isNullOrEmpty(walletList.getReturnResult().getResult()))
                            errorEntity.setResult(walletList.getReturnResult().getResult());

                        if (!StringUtil.isNullOrEmpty(walletList.getReturnResult().getResultDescription()))
                            errorEntity.setResultDescription(walletList.getReturnResult().getResultDescription());

                        if (!StringUtil.isNullOrEmpty(walletList.getReturnResult().getErrorRefId()))
                            errorEntity.setErrorRefId(walletList.getReturnResult().getErrorRefId());

                        errorEntity.setResultTimestamp(dateTimeUtil.GetTimeStamp());

                        response.setReturnResult(errorEntity);
                    }

                } else {
                    var err = ReturnResult.newBuilder().setResultCode("500").setResult("lost connect")
                            .setResultDescription("ไม่สามารถเชื่อมต่อได้ กรุณาติดต่ผู้ให้บริการ");

                    response.setReturnResult(err);
                }
            }

            // TODO: Return
            responseObserver.onNext(response.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            // TODO: handle exception
            responseObserver.onError(Status.INTERNAL
                    .withDescription("getFromWalletController : Cannot Connect to Service Because : " + e)
                    .asRuntimeException());
        }
    }

    @Override
    public void getToWallet(GetToWalletRequest request, StreamObserver<GetToWalletResponse> responseObserver) {
        var response = GetToWalletResponse.newBuilder();

        try {
            // TODO: CallApi
            var walletResponse = customerService.GetToWallet(request.getWalletId());

            // TODO: Map To Response
            if (walletResponse != null && walletResponse.getReturnResult() != null) {
                response.setWalletName(walletResponse.getWalletName());

                // TODO: ReturnResult
                var errorEntity = ReturnResult.newBuilder();
                if (!StringUtil.isNullOrEmpty(walletResponse.getReturnResult().getResultCode()))
                    errorEntity.setResultCode(walletResponse.getReturnResult().getResultCode());

                if (!StringUtil.isNullOrEmpty(walletResponse.getReturnResult().getResult()))
                    errorEntity.setResult(walletResponse.getReturnResult().getResult());

                if (!StringUtil.isNullOrEmpty(walletResponse.getReturnResult().getResultDescription()))
                    errorEntity.setResultDescription(walletResponse.getReturnResult().getResultDescription());

                if (!StringUtil.isNullOrEmpty(walletResponse.getReturnResult().getErrorRefId()))
                    errorEntity.setErrorRefId(walletResponse.getReturnResult().getErrorRefId());

                errorEntity.setResultTimestamp(dateTimeUtil.GetTimeStamp());
                response.setReturnResult(errorEntity);

            } else {
                // TODO: Handle Error
                var err = ReturnResult.newBuilder().setResultCode("500").setResult("lost connect")
                        .setResultDescription("ไม่สามารถเชื่อมต่อได้ กรุณาติดต่ผู้ให้บริการ");

                response.setReturnResult(err);
            }

            // TODO: Return
            responseObserver.onNext(response.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            // TODO: handle exception
            responseObserver.onError(
                    Status.INTERNAL.withDescription("getToWalletController : Cannot Connect to Service Because : " + e)
                            .asRuntimeException());
        }
    }
}
