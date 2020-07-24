package com.example.fashionstoreapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.fashionstoreapp.Adapters.TabPageAdapter;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Models.CartOrders;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitAPIService.CartOrdersService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.FragmentOrdersBinding;
import com.google.android.material.tabs.TabLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
//setting p tabs in a fragment
public class OrdersFragment extends Fragment implements ResponseCallback {

    private FragmentOrdersBinding fragmentOrdersBinding;
    private CartOrdersService cartOrdersService;
    private LoginResponse loginResponse;
    private ResponseCallback updateOrderResponseCallBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentOrdersBinding = FragmentOrdersBinding.inflate(getLayoutInflater());
        View view = fragmentOrdersBinding.getRoot();

        ViewPager viewPager = fragmentOrdersBinding.orderViewpagerId;
        setViewPager(viewPager);
        TabLayout tabLayout = fragmentOrdersBinding.orderTabId;
        tabLayout.setupWithViewPager(viewPager);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).getUser(); if(loginResponse.getRoles().equals("ROLE_ADMIN")){
            getActivity().setTitle("Orders");

        }else {
            getActivity().setTitle("My Orders");
        }
        cartOrdersService = new CartOrdersService();
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        cartOrdersService.getAllCartOrdersByUserId(Integer.valueOf(loginResponse.getId()),"Bearer "+loginResponse.getToken(),this);
        updateOrderResponseCallBack = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                getActivity().setTitle("Completed Order");
                fragmentManager.beginTransaction().replace(R.id.frameLayout, new CompletedOrderFragment()).commit();
                FancyToast.makeText(getContext(), "Your order is completed", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

            }

            @Override
            public void onError(String errorMessage) {
                FancyToast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        };
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

    @Override
    public void onSuccess(Response response) {
        if(response!=null){
            List<CartOrders> cartOrdersList = (List<CartOrders>) response.body();
            for (final CartOrders cartOrders : cartOrdersList) {
                if (cartOrders.getOrders().getStatus().equals("Pending")) {
                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    cartOrders.getOrders().setStatus("Completed");
                                    cartOrdersService.updateOrderStatus(cartOrders.getCardOrderId(),cartOrders,"Bearer "+loginResponse.getToken(),updateOrderResponseCallBack);
                                      }
                            },
//                            120000 //timer for 2 minutes
//                        300000 //timer for 5 minutes
                        86400000 //timer set for one day
                    );
                }
            }
        }

    }

    @Override
    public void onError(String errorMessage) {

    }
}

