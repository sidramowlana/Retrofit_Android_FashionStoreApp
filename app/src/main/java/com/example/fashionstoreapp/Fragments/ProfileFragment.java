package com.example.fashionstoreapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.FragmentProfileBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    FragmentProfileBinding fragmentProfileBinding;
    private LoginResponse loginResponse;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentProfileBinding = FragmentProfileBinding.inflate(getLayoutInflater());
        View view = fragmentProfileBinding.getRoot();
        getActivity().setTitle("My Profile");
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).getUser();

//        fragmentProfileBinding.etProfileName.setText(loginResponse.getUsername());
//        fragmentProfileBinding.etProfileEmail.setText(loginResponse.getEmail());
//        fragmentProfileBinding.etProfileAddress.setText(loginResponse.get.getAddress());
//        fragmentProfileBinding.etProfilePhone.setText(user.getPhone());
//        fragmentProfileBinding.etProfilePassword.setText(loginResponse.getPassword());
//        fragmentProfileBinding.btnUpdateAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onUpdateProfile();
//            }
//        });

        return view;
    }

//    public void onUpdateProfile() {
//        User user = StoresUser.getCurrentLoggedUser(getActivity());
//        String userName = fragmentProfileBinding.etProfileName.getText().toString();
//        String userEmail = fragmentProfileBinding.etProfileEmail.getText().toString();
//        String userAddress = fragmentProfileBinding.etProfileAddress.getText().toString();
//        String userPhone = fragmentProfileBinding.etProfilePhone.getText().toString();
//        String userPassword = fragmentProfileBinding.etProfilePassword.getText().toString();
//        try {
//            //check if any field is empty
//            if (userEmail.isEmpty() || userName.isEmpty() || userAddress.isEmpty() || userPhone.isEmpty() || userPassword.isEmpty()) {
//                Toast.makeText(getContext(), "Please fill all fields.", Toast.LENGTH_LONG).show();
//            } else {
//                    user.setName(userName);
//                    user.setEmail(userEmail);
//                    user.setAddress(userAddress);
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
//            }
//
//        } catch (NoSuchAlgorithmException e) {
//            System.out.println(e.getMessage());
//        }
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
