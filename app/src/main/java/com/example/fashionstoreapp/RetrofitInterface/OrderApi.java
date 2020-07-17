package com.example.fashionstoreapp.RetrofitInterface;

import com.example.fashionstoreapp.DTO.Responses.MessageResponse;
import com.example.fashionstoreapp.Models.CartOrders;
import com.example.fashionstoreapp.Models.Orders;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderApi {

    @POST("api/orders/cart/add-order")
    Call<MessageResponse> addCartOrders(@Body CartOrders cartOrders, @Header("Authorization") String token);

    @POST("api/orders/orders/add-order")
    Call<Orders> addOrder(@Body Orders orders, @Header("Authorization") String token);

    @GET("api/orders/status/{orderStatus}/user/{userId}")
    Call<List<Orders>> getAllUserOrdersByStatus(@Path("userId") Integer userId, @Path("orderStatus") String orderStatus, @Header("Authorization") String token);

    @GET("api/orders/cart/{ordersId}")
    Call<List<CartOrders>> getAllCartByOrderId(@Path("ordersId") Integer ordersId, @Header("Authorization") String token);

    @PUT("api/orders/order-update/{cartOrdersId}")
    Call<CartOrders> updateOrderStatus(@Path("cartOrdersId") Integer cartOrdersId,@Body CartOrders cartOrders, @Header("Authorization") String token);

    @GET("api/orders/cart-orders/{userId}")
    Call <List<CartOrders>> getAllCartOrdersByUserId(@Path("userId") Integer userId, @Header("Authorization") String token);
}
