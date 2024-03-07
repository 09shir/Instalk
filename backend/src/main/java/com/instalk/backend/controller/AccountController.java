package com.instalk.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.instalk.backend.exception.AccNotFoundException;
import com.instalk.backend.model.Account;
import com.instalk.backend.repository.AccountRepository;

@RestController
@CrossOrigin(origins="*")
public class AccountController {
    
    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/account")
    Account newAccount(@RequestBody Account newAccount){
        return accountRepository.save(newAccount);
    }

    @GetMapping("/accounts")
    List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    @GetMapping("/account/{id}")
    Account getAccountById(@PathVariable Long id){
        return accountRepository.findById(id).orElseThrow(() -> new AccNotFoundException(id));
    }

    @PutMapping("/account/{id}")
    Account updateAccount(@RequestBody Account newAccount, @PathVariable long id){
        return accountRepository.findById(id)
            .map(account -> {
                account.setUsername(newAccount.getUsername());
                account.setPassword(newAccount.getPassword());
                return accountRepository.save(account);
            }).orElseThrow(() -> new AccNotFoundException(id));
    }

    @DeleteMapping("/account/{id}")
    String deleteAccount(@PathVariable Long id){
        if (!accountRepository.existsById(id)){
            throw new AccNotFoundException(id);
        }
        accountRepository.deleteById(id);
        return "Account with id " + id + " has been successfully deleted";
    }

}
