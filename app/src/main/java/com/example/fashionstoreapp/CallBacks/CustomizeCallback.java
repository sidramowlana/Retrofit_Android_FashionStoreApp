package com.example.fashionstoreapp.CallBacks;

import com.example.fashionstoreapp.DTO.Responses.MessageResponse;
import com.example.fashionstoreapp.RetrofitInterface.ResponseCallBackInterface;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomizeCallback<T> implements Callback<T> {

    private ResponseCallBackInterface responseCallBackInterface;

    public CustomizeCallback(ResponseCallBackInterface responseCallBackInterface) {
        this.responseCallBackInterface = responseCallBackInterface;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            responseCallBackInterface.onSuccess(response);
        } else {
            String error = new Gson().fromJson(response.errorBody().charStream(), MessageResponse.class).getMessageResponse();
            responseCallBackInterface.onError(error);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        System.out.println("The failure message: " + t.getMessage());
        responseCallBackInterface.onError(t.getMessage());
    }
}