package com.auction.model;

import java.io.Serializable;
import java.util.Date;

public class AuctionItem implements Serializable {
    private Long id;
    private String name;
    private String description;
    private double startingBid;
    private double currentBid;
    private String highestBidder;
    private Date endTime;
    private boolean active;

    // Constructors, getters and setters


    public AuctionItem() {
    }

    public AuctionItem(Long id, String name, String description, double startingBid, double currentBid, String highestBidder, Date endTime, boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startingBid = startingBid;
        this.currentBid = currentBid;
        this.highestBidder = highestBidder;
        this.endTime = endTime;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getStartingBid() {
        return startingBid;
    }

    public void setStartingBid(double startingBid) {
        this.startingBid = startingBid;
    }

    public double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(double currentBid) {
        this.currentBid = currentBid;
    }

    public String getHighestBidder() {
        return highestBidder;
    }

    public void setHighestBidder(String highestBidder) {
        this.highestBidder = highestBidder;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}