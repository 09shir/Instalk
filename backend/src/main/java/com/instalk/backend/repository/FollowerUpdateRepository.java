package com.instalk.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.instalk.backend.model.FollowerUpdate;

public interface FollowerUpdateRepository extends JpaRepository<FollowerUpdate,Long> {
     
    @Query(value = "SELECT * FROM follower_update F WHERE F.acc_id = :acc_id", nativeQuery = true)
    List<FollowerUpdate> findByAccId(@Param("acc_id") Long acc_id);

    @Query(value = "SELECT * FROM follower_update F WHERE F.target_acc_id = :target_acc_id ORDER BY date DESC", nativeQuery = true)
    List<FollowerUpdate> findByTargetAccId(@Param("target_acc_id") Long target_acc_id);
}
