package com.auction.model;

import jakarta.persistence.*;


import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name", nullable = false, length = 45)
    private String userName;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 10)
    private String password;

    @Column(name = "joing_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date joingDate;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @OneToMany(mappedBy = "user")
    private List<Actions> actions;

    @OneToMany(mappedBy = "user")
    private List<Bids> bids;

    @OneToMany(mappedBy = "user")
    private List<ContactMessage> contactMessages;

    // getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getJoingDate() {
        return joingDate;
    }

    public void setJoingDate(Date joingDate) {
        this.joingDate = joingDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Actions> getActions() {
        return actions;
    }

    public void setActions(List<Actions> actions) {
        this.actions = actions;
    }

    public List<Bids> getBids() {
        return bids;
    }

    public void setBids(List<Bids> bids) {
        this.bids = bids;
    }

    public List<ContactMessage> getContactMessages() {
        return contactMessages;
    }

    public void setContactMessages(List<ContactMessage> contactMessages) {
        this.contactMessages = contactMessages;
    }
}