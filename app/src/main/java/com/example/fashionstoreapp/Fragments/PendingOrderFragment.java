package com.example.fashionstoreapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionstoreapp.Adapters.OrderAdapter;
import com.example.fashionstoreapp.CallBacks.ItemClickCallback;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Interface.OrderInterface;
import com.example.fashionstoreapp.Models.Orders;
import com.example.fashionstoreapp.RetrofitAPIService.CartService;
import com.example.fashionstoreapp.RetrofitAPIService.OrderService;
import com.example.fashionstoreapp.RetrofitAPIService.ProductService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.CommonlistviewBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PendingOrderFragment extends Fragment implements OrderInterface, ResponseCallback,
        ItemClickCallback {

    CommonlistviewBinding commonlistviewBinding;
    OrderAdapter orderAdapter;
    RecyclerView recyclerView;
    ProductService productService;
    OrderService orderService;
    CartService cartService;
    List<Orders> pendingOrdersList = new ArrayList<>();
    LoginResponse loginResponse;

    public PendingOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        commonlistviewBinding = CommonlistviewBinding.inflate(getLayoutInflater());
        View view = commonlistviewBinding.getRoot();
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).getUser();
        productService = new ProductService();
        orderService = new OrderService();
        orderService.getAllUserOrders(Integer.valueOf(loginResponse.getId()), "Pending", "Bearer " + loginResponse.getToken(), this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = commonlistviewBinding.commonRecycleviewId;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        orderAdapter = new OrderAdapter(this, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetailsFragment(Orders order) {
//        Bundle bundle = new Bundle();
////        Product product = Product.findById(Product.class,"product =
//        bundle.putLong("orderId", order.getId());
//        OrderDetailFragment orderDetailFragment = new OrderDetailFragment();
//        orderDetailFragment.setArguments(bundle);
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frameLayout, orderDetailFragment);
//        fragmentTransaction.commit();
    }

    @Override
    public void onItemClickListener(Integer id) {

    }

    @Override
    public void onSuccess(Response response) {
        pendingOrdersList = (List<Orders>) response.body();
        System.out.println("listing: " + pendingOrdersList);
        orderAdapter.setAllPendingProductData(pendingOrdersList);
        recyclerView.setAdapter(orderAdapter);
        orderAdapter.notifyDataSetChanged();

    }

    @Override
    public void onError(String errorMessage) {
        FancyToast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false);
        System.out.println("error messages hererere" + errorMessage);

    }
}
