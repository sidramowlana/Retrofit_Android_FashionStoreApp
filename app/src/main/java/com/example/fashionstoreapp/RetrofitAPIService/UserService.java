package com.example.fashionstoreapp.RetrofitAPIService;

import com.example.fashionstoreapp.CallBacks.CustomizeCallback;
import com.example.fashionstoreapp.Models.User;
import com.example.fashionstoreapp.RetrofitClient.RetrofitClient;
import com.example.fashionstoreapp.RetrofitInterface.ResponseCallBackInterface;
import com.example.fashionstoreapp.RetrofitInterface.UserApi;

import retrofit2.Call;

public class UserService {
    UserApi userApi;
    RetrofitClient retrofitClient;

    public UserService() {
        this.userApi = retrofitClient.getRetrofitClientInstance().create(UserApi.class);
    }

    public void updatePassword(String userId, String newPassword, ResponseCallBackInterface callback) {
        Call<User> userCall = userApi.updateUserPassword(userId,newPassword);
        userCall.enqueue(new CustomizeCallback<User>(callback));
    }
}
