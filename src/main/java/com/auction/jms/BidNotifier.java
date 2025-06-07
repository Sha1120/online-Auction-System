package com.auction.jms;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.jms.*;

@Stateless
public class BidNotifier {

    @Resource(lookup = "jms/auctionConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "jms/auctionQueue")
    private Queue bidQueue;

    public void notifyNewBid(String title, double bidAmount) {
        try (Connection connection = connectionFactory.createConnection();
             Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {

            connection.start();
            MessageProducer producer = session.createProducer(bidQueue);

            BidInfo bidInfo = new BidInfo(title, bidAmount);
            ObjectMessage message = session.createObjectMessage(bidInfo);

            producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
