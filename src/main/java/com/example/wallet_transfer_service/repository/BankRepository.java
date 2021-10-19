package com.example.wallet_transfer_service.repository;

import java.util.List;
import java.util.Optional;

import com.example.wallet_transfer_service.model.Bank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, String> {

    public List<Bank> findAll();
}
