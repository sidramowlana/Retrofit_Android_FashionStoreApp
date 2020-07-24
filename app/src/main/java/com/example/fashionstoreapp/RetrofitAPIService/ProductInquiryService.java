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

    //user
    public void onAddProductInquiryByProductId(Integer id, ProductInquiry productInquiry, String token, ResponseCallback callback) {
        Call<List<ProductInquiry>> call = productInquiryApi.onAddProductInquiryByProductId(id, productInquiry, token);
        call.enqueue(new CustomizeCallback<List<ProductInquiry>>(callback));
    }

    public void onGetAllProductInquiryByProductId(Integer id, String token, ResponseCallback callback) {
        Call<List<ProductInquiry>> call = productInquiryApi.onGetAllProductInquiryByProductId(id, token);
        call.enqueue(new CustomizeCallback<List<ProductInquiry>>(callback));
    }

    //admin
    public void onGetAllProductInquiryAnswered(boolean isAnswered,String token, ResponseCallback callback){
        Call<List<ProductInquiry>> call=productInquiryApi.onGetAllProductInquiryAnswered(isAnswered,token);
        call.enqueue(new CustomizeCallback<List<ProductInquiry>>(callback));
    }

    public void onAddAnswerByProductInquiryId(Integer productInquiryId, ProductInquiry productInquiry,String token, ResponseCallback callback){
        Call<MessageResponse> call=productInquiryApi.onAddAnswerByProductInquiryId(productInquiryId,productInquiry,token);
        call.enqueue(new CustomizeCallback<MessageResponse>(callback));
    }
}
