package com.example.fashionstoreapp.RetrofitInterface;

import com.example.fashionstoreapp.DTO.Responses.MessageResponse;
import com.example.fashionstoreapp.Models.Tag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TagApi {

    @GET("api/tags/all")
    Call<List<Tag>> getAllTags(@Header("Authorization") String token);

    @POST("api/tags/admin/createTag")
    Call<MessageResponse> saveCatergory(@Body Tag tag, @Header("Authorization") String token);
}
