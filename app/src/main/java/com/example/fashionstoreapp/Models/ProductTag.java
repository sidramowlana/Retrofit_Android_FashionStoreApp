package com.example.fashionstoreapp.Models;


public class ProductTag {
private Product product;
private Tag tag;

    public ProductTag() {
    }

    public ProductTag(Product product, Tag tag) {
        this.product = product;
        this.tag = tag;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
