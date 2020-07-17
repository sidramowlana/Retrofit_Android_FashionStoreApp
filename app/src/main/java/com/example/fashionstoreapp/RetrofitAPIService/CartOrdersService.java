package com.example.fashionstoreapp.RetrofitAPIService;

import com.example.fashionstoreapp.RetrofitClient.RetrofitClient;
import com.example.fashionstoreapp.RetrofitInterface.OrderApi;

public class CartOrdersService {
    OrderApi orderApi;
    RetrofitClient retrofitClient;

    public CartOrdersService() {
        this.orderApi = retrofitClient.getRetrofitClientInstance().create(OrderApi.class);
    }

}
