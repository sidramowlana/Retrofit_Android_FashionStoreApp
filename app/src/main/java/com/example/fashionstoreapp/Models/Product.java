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
    private String category;
    private double price;
    private int quantity;
    private String scaledImage;
    private List<ProductTag> productTag;

    public Product(String productName, String description, int quantity, double price) {
        this.productName = productName;
        this.shortDescription = description;
        this.quantity = quantity;
        this.price = price;
    }

    public Product(String productName, String shortDescription, double price, int quantity, String scaledImage) {
        this.productName = productName;
        this.shortDescription = shortDescription;
        this.price = price;
        this.quantity = quantity;
        this.scaledImage = scaledImage;
    }
}