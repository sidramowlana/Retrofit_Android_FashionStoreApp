package com.example.fashionstoreapp.APIInterface;


import com.example.fashionstoreapp.Models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductJsonApi {

    @GET("products")
    Call<List<Product>> getAllProduct();

    @GET("products/{productId}")
    Call<Product> getProductById(@Path("productId") Integer productId);



}
