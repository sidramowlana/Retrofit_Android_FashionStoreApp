package com.example.fashionstoreapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.fashionstoreapp.Adapters.TabPageAdapter;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Models.Orders;
import com.example.fashionstoreapp.RetrofitAPIService.OrderService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.FragmentOrdersBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
//setting p tabs in a fragment
public class OrdersFragment extends Fragment {

    FragmentOrdersBinding fragmentOrdersBinding;
    RecyclerView recyclerView;
    OrderService orderService;
    LoginResponse loginResponse;
    List<Orders> ordersList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentOrdersBinding = FragmentOrdersBinding.inflate(getLayoutInflater());
        View view = fragmentOrdersBinding.getRoot();
        getActivity().setTitle("My Orders");
        ViewPager viewPager = fragmentOrdersBinding.orderViewpagerId;
        setViewPager(viewPager);
        TabLayout tabLayout = fragmentOrdersBinding.orderTabId;
        tabLayout.setupWithViewPager(viewPager);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).getUser();
//        orderService = new OrderService();
//        orderService.getAllUserOrders(Integer.parseInt(loginResponse.getId()), "Bearer " + loginResponse.getToken(), this);
        return view;
    }

    public void setViewPager(ViewPager viewPager) {
        TabPageAdapter tabPageAdapter = new TabPageAdapter(this.getChildFragmentManager());
        tabPageAdapter.onAddFragment(new PendingOrderFragment(), "Pending");
        tabPageAdapter.onAddFragment(new CompletedOrderFragment(), "Completed");
        tabPageAdapter.onAddFragment(new CancelledOrderFragment(), "Cancelled");
        viewPager.setAdapter(tabPageAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}

