package com.madapps.marketappprototype1.activities.admin;


public class OrderModel {
    String username;
    String userid;

    public OrderModel() {

    }

    public OrderModel(String username,String userid) {
        this.username = username;
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
