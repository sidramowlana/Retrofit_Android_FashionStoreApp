package com.example.fashionstoreapp.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fashionstoreapp.APIService.UserService;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitInterface.ResponseCallBackInterface;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.ActivityForgotPasswordBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.security.NoSuchAlgorithmException;

import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity  implements ResponseCallBackInterface {

    ActivityForgotPasswordBinding binding;
    UserService userService;
    LoginResponse loginResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        userService = new UserService();
        loginResponse = new LoginResponse();

    }

    public void onUpdateNewPassword() throws NoSuchAlgorithmException {
        String newPassword = binding.etNewPassword.getText().toString();
        String confirmPassword = binding.etConfirmPassword.getText().toString();
String userId = loginResponse.getId();
        System.out.println(loginResponse.getUsername());
        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            FancyToast.makeText(getApplicationContext(), "Fields cannot be empty", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
        } if(!newPassword.equals(confirmPassword)){
            FancyToast.makeText(getApplicationContext(), "Password isnt the same", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
        } else{
            SharedPreferenceManager.getSharedPreferenceInstance(this);
            System.out.println(SharedPreferenceManager.getSharedPreferenceInstance(this));
           userService.updatePassword(userId,newPassword,this);
        }
    }

    @Override
    public void onSuccess(Response response) {
        FancyToast.makeText(getApplicationContext(), "New Password is updated", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

    }

    @Override
    public void onError(String errorMessage) {
        FancyToast.makeText(getApplicationContext(), "Failed", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

    }
}
