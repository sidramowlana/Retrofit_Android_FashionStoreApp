package com.example.fashionstoreapp.RetrofitInterface;

import com.example.fashionstoreapp.Models.Cart;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CartApi {

    @POST("/api/cart/add-cart/{productId}")
    Call<Cart> onAddProductCart(@Path("productId") Integer productId, @Query("quantity")Integer quantity,@Query("size")String size, @Query("total") Double total,@Header("Authorization") String token);

    @GET("api/cart/cartAll")
    Call<List<Cart>>getAllUserCartProduct(@Header("Authorization")String token);
    //get all cart product of a user

    @POST("api/cart/delete-cart/{productId}")
    Call <List<Cart>>onDeleteProductCart(@Path("productId")Integer productId,@Query("size") String size,@Header("Authorization")String token);
}
