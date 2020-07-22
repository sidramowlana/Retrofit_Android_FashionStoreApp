package com.example.fashionstoreapp.RetrofitInterface;

import com.example.fashionstoreapp.DTO.Requests.AddressRequest;
import com.example.fashionstoreapp.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {
    @GET("users")
    Call<List<User>> getAllUser();

    @PUT("api/users/reset-password/{userId}")
    Call<User> updateUserPassword(@Path("userId") Integer userId, @Body String newPassword, @Header("Authorization") String token);

    @POST("/api/users/user/add-address")
    Call<AddressRequest> getUserAddress(@Body AddressRequest addRequest, @Header("Authorization") String token);

    @GET("api/users/user/{userId}")
    Call<User> getUserDetail(@Path("userId") Integer userId, @Header("Authorization") String token);

}