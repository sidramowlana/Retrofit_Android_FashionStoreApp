package com.example.fashionstoreapp.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    private Integer cartId;
    private User user;
    private Product product;
    private int quantity;
    private String size;
    private double total;
    private boolean isPurchased;

    public Cart(User user, Product product, int quantity, String size, double total, boolean isPurchased) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.size = size;
        this.total = total;
        this.isPurchased = false;
    }
}