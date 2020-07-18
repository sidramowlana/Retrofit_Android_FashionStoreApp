package com.example.fashionstoreapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionstoreapp.Activities.ProductDetailActivity;
import com.example.fashionstoreapp.Adapters.WishlistAdapter;
import com.example.fashionstoreapp.CallBacks.ItemClickCallback;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Models.Wishlist;
import com.example.fashionstoreapp.RetrofitAPIService.ProductService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.CommonlistviewBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistFragment extends Fragment implements ResponseCallback, ItemClickCallback {

    private CommonlistviewBinding commonlistviewBinding;
    private WishlistAdapter wishlistAdapter;
    private RecyclerView recyclerView;

    public WishlistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        commonlistviewBinding = CommonlistviewBinding.inflate(getLayoutInflater());
        View view = commonlistviewBinding.getRoot();
        getActivity().setTitle("My Wishlist");
        super.onViewCreated(view, savedInstanceState);
        LoginResponse loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).getUser();
        ProductService productService = new ProductService();
        productService.getAllUserWishListProduct("Bearer " + loginResponse.getToken(), this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = commonlistviewBinding.commonRecycleviewId;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        wishlistAdapter = new WishlistAdapter(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSuccess(Response response) {
        List<Wishlist> wishlistList = (List<Wishlist>) response.body();
        wishlistAdapter.setAllWishlistProductData(wishlistList);
        recyclerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String errorMessage) {
        FancyToast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
        System.out.println("error messages hererere" + errorMessage);
    }


    @Override
    public void onItemClickListener(Integer id) {
        Log.e("clicked product detail:", String.valueOf(id));
        startActivity(new Intent(getActivity(), ProductDetailActivity.class).putExtra("productId", id));
    }

}
