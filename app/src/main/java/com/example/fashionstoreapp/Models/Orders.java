package com.example.fashionstoreapp.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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


}