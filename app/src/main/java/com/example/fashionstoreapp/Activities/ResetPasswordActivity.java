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
import com.example.fashionstoreapp.databinding.ActivityResetPasswordBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity implements ResponseCallback {

    ActivityResetPasswordBinding activityResetPasswordBinding;
    LoginResponse loginResponse;
    UserService userService;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityResetPasswordBinding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        View view = activityResetPasswordBinding.getRoot();
        setContentView(view);

        loginResponse = new LoginResponse();
        userService=new UserService();
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
        activityResetPasswordBinding.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = activityResetPasswordBinding.etPassword.getText().toString();
                resetPassword(newPassword);
            }
        });
    }

    public void resetPassword(String newPassword){
        userService.resetPassword(Integer.valueOf(loginResponse.getId()),newPassword,"Bearer "+loginResponse.getToken(),this);
    }
    @Override
    public void onSuccess(Response response) {
        MessageResponse messageResponse = (MessageResponse) response.body();
        FancyToast.makeText(getApplicationContext(), messageResponse.getMessageResponse(), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));

    }

    @Override
    public void onError(String errorMessage) {
        FancyToast.makeText(getApplicationContext(), "Please try again later", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

    }
}
