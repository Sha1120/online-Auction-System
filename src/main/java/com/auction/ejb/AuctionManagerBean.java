package com.auction.ejb;

import com.auction.model.AuctionItem;
import jakarta.ejb.Stateless;
import jakarta.annotation.Resource;
import jakarta.jms.*;

import java.util.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

@Stateless
public class AuctionManagerBean implements AuctionManager {
    private static final Map<Long, AuctionItem> auctions = new ConcurrentHashMap<>();
    private static long idCounter = 1;

    @Resource(lookup = "jms/auctionQueue")
    private Queue auctionQueue;
    @Resource
    private ConnectionFactory connectionFactory;

    @Override
    public synchronized AuctionItem createAuction(String name, String desc, double startBid, Date endTime) {
        AuctionItem item = new AuctionItem();
        item.setId(idCounter++);
        item.setName(name);
        item.setDescription(desc);
        item.setStartingBid(startBid);
        item.setCurrentBid(startBid);
        item.setEndTime(endTime);
        item.setActive(true);
        auctions.put(item.getId(), item);
        return item;
    }

    @Override
    public List<AuctionItem> getAllAuctions() {
        return new ArrayList<>(auctions.values());
    }

    @Override
    public synchronized boolean placeBid(long auctionId, String bidder, double amount) {
        AuctionItem item = auctions.get(auctionId);
        if (item == null || !item.isActive() || amount <= item.getCurrentBid() || new Date().after(item.getEndTime())) {
            return false;
        }
        item.setCurrentBid(amount);
        item.setHighestBidder(bidder);

        // Broadcast bid update
        sendBidUpdate(item);

        return true;
    }

    private void sendBidUpdate(AuctionItem item) {
        try (JMSContext context = connectionFactory.createContext()) {
            ObjectMessage msg = context.createObjectMessage(item);
            context.createProducer().send((Destination) auctionQueue, msg);
        }
    }
}