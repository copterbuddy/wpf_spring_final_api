package com.example.wallet_transfer_service.repository;

import com.example.wallet_transfer_service.model.LogActivity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogActivityRepository extends JpaRepository<LogActivity, Integer> {

}
