package com.example.fashionstoreapp.Interface;

import com.example.fashionstoreapp.Models.Product;
import com.example.fashionstoreapp.Models.Tag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ProductJsonPlaceHolderApi {
    @GET("products")
    Call<List<Product>> getAllProducts();

    @POST("products")
    Call<Product> createProduct(@Body Product product);
}
