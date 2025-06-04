package com.auction.ejb;

import com.auction.model.AuctionItem;
import jakarta.ejb.Remote;
import java.util.Date;
import java.util.List;

@Remote
public interface AuctionManager {
    AuctionItem createAuction(String name, String desc, double startBid, Date endTime);
    List<AuctionItem> getAllAuctions();
    boolean placeBid(long auctionId, String bidder, double amount);
}