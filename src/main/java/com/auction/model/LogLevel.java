package com.auction.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "log_level")
public class LogLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "level_name", nullable = false, length = 45)
    private String levelName;

    @OneToMany(mappedBy = "logLevel")
    private List<SystemLogs> systemLogs;

    // getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public List<SystemLogs> getSystemLogs() {
        return systemLogs;
    }

    public void setSystemLogs(List<SystemLogs> systemLogs) {
        this.systemLogs = systemLogs;
    }
}