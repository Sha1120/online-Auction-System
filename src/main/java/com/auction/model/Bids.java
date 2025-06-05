package com.auction.model;

import java.util.Date;

public class Bids {
    private Integer id;
    private Double bidAmount;
    private Date bidTime;
    private String user;
    private Actions actions;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(Double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public Date getBidTime() {
        return bidTime;
    }

    public void setBidTime(Date bidTime) {
        this.bidTime = bidTime;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Actions getActions() {
        return actions;
    }

    public void setActions(Actions actions) {
        this.actions = actions;
    }
}
