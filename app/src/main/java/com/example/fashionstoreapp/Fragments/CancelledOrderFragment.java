package com.example.fashionstoreapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionstoreapp.Adapters.OrderAdapter;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Interface.OrderInterface;
import com.example.fashionstoreapp.Models.Orders;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitAPIService.OrderService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.CommonlistviewBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CancelledOrderFragment extends Fragment  implements OrderInterface, ResponseCallback {


    private CommonlistviewBinding commonlistviewBinding;
    private OrderAdapter orderAdapter;
    private RecyclerView recyclerView;
    private OrderService orderService;
    private List<Orders> pendingOrdersList = new ArrayList<>();
    private LoginResponse loginResponse;


    public CancelledOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        commonlistviewBinding = CommonlistviewBinding.inflate(getLayoutInflater());
        View view = commonlistviewBinding.getRoot();
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).getUser();
        orderService = new OrderService();
        orderService.getAllUserOrdersByStatus(Integer.valueOf(loginResponse.getId()), "Cancelled", "Bearer " + loginResponse.getToken(), this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = commonlistviewBinding.commonRecycleviewId;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        orderAdapter = new OrderAdapter(this);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetailsFragment(Orders order) { Bundle bundle = new Bundle();
        bundle.putInt("ordersId", order.getOrdersId());
        OrdersDetailFragment orderDetailFragment = new OrdersDetailFragment();
        orderDetailFragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, orderDetailFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onSuccess(Response response) {
        pendingOrdersList = (List<Orders>) response.body();
        orderAdapter.setAllPendingProductData(pendingOrdersList);
        recyclerView.setAdapter(orderAdapter);
        orderAdapter.notifyDataSetChanged();

    }

    @Override
    public void onError(String errorMessage) {
        FancyToast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false);
    }
}
