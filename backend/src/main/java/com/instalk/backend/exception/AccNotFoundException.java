package com.instalk.backend.exception;

public class AccNotFoundException extends RuntimeException{
    public AccNotFoundException(Long id){
        super("Could not find the account with id " + id);
    }
}
