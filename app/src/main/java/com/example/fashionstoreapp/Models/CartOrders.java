package com.example.fashionstoreapp.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartOrders {
    private Integer cardOrderId;
    private Cart cart;
    private Orders orders;

    public CartOrders(Cart cart, Orders orders) {
        this.cart = cart;
        this.orders = orders;
    }
}