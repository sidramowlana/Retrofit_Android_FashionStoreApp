package com.example.fashionstoreapp.RetrofitInterface;

import com.example.fashionstoreapp.DTO.Responses.MessageResponse;
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
    Call<List<ProductInquiry>> onAddProductInquiryByProductId(@Path("productId") Integer productId, @Body ProductInquiry productInquiry, @Header("Authorization") String token);

    @GET("api/inquiry/product-all/{productId}")
    Call<List<ProductInquiry>> onGetAllProductInquiryByProductId(@Path("productId") Integer productId, @Header("Authorization") String token);

    @GET("api/inquiry/answered/{isAnswered}")
    Call<List<ProductInquiry>> onGetAllProductInquiryAnswered(@Path("isAnswered") boolean isAnswered, @Header("Authorization") String token);

    @POST("api/inquiry/product-answer/{productInquiryId}")
    Call<MessageResponse> onAddAnswerByProductInquiryId(@Path("productInquiryId") Integer productInquiryId, @Body ProductInquiry productInquiry, @Header("Authorization") String token);

}
