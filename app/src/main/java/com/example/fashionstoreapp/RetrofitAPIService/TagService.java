package com.example.fashionstoreapp.RetrofitAPIService;

import com.example.fashionstoreapp.CallBacks.CustomizeCallback;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.Models.Tag;
import com.example.fashionstoreapp.RetrofitClient.RetrofitClient;
import com.example.fashionstoreapp.RetrofitInterface.TagApi;

import java.util.List;

import retrofit2.Call;

public class TagService {
    TagApi tagApi;
    RetrofitClient retrofitClient;

    public TagService() {
        this.tagApi = retrofitClient.getRetrofitClientInstance().create(TagApi.class);
    }

    public void getAllTags(String token,ResponseCallback callback) {
        Call<List<Tag>> call = tagApi.getAllTags(token);
        call.enqueue(new CustomizeCallback<List<Tag>>(callback));
    }
}