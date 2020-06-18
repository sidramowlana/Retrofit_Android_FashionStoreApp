package com.example.fashionstoreapp.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Models.User;
import com.google.gson.Gson;

public class SharedPreferenceManager {
    private final String SHARED_PREF_INFO = "SHARED_PREF_INFO";
    public static SharedPreferenceManager sharedPreferenceManagerIntance;
    private Context context;

    public SharedPreferenceManager(Context context) {
        this.context = context;
    }

    public static synchronized SharedPreferenceManager getSharedPreferenceInstance(Context context) {
        if (sharedPreferenceManagerIntance == null) {
            sharedPreferenceManagerIntance = new SharedPreferenceManager(context);
        }
        return sharedPreferenceManagerIntance;
    }

    public void saveUserSharedPref(LoginResponse loginResponse) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String authenticationPreference = new Gson().toJson(loginResponse);
        editor.putString(SHARED_PREF_INFO,authenticationPreference);
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_INFO, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("userId", -1) != -1;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_INFO, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt("userId", -1),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("username", null),
                sharedPreferences.getString("address", null),
                sharedPreferences.getString("phone", null)
        );
    }

    public void clear() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
