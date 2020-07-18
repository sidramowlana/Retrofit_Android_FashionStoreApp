package com.example.fashionstoreapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Models.User;
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

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentProfileBinding = FragmentProfileBinding.inflate(getLayoutInflater());
        View view = fragmentProfileBinding.getRoot();
        getActivity().setTitle("My Profile");
        UserService userService = new UserService();
        LoginResponse loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).getUser();
        userService.getUserDetail(Integer.parseInt(loginResponse.getId()), "Bearer " + loginResponse.getToken(), this);

        fragmentProfileBinding.etProfileName.setText(loginResponse.getUsername());
        fragmentProfileBinding.etProfileEmail.setText(loginResponse.getEmail());
//        fragmentProfileBinding.etProfileAddress.setText(loginResponse.get.getAddress());
//        fragmentProfileBinding.etProfilePassword.setText(loginResponse.getPassword());
        fragmentProfileBinding.btnUpdateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateProfile();
            }
        });

        return view;
    }

    public void onUpdateProfile() {
//        loginResponse =SharedPreferenceManager.getSharedPreferenceInstance(getContext()).saveUserSharedPref().getCurrentLoggedUser(getActivity());
        String userName = fragmentProfileBinding.etProfileName.getText().toString();
        String userEmail = fragmentProfileBinding.etProfileEmail.getText().toString();
        String userPhone = fragmentProfileBinding.etProfilePhone.getText().toString();
        //check if any field is empty
        if (userEmail.isEmpty() || userName.isEmpty() || userPhone.isEmpty()) {
            FancyToast.makeText(getContext(), "Please fill all fields.", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
        } else {
            //update the user
            //get the user for the shared preference
//                    user.setName(userName);
//                    user.setEmail(userEmail);
//                    user.setPhone(userPhone);
//
//                    if (!userPassword.equals(user.getPassword())) {
//                        user.setPassword(MD5.passwordEncrypt(userPassword));
//                    }
//                    user.save();
//
//                    NavigationView navigationView = getActivity().findViewById(R.id.navigationView);
//                    View headerView = navigationView.getHeaderView(0);
//
//                    // NavigationView Header
//                    TextView nav_profile_name = (TextView) headerView.findViewById(R.id.tvUserName);
//                    nav_profile_name.setText(user.getName());
//
//                    TextView nav_profile_email = (TextView) headerView.findViewById(R.id.tvUserEmail);
//                    nav_profile_email.setText(user.getEmail());
//                    Toast.makeText(getContext(), "Updated Successfully.", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSuccess(Response response) {
        User user = (User) response.body();
        fragmentProfileBinding.etProfilePhone.setText(user.getPhone());
    }

    @Override
    public void onError(String errorMessage) {

    }
}
