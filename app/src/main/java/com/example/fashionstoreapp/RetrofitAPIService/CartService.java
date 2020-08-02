package com.example.fashionstoreapp.RetrofitAPIService;

import com.example.fashionstoreapp.CallBacks.CustomizeCallback;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.Models.Cart;
import com.example.fashionstoreapp.Models.Product;
import com.example.fashionstoreapp.RetrofitClient.RetrofitClient;
import com.example.fashionstoreapp.RetrofitInterface.CartApi;

import java.util.List;

import retrofit2.Call;

public class CartService {
    CartApi cartApi;
    RetrofitClient retrofitClient;

    public CartService() {
        this.cartApi = retrofitClient.getRetrofitClientInstance().create(CartApi.class);
    }

    public void onAddProductCart(Integer id, Integer quantity, String size, Double total, String token, ResponseCallback callback) {
        Call<Product> cartCall = cartApi.onAddProductCart(id, quantity, size, total, token);
        cartCall.enqueue(new CustomizeCallback<Product>(callback));
    }

    public void onUpdateQuantityProductCart(Integer cartId, int quantity, Double total, String token, ResponseCallback callback) {
        Call<Cart> cartCall = cartApi.onUpdateProduct(token, cartId, quantity, total);
        cartCall.enqueue(new CustomizeCallback<Cart>(callback));
    }

    public void onUpdateQuantityProductAfterPurchase(Integer cartId, int quantity, boolean isPurchased, String token, ResponseCallback callback) {
        Call<Cart> cartCall = cartApi.onUpdateQuantityProductAfterPurchase(token, cartId, quantity, isPurchased);
        cartCall.enqueue(new CustomizeCallback<Cart>(callback));
    }

    public void getAllCartProducts(String token, ResponseCallback callback) {
        Call<List<Cart>> cartCall = cartApi.getAllUserCartProduct(token);
        cartCall.enqueue(new CustomizeCallback<List<Cart>>(callback));
    }

//    public void onDeleteProductCart(Integer productId, String size, String token, ResponseCallback callback) {
//        Call<List<Cart>> cartCall = cartApi.onDeleteProductCart(productId, size, token);
//        cartCall.enqueue(new CustomizeCallback<List<Cart>>(callback));
//    }
    public void onDeleteProductCart(Integer cartId, String token, ResponseCallback callback) {
        System.out.println("ver2: "+cartId);
        Call<List<Cart>> cartCall = cartApi.onDeleteProductCart(cartId, token);
        cartCall.enqueue(new CustomizeCallback<List<Cart>>(callback));
    }
}
