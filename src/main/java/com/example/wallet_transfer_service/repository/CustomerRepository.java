package com.example.wallet_transfer_service.repository;

import java.util.List;

import com.example.wallet_transfer_service.model.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String>, CustomerRepositoryCustom {

    public List<Customer> findAll();

}
