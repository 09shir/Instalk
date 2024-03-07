package com.instalk.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.instalk.backend.model.Following;

import jakarta.transaction.Transactional;

public interface FollowingRepository extends JpaRepository<Following,Long> {
     
    @Query(value = "SELECT * FROM Following F WHERE F.acc_id = :acc_id", nativeQuery = true)
    List<Following> findByAccId(@Param("acc_id") Long acc_id);

    @Query(value = "SELECT * FROM Following F WHERE F.target_acc_id = :target_acc_id", nativeQuery = true)
    List<Following> findByTargetAccId(@Param("target_acc_id") Long target_acc_id);

    @Query(value = "SELECT CASE WHEN EXISTS (SELECT * FROM Following F WHERE F.acc_id = :acc_id) THEN TRUE ELSE FALSE END", nativeQuery = true)
    int existsByAccId(@Param("acc_id") Long acc_id);

    @Query(value = "SELECT CASE WHEN EXISTS (SELECT * FROM Following F WHERE F.target_acc_id = :target_acc_id) THEN TRUE ELSE FALSE END", nativeQuery = true)
    int existsByTargetAccId(@Param("target_acc_id") Long target_acc_id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Following F WHERE F.acc_id = :acc_id", nativeQuery = true)
    void deleteByAccId(@Param("acc_id") Long acc_id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Following F WHERE F.target_acc_id = :target_acc_id", nativeQuery = true)
    void deleteByTargetAccId(@Param("target_acc_id") Long target_acc_id);
    
}
