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
public class FollowerUpdate {
    
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

    private String added;

    private String removed;

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

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public String getRemoved() {
        return removed;
    }

    public void setRemoved(String removed) {
        this.removed = removed;
    }

    
}
