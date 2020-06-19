package com.example.fashionstoreapp.Models;

import java.util.List;

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

    public Product() {
    }

    public Product(Integer productId, String productName, String shortDescription, String longDescription, String category, double price, int quantity, String scaledImage, String fullImage, List<ProductTag> productTag) {
        this.productId = productId;
        this.productName = productName;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.scaledImage = scaledImage;
        this.fullImage = fullImage;
        this.productTag = productTag;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getScaledImage() {
        return scaledImage;
    }

    public void setScaledImage(String scaledImage) {
        this.scaledImage = scaledImage;
    }

    public String getFullImage() {
        return fullImage;
    }

    public void setFullImage(String fullImage) {
        this.fullImage = fullImage;
    }

    public List<ProductTag> getProductTag() {
        return productTag;
    }

    public void setProductTag(List<ProductTag> productTag) {
        this.productTag = productTag;
    }
}