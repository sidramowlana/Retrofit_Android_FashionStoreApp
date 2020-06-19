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
        if (!response.isSuccessful() && response.errorBody()!=null) {
            String error = new Gson().fromJson(response.errorBody().charStream(), MessageResponse.class).getMessageResponse();
            System.out.println("Customize callback not successfull "+error);
            responseCallBackInterface.onError(error);
        } else if(response.body()!=null) {
            responseCallBackInterface.onSuccess(response);
            System.out.println("Customize callback successfull "+response.body());
           }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        System.out.println("Customize callback failure message: " + t.getMessage());
        responseCallBackInterface.onError("Network error please try again later!" +t.getMessage());
    }
}