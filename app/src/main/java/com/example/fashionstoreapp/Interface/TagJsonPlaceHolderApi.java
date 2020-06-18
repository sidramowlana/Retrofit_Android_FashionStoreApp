package com.example.fashionstoreapp.Interface;

import com.example.fashionstoreapp.Models.Tag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TagJsonPlaceHolderApi {

    @GET("tags")
    Call<List<Tag>> getAllTags();

    @POST("tags")
    Call<Tag> createTag(@Body Tag tag);
}
