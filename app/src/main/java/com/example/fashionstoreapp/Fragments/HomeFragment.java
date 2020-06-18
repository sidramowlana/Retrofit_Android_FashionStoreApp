package com.example.fashionstoreapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.fashionstoreapp.APIService.ProductService;
import com.example.fashionstoreapp.Adapters.HomeAdapter;
import com.example.fashionstoreapp.Adapters.SlideAdapter;
import com.example.fashionstoreapp.Models.Product;
import com.example.fashionstoreapp.RetrofitInterface.ResponseCallBackInterface;
import com.example.fashionstoreapp.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class HomeFragment extends Fragment implements ResponseCallBackInterface {
    Context context;
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
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        final List<Product> productList = Product.listAll(Product.class);
        System.out.println("w2");

         productService.getAllProducts(this);
//        homeAdapter = new HomeAdapter(getActivity(), );
        System.out.println("w");

        viewPager = fragmentHomeBinding.viewPagerId;
        SlideAdapter adapter = new SlideAdapter(getActivity(), imageUrl);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        autoSlider(viewPager);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        RecyclerView recyclerView = fragmentHomeBinding.includeRecycleViewId.recycleViewId;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(homeAdapter);
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
        System.out.println("w1");
        System.out.println(productList);
        homeAdapter = new HomeAdapter(context,productList);
    }

    @Override
    public void onError(String errorMessage) {

    }
}
