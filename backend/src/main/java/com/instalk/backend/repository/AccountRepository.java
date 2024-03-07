package com.instalk.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.instalk.backend.model.Account;

public interface AccountRepository extends JpaRepository<Account,Long> {
     
}
