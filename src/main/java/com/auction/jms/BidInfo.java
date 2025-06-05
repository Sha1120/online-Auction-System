package com.auction.jms;

import java.io.Serializable;

public class BidInfo implements Serializable {
    private String title;
    private double bidAmount;

    public BidInfo() {}

    public BidInfo(String title, double bidAmount) {
        this.title = title;
        this.bidAmount = bidAmount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }
}
