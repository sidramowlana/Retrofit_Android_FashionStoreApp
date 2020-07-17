package com.example.fashionstoreapp.Models;

public class Orders {
    private Integer ordersId;
    private String date;
    private String status;
    private User user;
    private double total;

    public Orders(String date, String status, double total) {
        this.date = date;
        this.status = status;
        this.total = total;
    }

    public Orders() {

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