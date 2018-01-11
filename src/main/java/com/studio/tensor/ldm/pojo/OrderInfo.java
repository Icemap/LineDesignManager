package com.studio.tensor.ldm.pojo;

import java.util.Date;

public class OrderInfo {
    private Integer id;

    private Integer orderUserId;

    private Integer orderRoleId;

    private Date orderStartTime;

    private Date orderEndTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(Integer orderUserId) {
        this.orderUserId = orderUserId;
    }

    public Integer getOrderRoleId() {
        return orderRoleId;
    }

    public void setOrderRoleId(Integer orderRoleId) {
        this.orderRoleId = orderRoleId;
    }

    public Date getOrderStartTime() {
        return orderStartTime;
    }

    public void setOrderStartTime(Date orderStartTime) {
        this.orderStartTime = orderStartTime;
    }

    public Date getOrderEndTime() {
        return orderEndTime;
    }

    public void setOrderEndTime(Date orderEndTime) {
        this.orderEndTime = orderEndTime;
    }
}