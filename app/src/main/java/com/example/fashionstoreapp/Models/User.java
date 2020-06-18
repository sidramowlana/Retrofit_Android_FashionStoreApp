package com.example.fashionstoreapp.Models;

public class User {
    private Integer userId;
    private String email, username, password, address, phone;

    public User(String email, String username, String password, String address, String phone) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }

    public User(int userId, String email, String username, String address, String phone) {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}