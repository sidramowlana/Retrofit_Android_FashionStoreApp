package com.example.fashionstoreapp.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateReview {
    private User user;
    private Product product;
    private int rate = 0;
    private String feedback;
    private String date;

    public RateReview(Product product, int rate, String feedback, String date) {
        this.product = product;
        this.rate = rate;
        this.feedback = feedback;
        this.date = date;
    }
}