package com.auction.ejb;

import com.auction.model.Actions;
import com.auction.model.User;
import jakarta.ejb.Stateless;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.jms.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Stateless
public class AuctionManagerBean implements AuctionManager {

    @PersistenceContext(unitName = "AuctionPU")
    private EntityManager em;

    @Resource(lookup = "jms/auctionQueue")
    private Queue auctionQueue;

    @Resource
    private ConnectionFactory connectionFactory;

    @Override
    public Actions createAuction(String title, String desc, double startBid, Date endDate, User owner) {
        Actions item = new Actions();
        item.setTitle(title);
        item.setDescription(desc);
        item.setStartPrice(startBid);
        item.setCurrentPrice(startBid);
        item.setCreateDate(new Date());
        item.setEndDate(endDate);
        item.setUser(owner);
        // TODO: set ActionStatus and Category as needed
        em.persist(item);
        return item;
    }

    @Override
    public Actions createAuction(String name, String desc, double startBid, Date endTime) {
        return null;
    }

    @Override
    public List<Actions> getAllAuctions() {
        return em.createQuery("SELECT a FROM Actions a", Actions.class).getResultList();
    }

    @Override
    public boolean placeBid(long auctionId, String bidder, double amount) {
        return false;
    }

    @Override
    public boolean placeBid(long auctionId, User bidder, double amount) {
        Actions item = em.find(Actions.class, auctionId);
        if (item == null || amount <= item.getCurrentPrice() || new Date().after(item.getEndDate())) {
            return false;
        }
        item.setCurrentPrice(amount);
        // Optionally track highest bidder or add a Bid entity, e.g.:
        // Bids newBid = new Bids();
        // newBid.setActions(item);
        // newBid.setUser(bidder);
        // newBid.setBidAmount(amount);
        // newBid.setBidTime(new Date());
        // em.persist(newBid);

        em.merge(item);
        sendBidUpdate(item);
        return true;
    }

    private void sendBidUpdate(Actions item) {
        try (JMSContext context = connectionFactory.createContext()) {
            ObjectMessage msg = context.createObjectMessage((Serializable) item);
            context.createProducer().send(auctionQueue, msg);
        }
    }
}