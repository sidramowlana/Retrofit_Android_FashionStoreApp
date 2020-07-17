package com.example.fashionstoreapp.Fragments;

import android.annotation.SuppressLint;
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

import com.example.fashionstoreapp.Adapters.OrdersDetailsAdapter;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Models.CartOrders;
import com.example.fashionstoreapp.Models.Orders;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitAPIService.OrderService;
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
    private OrdersDetailsAdapter ordersDetailsAdapter;
    private RecyclerView recyclerView;
    private OrderService orderService;
    private List<CartOrders> cartOrdersList = new ArrayList<>();
    private CartOrders updateCartOrders;
    private LoginResponse loginResponse;
    int ordersId;
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
        orderService = new OrderService();

        recyclerView = fragmentOrderDetailBinding.orderDetailRecycleviewId;

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Bundle bundle = getArguments();
        if (bundle != null) {
            ordersId = bundle.getInt("ordersId");
            orderService.getAllCartByOrderId(ordersId, "Bearer " + loginResponse.getToken(), this);
            fragmentOrderDetailBinding.orderDetailOrderBtnId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCancelPendingCompleteOrder();
                }
            });
        }
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        updateOrderResponseCallBack = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                updateCartOrders = (CartOrders) response.body();
                System.out.println("working hereerererere: " + updateCartOrders.getOrders().getStatus());
                System.out.println("working hereerererere: " + updateCartOrders.getOrders().getTotal());
                System.out.println("working hereerererere: " + updateCartOrders.getOrders().getDate());
                System.out.println("working hereerererere: " + updateCartOrders.getCart());
//                for (CartOrders cartOrders : cartOrdersList) {
                if (updateCartOrders.getOrders().getStatus().equals("Cancelled")) {
                    fragmentManager.beginTransaction().replace(R.id.frameLayout, new CancelledOrderFragment()).commit();
                    System.out.println("Your Order is cancelled Successfully");
                    FancyToast.makeText(getActivity(), "Your Order is cancelled Successfully", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false);
                } else if (updateCartOrders.getOrders().getStatus().equals("Pending")) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    getActivity().setTitle("Pending Order");
                    fragmentManager.beginTransaction().replace(R.id.frameLayout, new PendingOrderFragment()).commit();
                    FancyToast.makeText(getActivity(), "Order made Successfully", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false);
                }
//                }
            }

            @Override
            public void onError(String errorMessage) {
                FancyToast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false);

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
                fragmentOrderDetailBinding.orderDetailOrderBtnId.setText(R.string.re_order);
            } else if (cartOrders.getOrders().getStatus().equals("Completed")) {
                fragmentOrderDetailBinding.orderDetailOrderBtnId.setText(R.string.rate_my_order);
            }
        }
        ordersDetailsAdapter = new OrdersDetailsAdapter(getActivity(), cartOrdersList);
        recyclerView.setAdapter(ordersDetailsAdapter);
    }

    @Override
    public void onError(String errorMessage) {
        FancyToast.makeText(getContext(), "Error herer.", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
    }


    public void onCancelPendingCompleteOrder() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date = sdf.format(new Date());
        if (getArguments() != null) {
            for (CartOrders cartOrders : cartOrdersList) {
                if (cartOrders.getOrders().getStatus().equals("Pending")) {
                    Orders updateOrders = new Orders(date, "Cancelled", cartOrders.getOrders().getTotal());
                    CartOrders updateCartOrders = new CartOrders(cartOrders.getCart(), updateOrders);
                    orderService.updateOrderStatus(cartOrders.getCardOrderId(), updateCartOrders, "Bearer " + loginResponse.getToken(), updateOrderResponseCallBack);
                } else if (cartOrders.getOrders().getStatus().equals("Cancelled")) {
                    Orders updateOrders = new Orders(date, "Pending", cartOrders.getOrders().getTotal());
                    CartOrders updateCartOrders = new CartOrders(cartOrders.getCart(), updateOrders);
                    orderService.updateOrderStatus(cartOrders.getCardOrderId(), updateCartOrders, "Bearer " + loginResponse.getToken(), updateOrderResponseCallBack);

                } else if (cartOrders.getOrders().getStatus().equals("Completed")) {
//                    Intent intent = new Intent(getContext(), ProductRatingActivity.class);
//                    intent.putExtra("orderId", cartOrders.getCardOrderId());
//                    getContext().startActivity(intent);
                }
            }
        }
    }
}
