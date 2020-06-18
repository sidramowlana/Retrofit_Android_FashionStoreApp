package com.example.fashionstoreapp.APIService;

import com.example.fashionstoreapp.CallBacks.CustomizeCallback;
import com.example.fashionstoreapp.Models.Product;
import com.example.fashionstoreapp.RetrofitClient.RetrofitClient;
import com.example.fashionstoreapp.RetrofitInterface.ProductApi;
import com.example.fashionstoreapp.RetrofitInterface.ResponseCallBackInterface;

import java.util.List;

import retrofit2.Call;

public class ProductService {
    ProductApi productApi;
    RetrofitClient retrofitClient;

    public ProductService() {
        this.productApi = retrofitClient.getRetrofitClientInstance().create(ProductApi.class);
    }

    public void getAllProducts(ResponseCallBackInterface callback) {
        Call<List<Product>> productCall = productApi.getAllProduct();
        productCall.enqueue(new CustomizeCallback<List<Product>>(callback));
    }
}
