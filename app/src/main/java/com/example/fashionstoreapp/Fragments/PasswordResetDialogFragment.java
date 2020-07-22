package com.example.fashionstoreapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.RetrofitAPIService.UserService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.FragmentPasswordResetDialogBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordResetDialogFragment extends DialogFragment {
    private FragmentPasswordResetDialogBinding fragmentPasswordResetDialogBinding;
    private LoginResponse loginResponse;
    private UserService userService;

    public PasswordResetDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentPasswordResetDialogBinding = FragmentPasswordResetDialogBinding.inflate(getLayoutInflater());
        View view = fragmentPasswordResetDialogBinding.getRoot();
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).getUser();
        userService = new UserService();
        System.out.println("checking rootss");

//        final String newPassword = fragmentPasswordResetDialogBinding.resetPasswordDialogNewEditViewId.getText().toString();
//        final String confirmPassword = fragmentPasswordResetDialogBinding.resetPasswordDialogConfirmEditViewId.getText().toString();
//
//        fragmentPasswordResetDialogBinding.resetPasswordResetButtonUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (newPassword.length() > 6) {
//                    if(confirmPassword.equals(newPassword)) {
//                        userService.updatePassword(Integer.valueOf(loginResponse.getId()),newPassword,"Bearer "+loginResponse.getToken(),updateResetResponseCallback());
//                    }
//                    else{
//                        FancyToast.makeText(getContext(), "Password mismatch", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
//                    }
//                } else {
//                    FancyToast.makeText(getContext(), "Password should be atleast 6 character long", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
//                }
//            }
//        });

//        fragmentPasswordResetDialogBinding.resetPasswordResetButtonCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getDialog().dismiss();
//            }
//        });
        return view;
    }

    public ResponseCallback updateResetResponseCallback(){
        ResponseCallback updateResetResponseCallback = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                FancyToast.makeText(getContext(), "Successfully updated", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
            }

            @Override
            public void onError(String errorMessage) {
                FancyToast.makeText(getContext(), "Couldnt update your password", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            }
        };
        return updateResetResponseCallback;
    }
}
