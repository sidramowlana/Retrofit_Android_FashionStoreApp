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
import com.example.fashionstoreapp.RetrofitAPIService.CartOrdersService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.FragmentAddProductCategoryBinding;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductCategoryFragment extends Fragment {

    private FragmentAddProductCategoryBinding fragmentAddProductCategoryBinding;
    private CartOrdersService cartOrdersService;
    private LoginResponse loginResponse;
    private ResponseCallback updateOrderResponseCallBack;

    public AddProductCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentAddProductCategoryBinding = FragmentAddProductCategoryBinding.inflate(getLayoutInflater());
        View view = fragmentAddProductCategoryBinding.getRoot();
        getActivity().setTitle("My Orders");
        ViewPager viewPager = fragmentAddProductCategoryBinding.addProductCategoryViewpagerId;
        setViewPager(viewPager);
        TabLayout tabLayout = fragmentAddProductCategoryBinding.addProductCategoryTabId;
        tabLayout.setupWithViewPager(viewPager);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).getUser();
        return view;
    }


    public void setViewPager(ViewPager viewPager) {
        TabPageAdapter tabPageAdapter = new TabPageAdapter(this.getChildFragmentManager());
        tabPageAdapter.onAddFragment(new AddProductFragment(), "Add Product");
        tabPageAdapter.onAddFragment(new AddCategoryFragment(), "Add Category");
        viewPager.setAdapter(tabPageAdapter);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
