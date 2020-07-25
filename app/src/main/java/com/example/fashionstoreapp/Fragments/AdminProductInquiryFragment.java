package com.example.fashionstoreapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.fashionstoreapp.Adapters.TabPageAdapter;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.RetrofitAPIService.ProductInquiryService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.FragmentAdminProductInquiryBinding;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminProductInquiryFragment extends Fragment {

    private FragmentAdminProductInquiryBinding fragmentAdminProductInquiryBinding;
    private ProductInquiryService productInquiryService;
    private LoginResponse loginResponse;
    private ResponseCallback updateOrderResponseCallBack;


    public AdminProductInquiryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentAdminProductInquiryBinding = FragmentAdminProductInquiryBinding.inflate(getLayoutInflater());
        View view = fragmentAdminProductInquiryBinding.getRoot();
        getActivity().setTitle("Inquiries");
        ViewPager viewPager = fragmentAdminProductInquiryBinding.adminProductInquiryViewpagerId;
        setViewPager(viewPager);
        TabLayout tabLayout = fragmentAdminProductInquiryBinding.adminProductInquiryTabId;
        tabLayout.setupWithViewPager(viewPager);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).getUser();
        return view;
    }
    public void setViewPager(ViewPager viewPager) {
        TabPageAdapter tabPageAdapter = new TabPageAdapter(this.getChildFragmentManager());
        tabPageAdapter.onAddFragment(new NewInquiriesFragment(), "New");
        tabPageAdapter.onAddFragment(new AnsweredInquiriesFragment(), "Answered");
        viewPager.setAdapter(tabPageAdapter);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
