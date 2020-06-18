package com.example.fashionstoreapp.RetrofitInterface;

import retrofit2.Response;

public interface ResponseCallBackInterface {
    void onSuccess(Response response);
    void onError(String errorMessage);
}