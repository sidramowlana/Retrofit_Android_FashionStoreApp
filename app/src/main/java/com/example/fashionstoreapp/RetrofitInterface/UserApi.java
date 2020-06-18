package com.example.fashionstoreapp.RetrofitInterface;

import com.example.fashionstoreapp.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {
    @GET("users")
    Call<List<User>> getAllUser();

    @PUT("api/users/{userId}")
    Call<User> updateUserPassword(@Path("userId") String userId, @Body String newPassword);

}
