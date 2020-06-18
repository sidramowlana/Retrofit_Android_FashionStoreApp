package com.example.fashionstoreapp.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.fashionstoreapp.Activities.LoginActivity;
import com.google.gson.Gson;

public class StoresUser {

    public static User getCurrentLoggedUser(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String user = sharedPreferences.getString(LoginActivity.USER, "");
        return new Gson().fromJson(user, User.class);
    }
}
