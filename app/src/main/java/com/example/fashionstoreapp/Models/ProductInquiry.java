package com.example.fashionstoreapp.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInquiry {
    private Integer productInquiryId;
    private User user;
    private String question;
    private Product product;
    private String date;
    private String answers;
    private boolean isReplied;

    public ProductInquiry(String question, String date) {
        this.question = question;
        this.date = date;
    }
    public ProductInquiry(String answers,boolean isReplied){
        this.answers = answers;
        this.isReplied = isReplied;
    }
}