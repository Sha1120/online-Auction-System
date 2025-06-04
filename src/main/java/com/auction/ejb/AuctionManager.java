package com.auction.ejb;

import com.auction.model.Actions;
import com.auction.model.User;
import jakarta.ejb.Remote;
import java.util.Date;
import java.util.List;

@Remote
public interface AuctionManager {
    Actions createAuction(String title, String desc, double startBid, Date endDate, User owner);

    Actions createAuction(String name, String desc, double startBid, Date endTime);
    List<Actions> getAllAuctions();
    boolean placeBid(long auctionId, String bidder, double amount);

    boolean placeBid(long auctionId, User bidder, double amount);
}