package com.example.fashionstoreapp.Activities;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionstoreapp.Adapters.FeedbackAdapter;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Interface.SharedService;
import com.example.fashionstoreapp.Models.RateReview;
import com.example.fashionstoreapp.RetrofitAPIService.ProductService;
import com.example.fashionstoreapp.RetrofitAPIService.RateReviewService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.ActivityProductFeedbackBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class ProductFeedbackActivity extends AppCompatActivity implements SharedService, ResponseCallback {

    ActivityProductFeedbackBinding activityProductFeedbackBinding;
    FeedbackAdapter feedbackAdapter;
    private LoginResponse loginResponse;
    private ProductService productService;
    private RateReviewService rateReviewService;
    RecyclerView recyclerView;
    List<RateReview> rateReviewList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProductFeedbackBinding = ActivityProductFeedbackBinding.inflate(getLayoutInflater());
        View view = activityProductFeedbackBinding.getRoot();

        setContentView(view);
        setSupportActionBar(activityProductFeedbackBinding.productFeedbackToolbarId);
        productService = new ProductService();
        rateReviewService = new RateReviewService();
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).getUser();

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Customer Reviews");
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            final int productId = bundle.getInt("productFeedbackId");
            rateReviewService.getRateReviewByProductId(productId,"Bearer "+loginResponse.getToken(),this);
        }
        recyclerView = activityProductFeedbackBinding.productFeedbackRecycleviewId;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onCalculateRatingAverage() {
        DecimalFormat df2 = new DecimalFormat("0.0");
        double totalRate = 0;
        double average = 0;
        for (RateReview rateReview : rateReviewList) {
            totalRate += rateReview.getRate();
            average = totalRate / rateReviewList.size();
        }
        activityProductFeedbackBinding.productFeedbackRatingbarId.setRating((float) average);
        activityProductFeedbackBinding.productFeedbackRateValueId.setText(df2.format(average));

    }

    @Override
    public void onSuccess(Response response) {
        rateReviewList = (List<RateReview>) response.body();
        feedbackAdapter = new FeedbackAdapter(getApplicationContext(),rateReviewList);
        recyclerView.setAdapter(feedbackAdapter);
        feedbackAdapter.notifyDataSetChanged();
        onCalculateRatingAverage();
    }

    @Override
    public void onError(String errorMessage) {
        FancyToast.makeText(getApplicationContext(), "Slow internet connection. "+errorMessage, Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

    }
}
