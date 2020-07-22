package com.example.fashionstoreapp.RetrofitInterface;

import com.example.fashionstoreapp.Models.Tag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface TagApi {

    @GET("api/tags/all")
    Call<List<Tag>> getAllTags(@Header("Authorization") String token);

}
