package com.example.fashionstoreapp.RetrofitInterface;

import com.example.fashionstoreapp.Models.ProductInquiry;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductInquiryApi {

    @POST("api/inquiry/product/{productId}")
    Call <List<ProductInquiry>> onAddProductInquiryByProductId(@Path("productId") Integer productId, @Body ProductInquiry productInquiry, @Header("Authorization") String token);

    @GET("api/inquiry/product-all/{productId}")
    Call<List<ProductInquiry>> onGetAllProductInquiry(@Path("productId") Integer productId, @Header("Authorization") String token);
}