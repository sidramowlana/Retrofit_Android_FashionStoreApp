package com.example.fashionstoreapp.RetrofitAPIService;

import com.example.fashionstoreapp.CallBacks.CustomizeCallback;
import com.example.fashionstoreapp.Models.Product;
import com.example.fashionstoreapp.Models.Wishlist;
import com.example.fashionstoreapp.RetrofitClient.RetrofitClient;
import com.example.fashionstoreapp.RetrofitInterface.ProductApi;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;

import java.util.List;

import retrofit2.Call;

public class ProductService {
    ProductApi productApi;
    RetrofitClient retrofitClient;

    public ProductService() {
        this.productApi = retrofitClient.getRetrofitClientInstance().create(ProductApi.class);
    }

    public void getAllProducts(ResponseCallback callback) {
        Call<List<Product>> call = productApi.getAllProduct();
        call.enqueue(new CustomizeCallback<List<Product>>(callback));
    }

    public void getProduct(Integer id, ResponseCallback callback) {
        Call<Product> call = productApi.getProductById(id);
        call.enqueue(new CustomizeCallback<Product>(callback));
    }

    public void onUpdateProductByProductId(Integer productId, Product product, String token, ResponseCallback callback) {
        Call<Product> call = productApi.onUpdateProductByProductId(productId, product, token);
        call.enqueue(new CustomizeCallback<Product>(callback));
    }

    public void onAddRemoveProductFavourite(Integer id, String token, ResponseCallback callback) {
        Call<Wishlist> call = productApi.onAddRemoveWishlistByProductId(id, token);
        call.enqueue(new CustomizeCallback<Wishlist>(callback));
    }


    public void getAllUserWishListProduct(String token, ResponseCallback callback) {
        Call<List<Wishlist>> wishlistCall = productApi.getAllUserWishListProduct(token);
        wishlistCall.enqueue(new CustomizeCallback<List<Wishlist>>(callback));
    }

    public void getAWishlistProduct(Integer productId, String token, ResponseCallback callback) {
        Call<Wishlist> call = productApi.getWishlistProduct(productId, token);
        call.enqueue(new CustomizeCallback<Wishlist>(callback));
    }

}
