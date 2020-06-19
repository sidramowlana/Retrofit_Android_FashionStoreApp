package com.example.fashionstoreapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fashionstoreapp.RetrofitAPIService.AuthenticationService;
import com.example.fashionstoreapp.Models.User;
import com.example.fashionstoreapp.RetrofitInterface.ResponseCallBackInterface;
import com.example.fashionstoreapp.databinding.ActivityRegisterBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements ResponseCallBackInterface {
    private ActivityRegisterBinding binding;
    Intent intent;
    public AuthenticationService authenticationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        authenticationService = new AuthenticationService();
        binding.btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnRegister();
            }
        });
        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoadLogin();
            }
        });
        setContentView(view);
    }

    public void onBtnRegister() {
        String email = binding.etRegisterEmail.getText().toString();
        String username = binding.etRegisterName.getText().toString();
        String address = binding.etRegisterAddress.getText().toString();
        String phone = binding.etRegisterPhone.getText().toString();
        String password = binding.etRegisterPassword.getText().toString();
        if (email.isEmpty() || username.isEmpty() || address.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            FancyToast.makeText(getApplicationContext(), "Please fill all fields", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            FancyToast.makeText(getApplicationContext(), "Please enter valid email address", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
        } else {
            if (password.length() > 6) {
                User user = new User(email, username, password, address, phone);
                System.out.println("autheservice: " + authenticationService);
                authenticationService.register(user, this);
                onLoadLogin();

            } else {
                FancyToast.makeText(getApplicationContext(), "Password should be atleast 6 character long", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
            }
        }
    }

    public void onLoadLogin() {
//        onBackPressed();
        intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSuccess(Response response) {
//
        FancyToast.makeText(getApplicationContext(), "Successfully Registered", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

    }

    @Override
    public void onError(String errorMessage) {
        FancyToast.makeText(this, errorMessage, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
    }
}
