package com.example.fashionstoreapp.RetrofitAPIService;

import com.example.fashionstoreapp.CallBacks.CustomizeCallback;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.MessageResponse;
import com.example.fashionstoreapp.Models.CartOrders;
import com.example.fashionstoreapp.Models.Orders;
import com.example.fashionstoreapp.RetrofitClient.RetrofitClient;
import com.example.fashionstoreapp.RetrofitInterface.OrderApi;

import retrofit2.Call;

public class OrderService {
    OrderApi orderApi;
    RetrofitClient retrofitClient;

    public OrderService() {
        this.orderApi = retrofitClient.getRetrofitClientInstance().create(OrderApi.class);
    }

     public void addCartOrders(CartOrders cartOrders, String token, ResponseCallback callback) {
        Call<MessageResponse> call = orderApi.addCartOrders(cartOrders, token);
         call.enqueue(new CustomizeCallback<MessageResponse>(callback));
    }

    public void addOrder(Orders orders, String token, ResponseCallback callback) {
        Call<Orders> call = orderApi.addOrder(orders, token);
        call.enqueue(new CustomizeCallback<Orders>(callback));
    }

}
