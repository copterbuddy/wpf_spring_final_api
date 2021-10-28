package com.example.wallet_transfer_service.repository;

import java.math.BigInteger;
import java.util.List;

import com.example.wallet_transfer_service.model.LogTransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogTransactionRepository extends JpaRepository<LogTransaction, BigInteger> {
    public LogTransaction findByTranId(BigInteger tranId);
}
