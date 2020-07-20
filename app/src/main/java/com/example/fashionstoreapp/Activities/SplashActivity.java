package com.example.fashionstoreapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1000;
    private SharedPreferences sharedPreferences;
    LoginResponse loginResponse;
boolean isLogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);




        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getApplicationContext()).getUser();
        isLogged=SharedPreferenceManager.getSharedPreferenceInstance(getApplicationContext()).isLoggedIn();
        if (isLogged==true) {
            System.out.println("user is logged in");
            if (loginResponse.getRoles().equals("ROLE_ADMIN")) {
                System.out.println("It is admin here");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this, AdminMainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, SPLASH_TIME_OUT);

            } else if (loginResponse.getRoles().equals("ROLE_USER")) {
                System.out.println("It is Customer here");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, SPLASH_TIME_OUT);
            } else {
                System.out.println("no user still loggedin");
            }
            if (loginResponse != null) {
                System.out.println("loooooopo: " + loginResponse.getId());
                System.out.println("loooooopo: " + loginResponse.getRoles());
                System.out.println("loooooopo: " + loginResponse.getToken());
                System.out.println("loooooopo: " + loginResponse.getEmail());
                System.out.println("loooooopo: " + loginResponse.getUsername());
//
                FancyToast.makeText(getApplicationContext(), "Successfully Logged In", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
            }
        }
        else if (isLogged==false){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }
}