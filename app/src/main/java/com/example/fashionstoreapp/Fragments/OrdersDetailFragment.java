package com.example.fashionstoreapp.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionstoreapp.Activities.ProductRatingActivity;
import com.example.fashionstoreapp.Adapters.OrdersDetailsAdapter;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Models.CartOrders;
import com.example.fashionstoreapp.Models.Orders;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitAPIService.CartOrdersService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.FragmentOrdersDetailBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersDetailFragment extends Fragment implements ResponseCallback {

    private FragmentOrdersDetailBinding fragmentOrderDetailBinding;
    private RecyclerView recyclerView;
    private CartOrdersService cartOrdersService;
    private List<CartOrders> cartOrdersList = new ArrayList<>();
    private CartOrders updateCartOrders;
    private LoginResponse loginResponse;
    private ResponseCallback updateOrderResponseCallBack;

    public OrdersDetailFragment() {
        // Required empty public constructor
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentOrderDetailBinding = FragmentOrdersDetailBinding.inflate(getLayoutInflater());
        View view = fragmentOrderDetailBinding.getRoot();
        super.onViewCreated(view, savedInstanceState);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).getUser();
        cartOrdersService = new CartOrdersService();

        recyclerView = fragmentOrderDetailBinding.orderDetailRecycleviewId;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        Bundle bundle = getArguments();
        if (bundle != null) {
            int ordersId = bundle.getInt("ordersId");
            cartOrdersService.getAllCartByOrderId(ordersId, "Bearer " + loginResponse.getToken(), this);
            fragmentOrderDetailBinding.orderDetailOrderBtnId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCancelPendingCompleteOrder();
                }
            });
        }

        updateOrderResponseCallBack = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                updateCartOrders = (CartOrders) response.body();
                if (updateCartOrders.getOrders().getStatus().equals("Cancelled")) {
                    fragmentManager.beginTransaction().replace(R.id.frameLayout, new CancelledOrderFragment()).commit();
                } else if (updateCartOrders.getOrders().getStatus().equals("Pending")) {
                    fragmentManager.beginTransaction().replace(R.id.frameLayout, new PendingOrderFragment()).commit();
                }
            }

            @Override
            public void onError(String errorMessage) {
                FancyToast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        };
        return view;
    }

    @Override
    public void onSuccess(Response response) {
        cartOrdersList = (List<CartOrders>) response.body();
        for (CartOrders cartOrders : cartOrdersList) {
            fragmentOrderDetailBinding.orderDetailOrdernoId.setText(cartOrders.getCardOrderId().toString());
            fragmentOrderDetailBinding.orderDetailDateId.setText(cartOrders.getOrders().getDate());
            fragmentOrderDetailBinding.orderDetailStatusId.setText(cartOrders.getOrders().getStatus());
//            fragmentOrderDetailBinding.orderDetailShippingAddressId.setText(cartOrders.getCart().getUser().getAddress());
            fragmentOrderDetailBinding.orderDetailAmountId.setText("USD $" + cartOrders.getOrders().getTotal());
            if (cartOrders.getOrders().getStatus().equals("Pending")) {
                fragmentOrderDetailBinding.orderDetailOrderBtnId.setText(R.string.cancel_order);
            } else if (cartOrders.getOrders().getStatus().equals("Cancelled")) {
                if (loginResponse.getRoles().equals("ROLE_ADMIN")) {
                    showAdmin(cartOrders);
                } else {
                    fragmentOrderDetailBinding.orderDetailOrderBtnId.setText(R.string.re_order);
                }
            } else if (cartOrders.getOrders().getStatus().equals("Completed")) {
                if (loginResponse.getRoles().equals("ROLE_ADMIN")) {
                    showAdmin(cartOrders);
                } else {
                    fragmentOrderDetailBinding.orderDetailOrderBtnId.setText(R.string.rate_my_order);
                }
            }
        }
        OrdersDetailsAdapter ordersDetailsAdapter = new OrdersDetailsAdapter(getActivity(), cartOrdersList);
        recyclerView.setAdapter(ordersDetailsAdapter);
    }

    public void showAdmin(CartOrders cartOrders) {
        fragmentOrderDetailBinding.orderDetailOrderBtnId.setVisibility(View.GONE);
        fragmentOrderDetailBinding.orderDetailssUsernameTextViewId.setVisibility(View.VISIBLE);
        fragmentOrderDetailBinding.orderDetailssEmailTextViewId.setVisibility(View.VISIBLE);
        fragmentOrderDetailBinding.orderDetailssPhoneTextViewId.setVisibility(View.VISIBLE);

        fragmentOrderDetailBinding.orderDetailssUsernameTextViewId.setText(cartOrders.getCart().getUser().getUsername());
        fragmentOrderDetailBinding.orderDetailssEmailTextViewId.setText(cartOrders.getCart().getUser().getEmail());
        fragmentOrderDetailBinding.orderDetailssPhoneTextViewId.setText(cartOrders.getCart().getUser().getPhone());
    }

    @Override
    public void onError(String errorMessage) {
        FancyToast.makeText(getContext(), errorMessage, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
    }

    public void onCancelPendingCompleteOrder() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date = sdf.format(new Date());
        if (getArguments() != null) {
            CartOrders cartOrders = cartOrdersList.get(0);
            if (cartOrders.getOrders().getStatus().equals("Pending")) {
                Orders updateOrders = new Orders(date, "Cancelled", cartOrders.getOrders().getTotal());
                CartOrders updateCartOrders = new CartOrders(cartOrders.getCart(), updateOrders);
                cartOrdersService.updateOrderStatus(cartOrders.getCardOrderId(), updateCartOrders, "Bearer " + loginResponse.getToken(), updateOrderResponseCallBack);
                getActivity().setTitle("Cancelled Orders");
                FancyToast.makeText(getActivity(), "Your Order is cancelled Successfully", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
            } else if (cartOrders.getOrders().getStatus().equals("Cancelled")) {
                Orders updateOrders = new Orders(date, "Pending", cartOrders.getOrders().getTotal());
                CartOrders updateCartOrders = new CartOrders(cartOrders.getCart(), updateOrders);
                cartOrdersService.updateOrderStatus(cartOrders.getCardOrderId(), updateCartOrders, "Bearer " + loginResponse.getToken(), updateOrderResponseCallBack);
                getActivity().setTitle("Pending Orders");
                FancyToast.makeText(getActivity(), "Order made Successfully", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
            } else if (cartOrders.getOrders().getStatus().equals("Completed")) {
                Intent intent = new Intent(getContext(), ProductRatingActivity.class);
                intent.putExtra("completedOrdersId", cartOrders.getOrders().getOrdersId());
                getContext().startActivity(intent);
            }
        }
    }
}
