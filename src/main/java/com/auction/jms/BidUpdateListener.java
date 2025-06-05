package com.auction.jms;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.*;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/auctionQueue")
        }
)
public class BidUpdateListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof ObjectMessage) {
                ObjectMessage objMsg = (ObjectMessage) message;
                BidInfo bidInfo = (BidInfo) objMsg.getObject();

                System.out.println("Bid updated: " + bidInfo.getTitle() + " - $" + bidInfo.getBidAmount());
                // Push update to clients here if needed
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
