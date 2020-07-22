package com.example.fashionstoreapp.DTO.Responses;

public class LoginResponse {
    private String token;
    private String id;
    private String username;
    private String email;
    private String roles;
    private String tokenExpireTime;


    public LoginResponse() {
    }

    public LoginResponse(String token, String id, String username, String email, String roles, String tokenExpireTime) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.tokenExpireTime = tokenExpireTime;
    }

    public LoginResponse(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(String tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }

}
