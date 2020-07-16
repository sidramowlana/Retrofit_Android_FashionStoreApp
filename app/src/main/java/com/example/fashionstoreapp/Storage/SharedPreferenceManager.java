package com.example.fashionstoreapp.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.fashionstoreapp.DTO.Requests.AddressRequest;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.google.gson.Gson;

public class SharedPreferenceManager {
    public static String SHARED_PREF_INFO = "SHARED_PREF_INFO";
    public static String SHARED_PREF_ADDRESS_INFO = "SHARED_PREF_ADDRESS_INFO";
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
        editor.putString("token", loginResponse.getToken());
        editor.putString("id", loginResponse.getId());
        editor.putString("username", loginResponse.getUsername());
        editor.putString("email", loginResponse.getEmail());
        editor.putString("roles", loginResponse.getRoles());
        editor.putString("tokenExpireTime", loginResponse.getTokenExpireTime());
        editor.putString(SHARED_PREF_INFO, new Gson().toJson(loginResponse));
        editor.apply();
    }

//    public static AddressRequest getCurrentAddress(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_ADDRESS_INFO, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("")
//        String user = sharedPreferences.getString(PaymentDialogFragment.ADDRESS, "");
//        return new Gson().fromJson(user, AddressRequest.class);
//    }

    public void saveAddressUserSharedPreference(AddressRequest addressRequest,LoginResponse loginResponse)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_ADDRESS_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", loginResponse.getToken());
        editor.putString("userId", loginResponse.getId());
        editor.putString("address", addressRequest.getAddress());
        editor.putString("city", addressRequest.getCity());
        editor.putString("postalCode", addressRequest.getPostalCode());
        editor.putString(SHARED_PREF_INFO, new Gson().toJson(addressRequest));
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_INFO, Context.MODE_PRIVATE);
        if (sharedPreferences.getString("id", "") != "") {
            return true;
        }
        return false;
    }
    public AddressRequest getUserAddress()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_ADDRESS_INFO, Context.MODE_PRIVATE);
        System.out.println(sharedPreferences);
        return new AddressRequest(
                sharedPreferences.getString("address", null),
                sharedPreferences.getString("city", null),
                sharedPreferences.getString("postalCode", null));
    }

    public LoginResponse getUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_INFO, Context.MODE_PRIVATE);
        System.out.println(sharedPreferences);
        return new LoginResponse(
                sharedPreferences.getString("token", null),
        sharedPreferences.getString("id", null),
        sharedPreferences.getString("username", null),
        sharedPreferences.getString("email", null),
        sharedPreferences.getString("roles", null),
        sharedPreferences.getString("tokenExpireTime",null)
        );
    }

    public void clear() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
