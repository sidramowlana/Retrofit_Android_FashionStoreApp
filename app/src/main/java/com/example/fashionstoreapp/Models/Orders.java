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

    private String postalCode;
    private String city;
    private String address;

    public Orders(String postalCode,String city,String address,String date, String status, double total) {
        this.postalCode=postalCode;
        this.city=city;
        this.address=address;
        this.date = date;
        this.status = status;
        this.total = total;
    }


}