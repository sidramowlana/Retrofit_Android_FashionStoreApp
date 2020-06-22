package com.example.fashionstoreapp.Activities;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Interface.SharedService;
import com.example.fashionstoreapp.Models.Product;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitAPIService.ProductService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.ActivityProductDetailBinding;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity implements SharedService {

    ActivityProductDetailBinding activityProductDetailBinding;
    Product product;
    ProductService productService;
    String selectedSize;
    LoginResponse loginResponse;
    public static String ORDER_KEY = "com.example.fashionstore.Activities.ORDER_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProductDetailBinding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        View view = activityProductDetailBinding.getRoot();
        setContentView(view);
        setSupportActionBar(activityProductDetailBinding.detailToolbarId);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productService = new ProductService();
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).getUser();
        Bundle bundle = getIntent().getExtras();

        final ResponseCallback productResponseCallback = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                product = (Product) response.body();
                activityProductDetailBinding.detailProductNameId.setText(product.getProductName());
                activityProductDetailBinding.detailProductPriceId.setText("USD $ " + String.valueOf(product.getPrice()));
                activityProductDetailBinding.detailProductDescriptionId.setText(product.getShortDescription());
                Picasso.get()
                        .load(product.getScaledImage())
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.error)
                        .into(activityProductDetailBinding.detailImageViewId);
                if (product.getQuantity() == 0) {
                    Toast.makeText(getApplicationContext(), "Out of Stock", Toast.LENGTH_SHORT).show();
                } else {
                    activityProductDetailBinding.textviewAvailableQtyId.setText(String.valueOf(product.getQuantity()));
                }
                activityProductDetailBinding.detailSmallBtnId.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activityProductDetailBinding.textviewSelectesSizeId.setText("S");
                        selectedSize = activityProductDetailBinding.textviewSelectesSizeId.getText().toString();
//                        GradientDrawable gradientDrawable = new GradientDrawable();
//                        gradientDrawable.setStroke(2, getResources().getColor(R.color.colorPrimary));
//                        activityProductDetailBinding.detailSmallBtnId.setBackground(gradientDrawable);
                        onSelectButton(activityProductDetailBinding.detailSmallBtnId);
                        removeSelectButton(activityProductDetailBinding.detailMediumBtnId);
                        removeSelectButton(activityProductDetailBinding.detailLargeBtnId);

                    }
                });

                activityProductDetailBinding.detailMediumBtnId.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activityProductDetailBinding.textviewSelectesSizeId.setText("M");
                        selectedSize = activityProductDetailBinding.textviewSelectesSizeId.getText().toString();
                        onSelectButton(activityProductDetailBinding.detailMediumBtnId);
                        removeSelectButton(activityProductDetailBinding.detailSmallBtnId);
                        removeSelectButton(activityProductDetailBinding.detailLargeBtnId);
                    }
                });
                activityProductDetailBinding.detailLargeBtnId.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activityProductDetailBinding.textviewSelectesSizeId.setText("L");
                        selectedSize = activityProductDetailBinding.textviewSelectesSizeId.toString();
                        onSelectButton(activityProductDetailBinding.detailLargeBtnId);
                        removeSelectButton(activityProductDetailBinding.detailMediumBtnId);
                        removeSelectButton(activityProductDetailBinding.detailSmallBtnId);
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                if (errorMessage != null) {
                    FancyToast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false);
                    System.out.println("error here is:" + errorMessage);
                }
            }
        };

        final ResponseCallback productFavouriteResponseCallback = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                if (response != null) {
                    activityProductDetailBinding.buttonFavorite.setChecked(true);
                }
            }

            @Override
            public void onError(String errorMessage) {
                System.out.println("productFavouriteResponseCallback error: " + errorMessage);
            }
        };

        if (bundle != null) {
            final int productId = bundle.getInt("productId");
            productService.getProduct(productId, productResponseCallback);
            productService.getAWishlistProduct(productId, "Bearer " + loginResponse.getToken(), productFavouriteResponseCallback);
            activityProductDetailBinding.buttonFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddRemoveProductWishlist(productId, loginResponse, productResponseCallback);
                }
            });
            activityProductDetailBinding.buttonShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onShare(product);
                }
            });

        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        onCalculateRatingAverage();
    }

    public void onAddRemoveProductWishlist(Integer productId, LoginResponse loginResponse, ResponseCallback productResponseCallback) {
        if (activityProductDetailBinding.buttonFavorite.isChecked()) {
            productService.onAddRemoveProductFavourite(productId, "Bearer " + loginResponse.getToken(), productResponseCallback);
            FancyToast.makeText(getApplicationContext(), "Added to your WishList", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
        } else {
            productService.onAddRemoveProductFavourite(productId, "Bearer " + loginResponse.getToken(), productResponseCallback);
            FancyToast.makeText(getApplicationContext(), "Removed from your WishList", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
        }
    }

    public void onShare(Product shareProduct) {
        String share = "Look what I found!!!" + "\n" + "\n" + shareProduct.getProductName() + "\n" + "USD $ " + shareProduct.getPrice() + "\n" + shareProduct.getLongDescription() + "\n" + shareProduct.getScaledImage();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, share);
        sendIntent.setType("text/plain");
        Intent.createChooser(sendIntent, "Share via");
        startActivity(sendIntent);
    }

    @Override
    public void onCalculateRatingAverage() {
        DecimalFormat df2 = new DecimalFormat("0.0");
        double totalRate = 0;
        double average = 0;
//        List<RateReview> rateList = RateReview.find(RateReview.class, "product=?", product.getId().toString());
//        for (RateReview rateReview : rateList) {
//            totalRate += rateReview.getRate();
//            average = totalRate / rateList.size();
//        }
//        activityProductDetailBinding.includeFeedbackId.feedbackRatingbarId.setRating((float) average);
//        activityProductDetailBinding.includeFeedbackId.feedbackRateValueId.setText(df2.format(average));
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
    public void onResume() {
        super.onResume();
    }
   public void onSelectButton(Button button)
    {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setStroke(2, getResources().getColor(R.color.colorPrimary));
        button.setBackground(gradientDrawable);
    }
    public void removeSelectButton(Button button){
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setStroke(2, getResources().getColor(R.color.darker_gray));
        button.setBackground(gradientDrawable);
    }
}