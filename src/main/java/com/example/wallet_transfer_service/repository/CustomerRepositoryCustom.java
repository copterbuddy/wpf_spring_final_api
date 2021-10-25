package com.example.wallet_transfer_service.repository;

import java.util.List;

import com.example.wallet_transfer_service.model.Customer;

public interface CustomerRepositoryCustom {

    public List<Customer> searchCustomers(String searchText, String searchType);
}
