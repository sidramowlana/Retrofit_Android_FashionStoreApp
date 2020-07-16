package com.example.fashionstoreapp.Models;

import java.util.List;

public class Orders {
    private Integer ordersId;
    private String date;
    private String status;
    private double total;
    private User user;
    private String userId;
    private List<Cart> cartList;

    public Orders(List<Cart>cartList, String date, String status, double total) {
        this.cartList = cartList;
        this.date = date;
        this.status = status;
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Integer getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }
}