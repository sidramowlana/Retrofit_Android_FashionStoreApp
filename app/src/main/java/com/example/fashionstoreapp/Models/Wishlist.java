package com.example.fashionstoreapp.Models;

public class Wishlist {
   private User favouriteUser;
    private Product favouriteProduct;

    public Wishlist() {
    }

    public Wishlist(User favouriteUser, Product favouriteProduct) {
        this.favouriteUser = favouriteUser;
        this.favouriteProduct = favouriteProduct;
    }

    public User getFavouriteUser() {
        return favouriteUser;
    }

    public void setFavouriteUser(User favouriteUser) {
        this.favouriteUser = favouriteUser;
    }

    public Product getFavouriteProduct() {
        return favouriteProduct;
    }

    public void setFavouriteProduct(Product favouriteProduct) {
        this.favouriteProduct = favouriteProduct;
    }
}
