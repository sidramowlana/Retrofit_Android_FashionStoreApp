package com.example.fashionstoreapp.Models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Integer productId;

    private String productName;
    private String shortDescription;
    private String longDescription;
    private String category;
    private double price;
    private int quantity;
    private String scaledImage;
    private String fullImage;
    private List<ProductTag> productTag;

}