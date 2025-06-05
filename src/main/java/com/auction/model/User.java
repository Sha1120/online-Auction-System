package com.auction.model;

import java.util.Date;
import java.util.List;

public class User {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private Date joingDate;
    private Status status;
    private List<Actions> actions;
    private List<Bids> bids;
    private List<ContactMessage> contactMessages;

    // getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getJoingDate() {
        return joingDate;
    }

    public void setJoingDate(Date joingDate) {
        this.joingDate = joingDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Actions > getActions() {
        return actions;
    }

    public void setActions(List<Actions > actions) {
        this.actions = actions;
    }

    public List<Bids > getBids() {
        return bids;
    }

    public void setBids(List<Bids> bids) {
        this.bids = bids;
    }

    public List<ContactMessage> getContactMessages() {
        return contactMessages;
    }

    public void setContactMessages(List<ContactMessage> contactMessages) {
        this.contactMessages = contactMessages;
    }
}
