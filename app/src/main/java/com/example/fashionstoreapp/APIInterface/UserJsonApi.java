package com.example.fashionstoreapp.APIInterface;

import com.example.fashionstoreapp.Models.LoginResponse;
import com.example.fashionstoreapp.Models.User;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface UserJsonApi {
    @GET("users")
    Call<List<User>> getAllUser();

//    @Headers("Content-Type: application/json")
//    @FormUrlEncoded
//    @POST("login")
//    Call<User> loginUser(@Field("username") String username, @Field("password") String password);

    @Headers("Content-Type: application/json")
//    @FormUrlEncoded
    @POST("login")
    Call<User> loginUser(@Body LoginResponse loginResponse);

    @POST("register")
    Call<User> createUser(@Body User user);


    @PUT("users/{usersId}")
    Call<User> updatePutUser(@Path("usersId") Integer usersId, @Body User user);

}
