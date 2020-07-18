package com.example.fashionstoreapp.RetrofitInterface;

import com.example.fashionstoreapp.DTO.Responses.MessageResponse;
import com.example.fashionstoreapp.Models.RateReview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RateReviewApi {

    @POST("/api/rate/product-rate/{productId}")
    Call<MessageResponse> onAddRateReviewByProductId(@Path("productId") Integer productId, @Body RateReview rateReview, @Header("Authorization") String token);

    @GET("api/rate/all/{productId}")
    Call<List<RateReview>> getAllProductRateByProductId(@Path("productId") Integer productId,@Header("Authorization") String token);

}
