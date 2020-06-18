package com.example.fashionstoreapp.DTO.Responses;

public class LoginResponse {
   private  String token;
   private String id;
   private String username;
   private String role;
   private String tokenExpireTime;

    public LoginResponse() {
    }

    public LoginResponse(String token, String id, String username, String role, String tokenExpireTime) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.role = role;
        this.tokenExpireTime = tokenExpireTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(String tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }
}
