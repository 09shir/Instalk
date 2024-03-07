package com.instalk.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.instalk.backend.model.TargetAccount;

public interface TargetAccountRepository extends JpaRepository<TargetAccount,Long> {

    @Query(value = "SELECT * FROM target_account T WHERE T.acc_id = :acc_id", nativeQuery = true)
    List<TargetAccount> findByAccId(@Param("acc_id") Long acc_id);
    
}
