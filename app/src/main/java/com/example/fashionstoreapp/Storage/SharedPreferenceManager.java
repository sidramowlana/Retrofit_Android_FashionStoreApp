package com.example.fashionstoreapp.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.fashionstoreapp.Models.User;

public class SharedPreferenceManager {
    private static final String SHARED_PREF_NAME = "my_shared_pref";
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

    public void saveUserSharedPref(String username,String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("userId", -1) != -1;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt("userId", -1),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("username", null),
                sharedPreferences.getString("address", null),
                sharedPreferences.getString("phone", null)
        );
    }

    public void clear() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
