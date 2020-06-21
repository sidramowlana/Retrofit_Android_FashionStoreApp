package com.example.fashionstoreapp.Models;

public class Wishlist {
    private User user;
    private Product product;
//    private boolean isFavourite;

    public Wishlist() {
    }

    public Wishlist(User user, Product product) {
        this.user = user;
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
//
//    public boolean isFavourite() {
//        return isFavourite;
//    }
//
//    public void setFavourite(boolean favourite) {
//        isFavourite = favourite;
//    }
}