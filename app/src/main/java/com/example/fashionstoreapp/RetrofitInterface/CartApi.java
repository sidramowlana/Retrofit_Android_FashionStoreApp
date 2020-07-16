package com.example.fashionstoreapp.RetrofitInterface;

import com.example.fashionstoreapp.Models.Cart;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CartApi {

    @POST("/api/cart/add-cart/{productId}")
    Call<Cart> onAddProductCart(@Path("productId") Integer productId, @Query("quantity") Integer quantity, @Query("size") String size, @Query("total") Double total, @Header("Authorization") String token);

    @GET("api/cart/cartAll")
    Call<List<Cart>> getAllUserCartProduct(@Header("Authorization") String token);

    //get all cart product of a user

    @GET("api/cart/cartAll/{userId}")
    Call<List<Cart>> getAllUserIsPurchaseCartProducts(@Path("userId")Integer userId,@Header("Authorization") String token, @Query("isPurchased") boolean isPurchased);

    @POST("api/cart/delete-cart/{productId}")
    Call<List<Cart>> onDeleteProductCart(@Path("productId") Integer productId, @Query("size") String size, @Header("Authorization") String token);

    @PUT("api/cart/update-cart/{cartId}")
    Call<Cart> onUpdateProduct(@Header("Authorization") String token, @Path("cartId") Integer cartId, @Query("quantity") Integer quantity, @Query("total") Double total);

    @PUT("api/cart/update-prodcuct/{cartId}")
    Call<Cart> onUpdateQuantityProductAfterPurchase(@Header("Authorization") String token, @Path("cartId") Integer cartId,
                                                    @Query("quantity") Integer quantity, @Query("isPurchased")boolean isPurchased);

}