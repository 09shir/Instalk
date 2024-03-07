package com.instalk.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.instalk.backend.exception.AccNotFoundException;
import com.instalk.backend.model.TargetAccount;
import com.instalk.backend.repository.TargetAccountRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@CrossOrigin(origins="*")
public class TargetAccountController {
    
    @Autowired
    private TargetAccountRepository targetAccountRepository;

    @PostMapping("/targetAccount")
    TargetAccount newTargetAccount(@RequestBody TargetAccount newTargetAccount) {
        return targetAccountRepository.save(newTargetAccount);
    }

    @GetMapping("/targetAccounts")
    List<TargetAccount> getAllTargetAccounts() {
        return targetAccountRepository.findAll();
    }

    @GetMapping("/targetAccount/{id}")
    TargetAccount getTargetAccountById(@PathVariable Long id){
        return targetAccountRepository.findById(id).orElseThrow(() -> new AccNotFoundException(id));
    }
    
    @GetMapping("/targetAccounts/{acc_id}")
    List<TargetAccount> getTargetAccountsByAccId(@PathVariable Long acc_id){
        return targetAccountRepository.findByAccId(acc_id);
    }

    @DeleteMapping("/targetAccount/{id}")
    String deleteTargetAccount(@PathVariable Long id){
        if (!targetAccountRepository.existsById(id)){
            throw new AccNotFoundException(id);
        }
        targetAccountRepository.deleteById(id);
        return "Target Account with id " + id + " has been successfully deleted";
    }
    
}
