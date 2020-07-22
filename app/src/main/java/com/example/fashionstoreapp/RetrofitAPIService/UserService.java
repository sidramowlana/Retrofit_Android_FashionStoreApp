package com.example.fashionstoreapp.RetrofitAPIService;

import com.example.fashionstoreapp.CallBacks.CustomizeCallback;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Requests.AddressRequest;
import com.example.fashionstoreapp.Models.User;
import com.example.fashionstoreapp.RetrofitClient.RetrofitClient;
import com.example.fashionstoreapp.RetrofitInterface.UserApi;

import retrofit2.Call;

public class UserService {
    UserApi userApi;
    RetrofitClient retrofitClient;

    public UserService() {
        this.userApi = retrofitClient.getRetrofitClientInstance().create(UserApi.class);
    }

    public void updatePassword(Integer userId, String newPassword, String token, ResponseCallback callback) {
        Call<User> call = userApi.updateUserPassword(userId, newPassword, token);
        call.enqueue(new CustomizeCallback<User>(callback));
    }

    public void saveAddress(AddressRequest addressRequest, String token, ResponseCallback callback) {
        Call<AddressRequest> call = userApi.getUserAddress(addressRequest, token);
        call.enqueue(new CustomizeCallback<AddressRequest>(callback));
    }

    public void getUserDetail(Integer userId, String token, ResponseCallback callback) {
        Call<User> call = userApi.getUserDetail(userId, token);
        call.enqueue(new CustomizeCallback<User>(callback));
    }
}