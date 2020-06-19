package com.example.fashionstoreapp.RetrofitAPIService;

import com.example.fashionstoreapp.CallBacks.CustomizeCallback;
import com.example.fashionstoreapp.DTO.Requests.LoginRequest;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Models.User;
import com.example.fashionstoreapp.RetrofitClient.RetrofitClient;
import com.example.fashionstoreapp.RetrofitInterface.AuthenticationApi;
import com.example.fashionstoreapp.RetrofitInterface.ResponseCallBackInterface;

import retrofit2.Call;

public class AuthenticationService {
    AuthenticationApi authenticationApi;
    RetrofitClient retrofitClient;
    public AuthenticationService() {
        this.authenticationApi = retrofitClient.getRetrofitClientInstance().create(AuthenticationApi.class);
    }

    public void login(LoginRequest request, ResponseCallBackInterface callback) {
        Call<LoginResponse> loginResponseCall = authenticationApi.loginUser(request);
        loginResponseCall.enqueue(new CustomizeCallback<LoginResponse>(callback));
    }

    public void register(User user, ResponseCallBackInterface callback) {
        Call<User> userCall = authenticationApi.registerUser(user);
        userCall.enqueue(new CustomizeCallback<User>(callback));
    }
}
