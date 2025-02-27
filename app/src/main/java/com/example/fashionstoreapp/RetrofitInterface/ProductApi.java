package com.example.fashionstoreapp.RetrofitInterface;


import com.example.fashionstoreapp.DTO.Responses.MessageResponse;
import com.example.fashionstoreapp.Models.Product;
import com.example.fashionstoreapp.Models.ProductTag;
import com.example.fashionstoreapp.Models.Wishlist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductApi {

    //get all products list
    @GET("api/products/productAll")
    Call<List<Product>> getAllProduct();

    //get a product by id
    @GET("/api/products/product/{productId}")
    Call<Product> getProductById(@Path("productId") Integer productId);

    @PUT("api/products/updateProduct/{productId}")
    Call<Product> onUpdateProductByProductId(@Path("productId") Integer productId, @Body Product product, @Header("Authorization") String token);

    //add a product to wishlist
    @POST("/api/wishlist/add-wishlist/{productId}")
    Call<Product> onAddRemoveWishlistByProductId(@Path("productId") Integer productId, @Header("Authorization") String token);

    //get all wishlist product of a user
    @GET("api/wishlist/wishlistAll")
    Call<List<Wishlist>> getAllUserWishListProduct(@Header("Authorization") String token);

    @GET("api/wishlist/product/{productId}")
    Call<Wishlist> getWishlistProduct(@Path("productId") Integer productId, @Header("Authorization") String token);
    //remove a product from wishlist

    @GET("api/products/category/product-all/{category}")
    Call<List<Product>> getProductByTagName(@Path("category") String category, @Header("Authorization") String token);

    @GET("api/tags/product-tags/{tagId}")
    Call<List<ProductTag>> getProductByTagId(@Path("tagId") Integer tagId, @Header("Authorization") String token);

    @POST("api/products/admin/new-product")
    Call<MessageResponse> addProduct(@Body Product product, @Header("Authorization") String token);
}