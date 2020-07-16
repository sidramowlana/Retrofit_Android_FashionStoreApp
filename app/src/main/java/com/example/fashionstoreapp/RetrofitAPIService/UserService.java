package com.example.fashionstoreapp.RetrofitAPIService;

import com.example.fashionstoreapp.CallBacks.CustomizeCallback;
import com.example.fashionstoreapp.DTO.Requests.AddressRequest;
import com.example.fashionstoreapp.Models.User;
import com.example.fashionstoreapp.RetrofitClient.RetrofitClient;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.RetrofitInterface.UserApi;

import retrofit2.Call;

public class UserService {
    UserApi userApi;
    RetrofitClient retrofitClient;

    public UserService() {
        this.userApi = retrofitClient.getRetrofitClientInstance().create(UserApi.class);
    }

    public void updatePassword(String userId, String newPassword, ResponseCallback callback) {
        Call<User> userCall = userApi.updateUserPassword(userId, newPassword);
        userCall.enqueue(new CustomizeCallback<User>(callback));
    }

    public void saveAddress(AddressRequest addressRequest, String token, ResponseCallback callback) {
        Call<AddressRequest> addressCall = userApi.getUserAddress(addressRequest, token);
        addressCall.enqueue(new CustomizeCallback<AddressRequest>(callback));
    }
    public void getUserDetail(Integer userId, String token, ResponseCallback callback)
    {
     Call<User> userCall = userApi.getUserDetail(userId,token);
     userCall.enqueue(new CustomizeCallback<User>(callback));
    }
}
