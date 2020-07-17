package com.example.fashionstoreapp.Models;

public class Cart {

    private Integer cartId;
    private User user;
    private Product product;
    private int quantity;
    private String size;
    private double total;
    private boolean isPurchased;
//    private Orders orders;

    public Cart() {
    }

    public Cart(User user, Product product, int quantity, String size, double total, boolean isPurchased) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.size = size;
        this.total = total;
        this.isPurchased = false;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }
}
