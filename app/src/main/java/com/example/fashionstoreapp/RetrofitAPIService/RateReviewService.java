package com.example.fashionstoreapp.RetrofitAPIService;

import com.example.fashionstoreapp.CallBacks.CustomizeCallback;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.MessageResponse;
import com.example.fashionstoreapp.Models.RateReview;
import com.example.fashionstoreapp.RetrofitClient.RetrofitClient;
import com.example.fashionstoreapp.RetrofitInterface.RateReviewApi;

import retrofit2.Call;

public class RateReviewService {
    RateReviewApi rateReviewApi;
    RetrofitClient retrofitClient;

    public RateReviewService() {
        this.rateReviewApi = retrofitClient.getRetrofitClientInstance().create(RateReviewApi.class);
    }

    public void onAddRateReviewByProductId(Integer id, RateReview rateReview, String token, ResponseCallback callback) {
        Call<MessageResponse> cartCall = rateReviewApi.onAddRateReviewByProductId(id, rateReview, token);
        cartCall.enqueue(new CustomizeCallback<MessageResponse>(callback));
    }
}
