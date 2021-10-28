package com.example.wallet_transfer_service.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.example.wallet_transfer_service.dto.CustomerDto;
import com.example.wallet_transfer_service.dto.CustomerListDto;
import com.example.wallet_transfer_service.dto.ReturnResult;
import com.example.wallet_transfer_service.dto.WalletDto;
import com.example.wallet_transfer_service.dto.WalletListResponse;
import com.example.wallet_transfer_service.dto.WalletResponse;
import com.example.wallet_transfer_service.model.Customer;
import com.example.wallet_transfer_service.model.RunningID;
import com.example.wallet_transfer_service.repository.CustomerRepository;
import com.example.wallet_transfer_service.repository.RunningIdRepository;
import com.example.wallet_transfer_service.utils.DateTimeUtil;
import com.example.wallet_transfer_service.utils.EnumUtil;
import com.example.wallet_transfer_service.utils.ErrorUtil;
import com.google.gson.Gson;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Transactional
public class CustomerService {

    @Autowired
    ErrorUtil errorUtil;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    DateTimeUtil dateTimeUtil;

    @Autowired
    EnumUtil enumUtil;

    @Autowired
    RunningIdRepository runningIdRepository;

    @Autowired
    LogService logService;

    private String _baseUrl = "http://gf-dev-bo-website-lb-1570887081.ap-southeast-1.elb.amazonaws.com:550/api/Wallet";

    public CustomerListDto GetCustomerList(String searchText, String searchType, String comName, String userId) {
        return GetCustomerListNew(searchText, searchType, comName, userId, true);
    }

    public CustomerListDto GetCustomerListNew(String searchText, String searchType, String comName, String userId,
            boolean isAdd) {

        // Create Response
        CustomerListDto response = new CustomerListDto();
        boolean isProcess = true;

        try {
            // Check Param
            if (StringUtil.isNullOrEmpty(searchText) || StringUtil.isNullOrEmpty(searchType)) {
                // Handle Error
                isProcess = false;
                response.setReturnResult(errorUtil.Error400());
            }

            if (StringUtil.isNullOrEmpty(comName) || StringUtil.isNullOrEmpty(userId)) {
                // Handle Error
                isProcess = false;
                response.setReturnResult(errorUtil.Error401());
            }

            // Get SearchType
            String getSearchType = "";
            if (isProcess) {
                getSearchType = enumUtil.GetSearchType(searchType);
                if (StringUtil.isNullOrEmpty(getSearchType)) {
                    isProcess = false;
                    response.setReturnResult(errorUtil.Error400());
                }
            }

            // TODO: Get CustList
            if (isProcess) {
                List<Customer> custList = customerRepository.searchCustomers(searchText, getSearchType);

                // TODO: Pass
                if (custList != null) {
                    if (custList.size() > 0) {
                        ModelMapper mapper = new ModelMapper();
                        List<CustomerDto> custListDto = custList.stream().map(o -> mapper.map(o, CustomerDto.class))
                                .collect(Collectors.toList());

                        response.setCustomerEntity(custListDto);
                    }
                }

                response.setReturnResult(errorUtil.SuccessResult());

                if (isAdd != false) {
                    Gson gson = new Gson();

                    var actFullDetail = gson.toJson(response);
                    logService.AddActivityLog(1, "SearchCustomer", actFullDetail, userId, comName, null,
                            response.getReturnResult().getResultCode(),
                            response.getReturnResult().getResultDescription(), "PAGE001", "TRANSFER_PAGE", null, null,
                            null, null, null, Date.from(Instant.now()), userId);
                }
            }

            // TODO: Failed

        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        }

        return response;
    }

    public WalletListResponse GetFromWallet(String custId) {

        // TODO: Create Response
        WalletListResponse response = new WalletListResponse();
        boolean isProcess = true;

        try {
            // TODO: Check Param
            if (isProcess)
                if (StringUtil.isNullOrEmpty(custId)) {
                    isProcess = false;
                    response.setReturnResult(errorUtil.Error400());
                }

            if (isProcess) {

                response.setWalletList(new ArrayList<>());

                // TODO: Get WalletList
                var responseWalletList = restTemplate.getForObject(_baseUrl + "/GetWallets?userid=" + custId.trim(),
                        WalletListResponse.class);

                if (responseWalletList != null) {
                    if (responseWalletList.getWalletList() != null
                            && responseWalletList.getReturnResult().getResultCode().equals("200")
                            && responseWalletList.getWalletList().size() > 0) {

                        for (WalletDto item : responseWalletList.getWalletList()) {
                            if (item.getWalletId() != null) {
                                WalletDto walletDto = new WalletDto();
                                walletDto = item;
                                var responseWalletInfo = restTemplate.getForObject(
                                        _baseUrl + "/GetBalance?walletid=" + item.getWalletId(), WalletDto.class);
                                if (responseWalletInfo != null) {
                                    if (responseWalletInfo.getReturnResult().getResultCode().equals("200")
                                            && responseWalletInfo.getBalance() != null) {
                                        walletDto.setBalance(responseWalletInfo.getBalance());
                                        response.getWalletList().add(walletDto);
                                    }
                                }

                            }
                        }

                    }
                    if (responseWalletList.getReturnResult() != null)
                        response.setReturnResult(responseWalletList.getReturnResult());

                } else {
                    response.setReturnResult(errorUtil.Error500());
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        }

        return response;
    }

    public WalletResponse GetToWallet(String toWalletId) {
        WalletResponse response = new WalletResponse();
        boolean isProcess = true;

        try {
            // TODO: Check Param
            if (isProcess)
                if (StringUtil.isNullOrEmpty(toWalletId)) {
                    isProcess = false;
                    response.setReturnResult(errorUtil.Error400());
                }

            // TODO: Get ToWallet
            var responseToWallet = restTemplate.getForObject(_baseUrl + "/GetWalletInfo?walletid=" + toWalletId,
                    WalletDto.class);

            if (responseToWallet != null) {
                if (!StringUtil.isNullOrEmpty(responseToWallet.getWalletName())) {
                    response.setWalletName(responseToWallet.getWalletName());
                }
                if (responseToWallet.getReturnResult() != null) {
                    response.setReturnResult(responseToWallet.getReturnResult());
                }
            } else {
                response.setReturnResult(errorUtil.Error500());
            }

        } catch (

        Exception e) {
            // TODO: handle exception
            throw e;
        }

        return response;
    }
}
