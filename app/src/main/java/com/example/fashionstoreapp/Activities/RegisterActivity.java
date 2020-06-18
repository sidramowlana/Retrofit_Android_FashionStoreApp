package com.example.fashionstoreapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fashionstoreapp.APIInterface.UserJsonApi;
import com.example.fashionstoreapp.APIService.RetrofitClient;
import com.example.fashionstoreapp.Models.User;
import com.example.fashionstoreapp.databinding.ActivityRegisterBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    Intent intent;
    RetrofitClient retrofitClient;
    UserJsonApi userJsonApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
//        userJsonApi = retrofitClient.onConnectApi().create(UserJsonApi.class);
        binding.btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnCreateAccount();
            }
        });
        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnSignIn();
            }
        });
        setContentView(view);
    }

    public void onBtnCreateAccount() {
        String email = binding.etRegisterEmail.getText().toString();
        String username = binding.etRegisterName.getText().toString();
        String address = binding.etRegisterAddress.getText().toString();
        String phone = binding.etRegisterPhone.getText().toString();
        String password = binding.etRegisterPassword.getText().toString();
        try {
            if (email.isEmpty() || username.isEmpty() || address.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                FancyToast.makeText(getApplicationContext(), "Please fill all fields", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                FancyToast.makeText(getApplicationContext(), "Please enter valid email address", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
            } else {
                if (password.length() > 6) {
                    User user = new User(email, username, password, address, phone);
                    onCreateUser(user);
                } else {
                    FancyToast.makeText(getApplicationContext(), "Password should be atleast 6 character long", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void onCreateUser(User user) {
        Call<User> call = RetrofitClient.getRetrofitClientInstance().getApi().createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {
                    System.out.println(response.code());
                    if (!response.isSuccessful()) {
                        FancyToast.makeText(getApplicationContext(), "Please check your internet connection", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    }
                    if (response.code() == 200) {
                        User userResponse = response.body();
                        String m = response.message();
                        System.out.println("luffy: " + userResponse);
                        System.out.println("zorro: " + m);
                        FancyToast.makeText(getApplicationContext(), "Successfully Registered", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    } else {
                        String s = response.errorBody().string();
                        s = s.substring(s.indexOf(":") + 1);
                        s = s.substring(0, s.indexOf("}"));
                        FancyToast.makeText(getApplicationContext(), s, FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                FancyToast.makeText(getApplicationContext(), "Server Down", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                System.out.println("The error message is: " + t.getMessage());
            }
        });
    }

    public void onBtnSignIn() {
        onBackPressed();
    }
}
