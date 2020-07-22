package com.example.fashionstoreapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.fashionstoreapp.Activities.LoginActivity;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Models.User;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitAPIService.UserService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.FragmentProfileBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ResponseCallback {

    private FragmentProfileBinding fragmentProfileBinding;
    private UserService userService;
    User user;
    LoginResponse loginResponse;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentProfileBinding = FragmentProfileBinding.inflate(getLayoutInflater());
        View view = fragmentProfileBinding.getRoot();
        getActivity().setTitle("My Profile");
        userService = new UserService();
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).getUser();
        userService.getUserDetail(Integer.parseInt(loginResponse.getId()), "Bearer " + loginResponse.getToken(), this);

        fragmentProfileBinding.btnEditAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowResetDialog().show(getChildFragmentManager(), "Reset");;
            }
        });

        fragmentProfileBinding.btnLogoutPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferenceManager.getSharedPreferenceInstance(getContext()).clear();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSuccess(Response response) {
        user = (User) response.body();
        fragmentProfileBinding.etProfileName.setText(loginResponse.getUsername());
        fragmentProfileBinding.etProfileEmail.setText(loginResponse.getEmail());
        fragmentProfileBinding.etProfilePhone.setText(user.getPhone());
    }

    @Override
    public void onError(String errorMessage) {
        FancyToast.makeText(getContext(), "System down", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
    }

    public void editableChanges() {
        fragmentProfileBinding.etProfileEmail.setBackgroundResource(R.drawable.border_change);
        fragmentProfileBinding.etProfileName.setBackgroundResource(R.drawable.border_change);
        fragmentProfileBinding.etProfilePhone.setBackgroundResource(R.drawable.border_change);
        fragmentProfileBinding.etProfileEmail.setEnabled(true);
        fragmentProfileBinding.etProfileName.setEnabled(true);
        fragmentProfileBinding.etProfilePhone.setEnabled(true);
        fragmentProfileBinding.btnEditAccount.setVisibility(View.GONE);

    }

    public void afterEditableChanges() {
        fragmentProfileBinding.etProfileEmail.setBackgroundResource(R.drawable.border);
        fragmentProfileBinding.etProfileName.setBackgroundResource(R.drawable.border);
        fragmentProfileBinding.etProfilePhone.setBackgroundResource(R.drawable.border);
        fragmentProfileBinding.etProfileEmail.setEnabled(false);
        fragmentProfileBinding.etProfileName.setEnabled(false);
        fragmentProfileBinding.etProfilePhone.setEnabled(false);
        fragmentProfileBinding.btnEditAccount.setVisibility(View.VISIBLE);
    }

    public PasswordResetDialogFragment onShowResetDialog() {
        PasswordResetDialogFragment passwordResetDialogFragment = new PasswordResetDialogFragment();
        return passwordResetDialogFragment;

    }
}

