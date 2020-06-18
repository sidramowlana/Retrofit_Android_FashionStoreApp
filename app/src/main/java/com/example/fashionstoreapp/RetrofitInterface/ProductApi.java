package com.example.fashionstoreapp.RetrofitInterface;


import com.example.fashionstoreapp.Models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductApi {

    @GET("api/products/productAll")
    Call<List<Product>> getAllProduct();

    @GET("products/{productId}")
    Call<Product> getProductById(@Path("productId") Integer productId);



}
