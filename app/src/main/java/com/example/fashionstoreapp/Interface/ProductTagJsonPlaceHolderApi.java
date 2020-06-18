package com.example.fashionstoreapp.Interface;

import com.example.fashionstoreapp.Models.Product;
import com.example.fashionstoreapp.Models.ProductTag;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ProductTagJsonPlaceHolderApi {

    @POST("productstags")
    Call<ProductTag> createProductTag(@Body ProductTag productTag);
}
