package com.auction.jms;

import com.auction.model.AuctionItem;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.*;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/auctionQueue")
        }
)
public class BidUpdateListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof ObjectMessage) {
                AuctionItem item = (AuctionItem) ((ObjectMessage) message).getObject();
                // Here you would push update to clients (e.g., via WebSocket or similar)
                System.out.println("Bid updated: " + item.getName() + " - " + item.getCurrentBid());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}