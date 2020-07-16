package com.example.fashionstoreapp.RetrofitInterface;

import com.example.fashionstoreapp.DTO.Responses.MessageResponse;
import com.example.fashionstoreapp.Models.Cart;
import com.example.fashionstoreapp.Models.Orders;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrderApi {

    //    @Headers({"Accept: application/json"})
    @Headers({"Accept:application/json", "Content-Type:application/json;"})
    @POST("api/orders/add-order/{userId}")
    Call<MessageResponse> addUserOrders(@Body Orders orders, @Path("userId") Integer userId, @Header("Authorization") String token);

//    @POST("api/orders/add-order")
//    Call<MessageResponse> saveCartOrders(@Body CartOrders cartOrders, @Header("Authorization") String token);
    @POST("api/orders/add-order")
    Call<MessageResponse> saveCartOrders(@Body Orders cartOrders, @Header("Authorization") String token);

    @GET("api/orders/status/{orderStatus}/user/{userId}")
    Call<List<Orders>> getAllUserOrders(@Path("userId") Integer userId,@Path("orderStatus") String orderStatus, @Header("Authorization") String token);

    @GET("api/orders/pending/{userId}/")
    Call<List<Cart>> getOrderPending(@Path("userId")Integer userId,
                                     @Query("status") String status,
                                     @Header("Authorization") String token);
}
