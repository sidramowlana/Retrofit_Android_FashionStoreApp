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
import com.example.fashionstoreapp.DTO.Responses.MessageResponse;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitAPIService.UserService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.ActivityOtpBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Response;

public class otpActivity extends AppCompatActivity implements ResponseCallback {
    ActivityOtpBinding activityOtpBinding;
    LoginResponse loginResponse;
UserService userService;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOtpBinding = activityOtpBinding.inflate(getLayoutInflater());
        View view = activityOtpBinding.getRoot();
        setContentView(view);
        setSupportActionBar(activityOtpBinding.otpPasswordToolbarId);
        loginResponse = new LoginResponse();
        userService = new UserService();
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getApplicationContext()).getUser();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Window window = this.getWindow();
            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // finally change the color
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        activityOtpBinding.validOtpButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Integer otp = Integer.valueOf(String.valueOf(activityOtpBinding.otpNumberEdittext.getText()));
                validateOTP(otp);
            }
        });
        activityOtpBinding.resendOtpButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userService.onSendEmailResetPassword(Integer.valueOf(loginResponse.getId()),null,"Bearer "+loginResponse.getToken(),changeToValidateButton());
            }
        });
    }
    public void validateOTP(Integer otp){
        userService.validateOTP(Integer.valueOf(loginResponse.getId()),otp,"Bearer "+loginResponse.getToken(),this);
    }

    @Override
    public void onSuccess(Response response) {
        MessageResponse messageResponse = (MessageResponse) response.body();
        FancyToast.makeText(getApplicationContext(), messageResponse.getMessageResponse(), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
        activityOtpBinding.validOtpButtonId.setVisibility(View.GONE);
        activityOtpBinding.resendOtpButtonId.setVisibility(View.VISIBLE);
        startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));

    }

    @Override
    public void onError(String errorMessage) {
        FancyToast.makeText(getApplicationContext(), "Please try again later", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
    }
    public ResponseCallback changeToValidateButton(){
        ResponseCallback changeToValidateButton = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                FancyToast.makeText(getApplicationContext(), "OTP is valid", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                activityOtpBinding.validOtpButtonId.setVisibility(View.GONE);
                activityOtpBinding.resendOtpButtonId.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(String errorMessage) {
                FancyToast.makeText(getApplicationContext(), "Please try again later", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

            }
        };
        return changeToValidateButton;
    }
}
