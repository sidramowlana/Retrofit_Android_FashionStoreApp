package com.example.fashionstoreapp.RetrofitAPIService;

import com.example.fashionstoreapp.CallBacks.CustomizeCallback;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.MessageResponse;
import com.example.fashionstoreapp.Models.Orders;
import com.example.fashionstoreapp.RetrofitClient.RetrofitClient;
import com.example.fashionstoreapp.RetrofitInterface.OrderApi;

import java.util.List;

import retrofit2.Call;

public class OrderService {
    OrderApi orderApi;
    RetrofitClient retrofitClient;

    public OrderService() {
        this.orderApi = retrofitClient.getRetrofitClientInstance().create(OrderApi.class);
    }
    public void saveCartOrders(Orders cartOrders, String token, ResponseCallback callback){
        Call<MessageResponse> cartOrdersCall = orderApi.saveCartOrders(cartOrders,token);
        cartOrdersCall.enqueue(new CustomizeCallback<MessageResponse>(callback));
    }
//    public void saveCartOrders(CartOrders cartOrders,String token, ResponseCallback callback){
//        Call<MessageResponse> cartOrdersCall = orderApi.saveCartOrders(cartOrders,token);
//        cartOrdersCall.enqueue(new CustomizeCallback<MessageResponse>(callback));
//    }
    public void getAllUserOrders(Integer userId, String status, String token, ResponseCallback callback) {
        Call<List<Orders>> ordersCall = orderApi.getAllUserOrders(userId, status, token);
        ordersCall.enqueue(new CustomizeCallback<List<Orders>>(callback));
    }

    public void addUserOrders(Orders orders, Integer userId, String token, ResponseCallback callback) {
        Call<MessageResponse> ordersCall = orderApi.addUserOrders(orders, userId, token);
        ordersCall.enqueue(new CustomizeCallback<MessageResponse>(callback));
    }

}
