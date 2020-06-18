package com.example.fashionstoreapp.Models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class Product{
private Integer productId;
    @SerializedName("Name")
    private String name;
    @SerializedName("ShortDescription")
    private String shortDescription;
    @SerializedName("LongDescription")
    private String longDescription;
    @SerializedName("Category")
    private String category;
    @SerializedName("Price")
    private double price;
    @SerializedName("Quantity")
    private int quantity;
    @SerializedName("ScaledImage")
    private String scaledImage;
    @SerializedName("FullImage")
    private String fullImage;

    @SerializedName("Tag")
    private ArrayList<String> tags;

    public Product() {
    }

    public Product(String name, String shortDescription, String longDescription, String category, double price, int quantity, String scaledImage, String fullImage, ArrayList<String> tags) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.scaledImage = scaledImage;
        this.fullImage = fullImage;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}