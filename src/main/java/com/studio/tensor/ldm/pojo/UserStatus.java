package com.studio.tensor.ldm.pojo;

public class UserStatus {
    private Integer id;

    private Integer userId;

    private String statusJson;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatusJson() {
        return statusJson;
    }

    public void setStatusJson(String statusJson) {
        this.statusJson = statusJson == null ? null : statusJson.trim();
    }
}