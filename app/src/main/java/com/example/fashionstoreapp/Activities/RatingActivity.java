package com.example.fashionstoreapp.Activities;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Models.Product;
import com.example.fashionstoreapp.Models.RateReview;
import com.example.fashionstoreapp.RetrofitAPIService.ProductService;
import com.example.fashionstoreapp.RetrofitAPIService.RateReviewService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.ActivityRatingBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Response;

public class RatingActivity extends AppCompatActivity implements ResponseCallback {

    private ActivityRatingBinding activityRatingBinding;
    private EditText rateFeedback;
    private RatingBar ratingBar;
    private RateReviewService rateReviewService;
    private LoginResponse loginResponse;
    private ResponseCallback saveProductCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRatingBinding = ActivityRatingBinding.inflate(getLayoutInflater());
        View view = activityRatingBinding.getRoot();
        rateFeedback = activityRatingBinding.rateEtFeedbackId;
        ratingBar = activityRatingBinding.rateProductRatingbarId;
        setTitle("My Ratings");
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getApplicationContext()).getUser();
        final ProductService productService = new ProductService();
        rateReviewService = new RateReviewService();
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            final int productId = bundle.getInt("rateProductId", 0);
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    switch ((int) ratingBar.getRating()) {
                        case 1:
                            activityRatingBinding.rateDescriptionId.setText("Very Bad");
                            break;
                        case 2:
                            activityRatingBinding.rateDescriptionId.setText("Bad");
                            break;
                        case 3:
                            activityRatingBinding.rateDescriptionId.setText("Some What");
                            break;
                        case 4:
                            activityRatingBinding.rateDescriptionId.setText("Good");
                            break;
                        case 5:
                            activityRatingBinding.rateDescriptionId.setText("Excellent");
                            break;
                        default:
                            activityRatingBinding.rateDescriptionId.setText("");
                    }
                }
            });
            activityRatingBinding.rateSubmitBtnId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productService.getProduct(productId, saveProductCallback);
                }
            });
        }
        saveProductCallback = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                Product product = (Product) response.body();
                onSubmitRate(product);

            }

            @Override
            public void onError(String errorMessage) {
                FancyToast.makeText(getApplicationContext(), "Error: Please give your feed back later", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        };
        setContentView(view);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSuccess(Response response) {
        if (response != null) {
            FancyToast.makeText(getApplicationContext(), "Thank you for your feedback", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
            ratingBar.setRating(0);
            rateFeedback.setText("");
            onBackPressed();
        }
    }

    @Override
    public void onError(String errorMessage) {
        FancyToast.makeText(getApplicationContext(), errorMessage, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
    }


    public void onSubmitRate(Product product) {
        int rating = (int) ratingBar.getRating();
        String feedback = rateFeedback.getText().toString();
        if (ratingBar.toString().isEmpty() && rateFeedback.toString().isEmpty()) {
            FancyToast.makeText(getApplicationContext(), "Fields cannot be empty", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String date = sdf.format(new Date());
            RateReview rateReview = new RateReview(product, rating, feedback, date);
            System.out.println("product : " + product.getProductName());
            System.out.println("rating : " + rating);
            System.out.println("feedback : " + feedback);
            System.out.println("Date : " + date);
            rateReviewService.onAddRateReviewByProductId(product.getProductId(), rateReview, "Bearer " + loginResponse.getToken(), this);
        }
    }
}
