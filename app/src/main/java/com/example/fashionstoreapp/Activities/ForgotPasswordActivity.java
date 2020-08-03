package com.example.fashionstoreapp.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitAPIService.UserService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.ActivityForgotPasswordBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements ResponseCallback {

    ActivityForgotPasswordBinding activityForgotPasswordBinding;
    UserService userService;
    LoginResponse loginResponse;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityForgotPasswordBinding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        View view = activityForgotPasswordBinding.getRoot();
        setContentView(view);
        setSupportActionBar(activityForgotPasswordBinding.forgotPasswordToolbarId);

        userService = new UserService();
        loginResponse = new LoginResponse();
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getApplicationContext()).getUser();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Change Password");
            Window window = this.getWindow();
            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // finally change the color
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }


        activityForgotPasswordBinding.sendEmailButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = activityForgotPasswordBinding.forgotPasswordEmail.getText().toString();
                System.out.println("hooping: " + email);
                if (email.isEmpty()) {
                    FancyToast.makeText(getApplicationContext(), "Email is empty", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                } else {
                    onSendEmail(email);
                }
            }
        });

    }

    public void onSendEmail(String email) {
        userService.onSendEmailResetPassword(Integer.valueOf(loginResponse.getId()), email, "Bearer " + loginResponse.getToken(), this);
//        activityForgotPasswordBinding.forgotPasswordEmail.setText();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onSuccess(Response response) {
        System.out.println("working");
        FancyToast.makeText(getApplicationContext(), "An email with the otp has been sent you", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
        System.out.println(response.body());
        if (response != null) {
            startActivity(new Intent(getApplicationContext(), otpActivity.class));
        }
    }

    @Override
    public void onError(String errorMessage) {
        FancyToast.makeText(getApplicationContext(), "Please try again later", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

    }
}