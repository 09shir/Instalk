package com.instalk.backend.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Following {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name="acc_id", nullable=false)
    private Account account;

    @ManyToOne
    @JoinColumn(name="targetAcc_id", nullable=false)
    private TargetAccount targetAccount;
    
    @Temporal(TemporalType.DATE)
    private Date date;

    private String following;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TargetAccount getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(TargetAccount targetAccount) {
        this.targetAccount = targetAccount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    
    
}
