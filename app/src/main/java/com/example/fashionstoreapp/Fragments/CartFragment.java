package com.example.fashionstoreapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionstoreapp.Activities.ProductDetailActivity;
import com.example.fashionstoreapp.Adapters.CartAdapter;
import com.example.fashionstoreapp.CallBacks.ItemClickCallback;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Interface.CartInterface;
import com.example.fashionstoreapp.Models.Cart;
import com.example.fashionstoreapp.RetrofitAPIService.CartService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.FragmentCartBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment implements CartInterface,
        ResponseCallback, ItemClickCallback {

    FragmentCartBinding fragmentCartBinding;
    CartAdapter cartAdapter;
    LoginResponse loginResponse;
    RecyclerView recyclerView;
    CartService cartService;
    List<Cart> cartList = new ArrayList<>();
    double totalPrice = 0.0;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentCartBinding = FragmentCartBinding.inflate(getLayoutInflater());
        View view = fragmentCartBinding.getRoot();
        getActivity().setTitle("My Cart");
        super.onViewCreated(view, savedInstanceState);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).getUser();
        cartService = new CartService();
        cartService.getAllCartProducts("Bearer " + loginResponse.getToken(), this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = fragmentCartBinding.cartRecycleviewId;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        cartAdapter = new CartAdapter(this, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSuccess(Response response) {
        cartList = (List<Cart>) response.body();
        cartAdapter.setAllCartProductData(cartList);
        recyclerView.setAdapter(cartAdapter);
        setUpdateTotal(cartAdapter.calculateTotal());
        fragmentCartBinding.cartCheckoutBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartList.isEmpty()) {
                    Toast.makeText(getContext(), "Your cart is empty", Toast.LENGTH_SHORT).show();
                } else {
                    onShowCheckoutDialog(cartAdapter.calculateTotal()).show(getChildFragmentManager(), "Payments");
                }
            }
        });
        cartAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String errorMessage) {
        FancyToast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false);
        System.out.println("error messages hererere" + errorMessage);
    }

    @Override
    public void setUpdateTotal(double total) {
        System.out.println("setting the total2: "+total);
        fragmentCartBinding.cartTextAmountId.setText(String.valueOf(total));
    }

    @Override
    public void onItemClickListener(Integer id) {
        Log.e("clicked product detail:", String.valueOf(id));
        startActivity(new Intent(getActivity(), ProductDetailActivity.class).putExtra("productId", id));
    }


    public PaymentDialogFragment onShowCheckoutDialog(double totalPrice) {
        PaymentDialogFragment paymentDialogFragment = new PaymentDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble("TOTAL_AMOUNT_KEY", totalPrice);
        paymentDialogFragment.setArguments(bundle);
        return paymentDialogFragment;
    }
}