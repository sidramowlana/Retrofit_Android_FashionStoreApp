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

import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitAPIService.UserService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.ActivityForgotPasswordBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

public class ForgotPasswordActivity extends AppCompatActivity {

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
            getSupportActionBar().setTitle("Product Inquiry");
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
                onSendEmail();
            }
        });
    }

    public void onSendEmail() {
        String email = activityForgotPasswordBinding.forgotPasswordEmail.getText().toString();

        if (email.equals(null)) {
            FancyToast.makeText(getApplicationContext(), "Email is empty", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
        } else {
            //if the email is sent successfully go to the reset password activity
            startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}