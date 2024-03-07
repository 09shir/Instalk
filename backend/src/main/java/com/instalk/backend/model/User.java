package com.instalk.backend.model;

public class User {
    private String full_name;
    private String username;

    public User(String full_name, String username){
        this.full_name = full_name;
        this.username = username;
    }

    public String getFull_name() {
        return full_name;
    }
    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
