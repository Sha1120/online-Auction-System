package com.auction.model;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Actions {
    private Integer id;
    private String title;
    private String description;
    private String imgUrl;
    private Double startPrice;
    private Double currentPrice;
    private Date createDate;
    private Date endDate;

    private User user;
    private List<Bids> bids;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Double startPrice) {
        this.startPrice = startPrice;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Bids> getBids() {
        return bids;
    }

    public void setBids(List<Bids> bids) {
        this.bids = bids;
    }

    public String getTimeRemaining() {
        if (endDate == null) return "Unknown";

        long timeDiff = endDate.getTime() - System.currentTimeMillis();
        if (timeDiff <= 0) {
            return "Auction ended";
        }

        long hours = TimeUnit.MILLISECONDS.toHours(timeDiff);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDiff) % 60;
        return hours + "h " + minutes + "m remaining";
    }
}
