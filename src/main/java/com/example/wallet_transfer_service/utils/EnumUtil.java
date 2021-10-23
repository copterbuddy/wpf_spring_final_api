package com.example.wallet_transfer_service.utils;

import org.springframework.stereotype.Component;

@Component
public class EnumUtil {

    public String GetSearchType(String searchType) {
        String response = "";
        switch (searchType) {
        case "1":
            response = "cifId";
            break;
        case "2":
            response = "passportNo";
            break;
        case "3":
            response = "name";
            break;
        default:
            response = "";
            break;
        }

        return response;
    }
}
