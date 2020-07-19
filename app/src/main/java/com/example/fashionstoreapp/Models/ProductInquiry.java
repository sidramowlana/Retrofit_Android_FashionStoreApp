package com.example.fashionstoreapp.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInquiry {
    private User user;
    private String question;
    private Product product;
    private String date;
    private String answers;

    public ProductInquiry(String question, String date) {
        this.question = question;
        this.date = date;
    }
}