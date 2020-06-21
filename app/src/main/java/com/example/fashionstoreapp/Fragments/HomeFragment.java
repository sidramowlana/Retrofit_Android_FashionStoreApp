package com.example.fashionstoreapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.fashionstoreapp.Activities.ProductDetailActivity;
import com.example.fashionstoreapp.Adapters.HomeAdapter;
import com.example.fashionstoreapp.Adapters.SlideAdapter;
import com.example.fashionstoreapp.CallBacks.ItemClickCallback;
import com.example.fashionstoreapp.Models.Product;
import com.example.fashionstoreapp.RetrofitAPIService.ProductService;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.databinding.FragmentHomeBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class HomeFragment extends Fragment implements ResponseCallback, ItemClickCallback {
    HomeAdapter homeAdapter;
    FragmentHomeBinding fragmentHomeBinding;
    RecyclerView recyclerView;
    ViewPager viewPager;
    ProductService productService;
    List<Product> productList = new ArrayList<>();

    private String[] imageUrl = new String[]{
            "https://images.indianexpress.com/2018/09/kids-fashion_759_ts.jpg",
            "https://bestwomenapparels.com/wp-content/uploads/2019/05/bestwomenapparels-fashion.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXQQdCsdRYfb4JoyrtdVsOecXdxmDRjk2SLDul0zLybQG6gn9Znw&s",
            "https://img2268.weyesimg.com/uploads/ouyeedisplays.com/images/15413999922820.jpg?imageView2/2/w/1920/q/75",
            "http://mountmessenger.msmc.edu/wp-content/uploads/2016/09/fashion.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRDMCPEupciGuHCLMrvQur_yD7V91MG1Zh47t4XsDeLeTLWx719sw&s",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTeTVNUSNO76MpyZKPATawdM3ICFCYexjykxD7YMSCKtTfh9lAv&usqp=CAU"};


    public HomeFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentHomeBinding = FragmentHomeBinding.inflate(getLayoutInflater());
        View view = fragmentHomeBinding.getRoot();
        getActivity().setTitle("Home");
        productService = new ProductService();
        productService.getAllProducts(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = fragmentHomeBinding.viewPagerId;
        SlideAdapter adapter = new SlideAdapter(getActivity(), imageUrl);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        autoSlider(viewPager);

        recyclerView = fragmentHomeBinding.includeRecycleViewId.recycleViewId;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        homeAdapter = new HomeAdapter(this);
    }


    public void autoSlider(final ViewPager viewPager) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                int pos = viewPager.getCurrentItem();
                int i = 0;
                if (pos >= i && pos != imageUrl.length - 1) {
                    i = pos;
                    i++;
                } else if (pos < (i - 1)) {
                    i = pos;
                    i++;
                }
                viewPager.setCurrentItem(i, true);
                i++;
                if (i >= imageUrl.length)
                    i = 0;
                autoSlider(viewPager);
            }
        };
        handler.postDelayed(runnable, 4000);
    }

    @Override
    public void onSuccess(Response response) {
        productList = (List<Product>) response.body();
        homeAdapter.setAllProductData(productList);
        recyclerView.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String errorMessage) {
        FancyToast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false);
        System.out.println("error messages hererere" + errorMessage);
    }

    @Override
    public void onItemClickListener(Integer id) {
        Log.e("clicked product detail:" , String.valueOf(id));
        startActivity(new Intent(getActivity(), ProductDetailActivity.class).putExtra("productId",id));
    }

}
