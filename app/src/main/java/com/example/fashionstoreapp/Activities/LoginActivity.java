package com.example.fashionstoreapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fashionstoreapp.APIService.AuthenticationService;
import com.example.fashionstoreapp.DTO.Requests.LoginRequest;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.RetrofitInterface.ResponseCallBackInterface;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.ActivityLoginBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements ResponseCallBackInterface {

    private ActivityLoginBinding binding;
    Intent intent;
    public AuthenticationService authenticationService;

    private SharedPreferenceManager sharedPreferences;
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
        authenticationService = new AuthenticationService();

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
        binding.btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnForgotPassword();
            }
        });

        //checking in shared preference for the same user data
//
//        if (sharedPreferences.isLoggedIn()) {
//            loadMain();
//        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPreferenceManager.getSharedPreferenceInstance(this).isLoggedIn()) {
            System.out.println(!SharedPreferenceManager.getSharedPreferenceInstance(this).isLoggedIn() + " +hertr");
            intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
//            User user = SharedPreferenceManager.getSharedPreferenceInstance(this).getUser();
//            String username = user.getUsername();
//            String password = user.getPassword();
//            System.out.println("usernameluffy: " + username);
//
//            binding.etUsername.setText(username);
////            binding.etPassword.setText(password);
//        }
//    }

    public void loadMain() {
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Will take to the main activity!", Toast.LENGTH_LONG).show();

    }

    public void onBtnLogin() {
        String username = binding.etUsername.getText().toString();
        String password = binding.etPassword.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            FancyToast.makeText(getApplicationContext(), "Fields cannot be empty", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
        } else {
            LoginRequest loginRequest = new LoginRequest(username, password);
            authenticationService.login(loginRequest, this);

        }
    }

    public void onBtnRegister() {
        intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSuccess(Response response) {
        LoginResponse loginResponse = (LoginResponse) response.body();
        if (loginResponse != null) {
            System.out.println("----------------------------------------------------");
            System.out.println("responsebody: "+response.body());
            System.out.println("toke: "+loginResponse.getToken());
            System.out.println("id: "+loginResponse.getId());
            System.out.println("username: "+loginResponse.getUsername());
            System.out.println("email: "+loginResponse.getEmail());
            System.out.println("role: "+loginResponse.getRoles());
            System.out.println("expiretime: "+loginResponse.getTokenExpireTime());
            System.out.println("----------------------------------------------------");

            //save the user in sharedpred
            SharedPreferenceManager.getSharedPreferenceInstance(LoginActivity.this).saveUserSharedPref(loginResponse);

            intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            if (loginResponse.getRoles()== "ROLE_ADMIN") {
                System.out.println("It is admin here");
            } else {
                System.out.println("It is Customer here");
            }
            FancyToast.makeText(getApplicationContext(), "Successfully Logged In", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
        }
    }

    @Override
    public void onError(String errorMessage) {
        System.out.println("errorMessage: " + errorMessage);
        FancyToast.makeText(this, "Invalid Credentials  ", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
    }

    public void onBtnForgotPassword() {
        intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
        startActivity(intent);
    }

}
