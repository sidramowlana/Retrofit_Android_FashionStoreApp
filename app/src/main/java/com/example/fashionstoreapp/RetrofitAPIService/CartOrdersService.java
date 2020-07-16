package com.example.fashionstoreapp.RetrofitAPIService;

import com.example.fashionstoreapp.CallBacks.CustomizeCallback;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.Models.Cart;
import com.example.fashionstoreapp.RetrofitClient.RetrofitClient;
import com.example.fashionstoreapp.RetrofitInterface.OrderApi;

import java.util.List;

import retrofit2.Call;

public class CartOrdersService {
    OrderApi orderApi;
    RetrofitClient retrofitClient;

    public CartOrdersService() {
        this.orderApi = retrofitClient.getRetrofitClientInstance().create(OrderApi.class);
    }

    public void getOrderPending(Integer userId, String status, String token, ResponseCallback callback){
        Call<List<Cart>> cartOrderPendingCall = orderApi.getOrderPending(userId, status, token);
        cartOrderPendingCall.enqueue(new CustomizeCallback<List<Cart>>(callback));
    }
}
