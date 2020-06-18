package com.example.fashionstoreapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.fashionstoreapp.APIInterface.UserJsonApi;
import com.example.fashionstoreapp.APIService.RetrofitClient;
import com.example.fashionstoreapp.MainActivity;
import com.example.fashionstoreapp.Models.LoginResponse;
import com.example.fashionstoreapp.Models.User;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.ActivityLoginBinding;
import com.google.gson.Gson;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    Intent intent;
    RetrofitClient retrofitClient;
    UserJsonApi userJsonApi;

    private SharedPreferences sharedPreferences;
    public static String USER = "com.example.fashionsapptore.USER";
    public static String LOGGED_USER = "com.example.fashionsapptore.LOGGED_USER";
    public static String EMAIL_KEY = "com.example.fashionsapptore.activities.USERNAME_KEY";
    public static String PASSWORD_KEY = "com.example.fashionsapptore.activities.PASSWORD_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnLogin();
            }
        });
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnRegister();
            }
        });
//        binding.btnForgotPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBtnForgotPassword();
//            }
//        });
        //checking in shared preference for the same user data
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String email = sharedPreferences.getString(EMAIL_KEY, "");
//        String password = sharedPreferences.getString(PASSWORD_KEY, "");
//
//        binding.etEmail.setText(email);
//        binding.etPassword.setText(password);

//        if (sharedPreferences.getBoolean(LOGGED_USER, false)) {
//            loadMain();
//        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPreferenceManager.getSharedPreferenceInstance(getApplicationContext()).isLoggedIn()) {
            intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void loadMain() {
//        intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Will take to the main activity!", Toast.LENGTH_LONG).show();

    }

    private static String token;

    public void onLogin(final String username, final String password) {
        System.out.println(username);
        System.out.println(password);
        System.out.println("kok");
        LoginResponse loginResponse = new LoginResponse(username, password);
        Call<User> call = RetrofitClient.getRetrofitClientInstance().getApi().loginUser(loginResponse);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.body().getToken(), Toast.LENGTH_SHORT).show();
                    token = response.body().getToken();
                } else {
                    Toast.makeText(getApplicationContext(), "Login not correct!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error :(", Toast.LENGTH_SHORT).show();

            }
        });
//        Call<User> call = RetrofitClient.getRetrofitClientInstance().getApi().loginUser(username, password);
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                try {
//                    System.out.println(response.code());
//                    if (!response.isSuccessful()) {
//                        FancyToast.makeText(getApplicationContext(), "responseissucceffull: "+response.message(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//                    }
//
//                    if (response.code() == 200) {
//                        FancyToast.makeText(getApplicationContext(), "Successfully Logged In", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
//
//                        System.out.println("succefully loggedin");
//                        SharedPreferenceManager.getSharedPreferenceInstance(LoginActivity.this).saveUserSharedPref(username, password);
//                        intent = new Intent(getApplicationContext(), MainActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
//                    } else {
//                        System.out.println(response.message());
//                        String s = response.errorBody().string();
//                        System.out.println(s);
//                        s = s.substring(s.indexOf(":") + 1);
//                        s = s.substring(0, s.indexOf("}"));
//                        System.out.println("the s: "+s);
////                        Gson gson;
//                        FancyToast.makeText(getApplicationContext(), s, FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
////                FancyToast.makeText(getApplicationContext(), "Please check your internet connection", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//                System.out.println("The error message is: " + t.getMessage());
//            }
//        });
    }

    public void onBtnLogin() {
        String username = binding.etUsername.getText().toString();
        String password = binding.etPassword.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            FancyToast.makeText(getApplicationContext(), "Fields cannot be empty", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
        } else {
            onLogin(username, password);
        }
    }

    public void onBtnRegister() {
        intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

//    public void onBtnForgotPassword() {
//        intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
//        startActivity(intent);
//    }

}
