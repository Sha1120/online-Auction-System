package com.auction.model;

import java.util.List;

public class LogLevel {
    private Integer id;
    private String levelName;
    private List<SystemLogs> systemLogs;  // Use SystemLogsModel instead of entity

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
