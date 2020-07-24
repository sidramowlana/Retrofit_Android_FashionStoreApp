package com.example.fashionstoreapp.RetrofitAPIService;

import com.example.fashionstoreapp.CallBacks.CustomizeCallback;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.Models.CartOrders;
import com.example.fashionstoreapp.Models.Orders;
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

    public void getAllUserOrdersByStatus(Integer userId, String status, String token, ResponseCallback callback) {
        Call<List<Orders>> call = orderApi.getAllUserOrdersByStatus(userId, status, token);
        call.enqueue(new CustomizeCallback<List<Orders>>(callback));
    }

    public void getAllCartByOrderId(Integer orderId, String token, ResponseCallback callback) {
        Call<List<CartOrders>> call = orderApi.getAllCartByOrderId(orderId, token);
        call.enqueue(new CustomizeCallback<List<CartOrders>>(callback));
    }
    public void getAllPendingOrdersByStatus(String status, String token, ResponseCallback callback) {
        Call<List<Orders>> call = orderApi.getAllPendingOrdersByStatus(status, token);
        call.enqueue(new CustomizeCallback<List<Orders>>(callback));
    }
    public void updateOrderStatus(Integer updateCartOrdersId, CartOrders updateCartOrders,String token,ResponseCallback callback){
        Call<CartOrders> call = orderApi.updateOrderStatus(updateCartOrdersId,updateCartOrders,token);
        call.enqueue(new CustomizeCallback<CartOrders>(callback));
    }
    public void getAllCartOrdersByUserId(Integer userId, String token, ResponseCallback callback){
        Call<List<CartOrders>> call = orderApi.getAllCartOrdersByUserId(userId,token);
        call.enqueue(new CustomizeCallback<List<CartOrders>>(callback));
    }
}
