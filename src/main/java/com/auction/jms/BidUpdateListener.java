package com.auction.jms;

import com.auction.websocket.BidWebSocketServer;
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

                String notification = String.format("New bid on %s: $%.2f", bidInfo.getTitle(), bidInfo.getBidAmount());

                // Broadcast to all WebSocket clients
                BidWebSocketServer.broadcast(notification);

                // Optionally keep logging
                System.out.println("Bid updated: " + bidInfo.getTitle() + " - $" + bidInfo.getBidAmount());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}