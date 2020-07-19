package com.example.fashionstoreapp.RetrofitAPIService;

import com.example.fashionstoreapp.CallBacks.CustomizeCallback;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.MessageResponse;
import com.example.fashionstoreapp.Models.ProductInquiry;
import com.example.fashionstoreapp.RetrofitClient.RetrofitClient;
import com.example.fashionstoreapp.RetrofitInterface.ProductInquiryApi;

import java.util.List;

import retrofit2.Call;

public class ProductInquiryService {
    ProductInquiryApi productInquiryApi;
    RetrofitClient retrofitClient;

    public ProductInquiryService() {
        this.productInquiryApi = retrofitClient.getRetrofitClientInstance().create(ProductInquiryApi.class);
    }

    public void onAddProductInquiryByProductId(Integer id, ProductInquiry productInquiry, String token, ResponseCallback callback) {
        Call<List<ProductInquiry>> call = productInquiryApi.onAddProductInquiryByProductId(id, productInquiry, token);
        call.enqueue(new CustomizeCallback<List<ProductInquiry>>(callback));
    }

    public void onGetAllProductInquiry(Integer id, String token, ResponseCallback callback) {
        Call<List<ProductInquiry>> call = productInquiryApi.onGetAllProductInquiry(id, token);
        call.enqueue(new CustomizeCallback<List<ProductInquiry>>(callback));
    }
}
