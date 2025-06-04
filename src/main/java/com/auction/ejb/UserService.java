package com.auction.ejb;

import com.auction.model.Status;
import com.auction.model.User;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.util.Date;

@Stateless
public class UserService {

    @PersistenceContext(unitName = "AuctionPU")
    private EntityManager em;

    public String registerUser(String userName, String email, String password) {
        long count = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();

        if (count > 0) {
            return "Email already registered.";
        }

        Status status = em.find(Status.class, 1);
        if (status == null) {
            return "Server error: Default user status not found.";
        }

        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(password);
        user.setJoingDate(new Date());
        user.setStatus(status);

        em.persist(user);
        return "success";
    }
}
