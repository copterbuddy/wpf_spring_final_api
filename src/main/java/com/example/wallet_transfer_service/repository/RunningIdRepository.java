package com.example.wallet_transfer_service.repository;

import java.util.List;
import java.util.Optional;

import com.example.wallet_transfer_service.model.RunningID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RunningIdRepository extends JpaRepository<RunningID, String> {

    // public Optional<RunningID> findById(String id);

    // public List<RunningID> findAll();

    public RunningID findByRunningType(String id);

    // public RunningID findTopByRunningTypeOrderByLastRunningIdDesc(String id);

}
