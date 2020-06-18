package com.example.fashionstoreapp.APIService;

import com.example.fashionstoreapp.APIInterface.UserJsonApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static String BASE_URL = "http://192.168.43.239:8080/api/auth/";
//    private static String BASE_URL = "http://169.254.236.94:8080/api/auth/";

    private static RetrofitClient retrofitClientInstance;
    private Retrofit retrofit;

    public RetrofitClient() {
//        Gson gson = new GsonBuilder().serializeNulls()
//                .setLenient()
//                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitClient getRetrofitClientInstance() {
        if (retrofitClientInstance == null) {
            retrofitClientInstance = new RetrofitClient();
        }
        return retrofitClientInstance;
    }

    public UserJsonApi getApi() {
        return retrofit.create(UserJsonApi.class);
    }

    public static OkHttpClient getHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

       return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(1, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
    }
}
