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
import com.example.fashionstoreapp.Models.RateReview;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitAPIService.CartService;
import com.example.fashionstoreapp.RetrofitAPIService.ProductService;
import com.example.fashionstoreapp.RetrofitAPIService.RateReviewService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.ActivityProductDetailBinding;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity implements SharedService {

    ActivityProductDetailBinding activityProductDetailBinding;
    Product product;
    ProductService productService;
    RateReviewService rateReviewService;
    CartService cartService;
    String selectedSize;
    LoginResponse loginResponse;
    List<RateReview> rateReviewList = new ArrayList<>();

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
        rateReviewService = new RateReviewService();
        cartService = new CartService();
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).getUser();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            final int productId = bundle.getInt("productId");
            productService.getProduct(productId, productDetailResponseCallback());
            productService.getAWishlistProduct(productId, "Bearer " + loginResponse.getToken(), favouriteCallBack());
            rateReviewService.getRateReviewByProductId(productId, "Bearer " + loginResponse.getToken(), rateReview());
            //add and remove wishlist
            activityProductDetailBinding.buttonFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddRemoveProductWishlist(productId, loginResponse, productDetailResponseCallback());
                }
            });
            //share
            activityProductDetailBinding.buttonShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onShare(product);
                }
            });
            //add to cart
            activityProductDetailBinding.detailAddCartBtnId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (product.getQuantity() == 0) {
                        FancyToast.makeText(getApplicationContext(), "Out of stock", Toast.LENGTH_SHORT, FancyToast.ERROR, false);
                        activityProductDetailBinding.textviewAvailableQtyId.setText("Out of Stock");
                    } else {
                        int quantity = Integer.parseInt(activityProductDetailBinding.detailQtyId.getNumber());
                        String size = activityProductDetailBinding.textviewSelectesSizeId.getText().toString();
                        double total = quantity * product.getPrice();
                        onAddProductCart(productId, quantity, size, total, loginResponse, productDetailResponseCallback());
                    }
                }
            });
            activityProductDetailBinding.detailQACardId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), QuestionAnswerActivity.class);
                    intent.putExtra("productQAId", product.getProductId());
                    startActivity(intent);
                }
            });
        }
    }

    public void onAddProductCart(Integer productId, Integer quantity, String size, Double total, LoginResponse loginResponse, ResponseCallback responseCallback) {
        if (size.equals("")) {
            FancyToast.makeText(getApplicationContext(), "Please select a size", Toast.LENGTH_SHORT, FancyToast.WARNING, false).show();
        } else {
            cartService.onAddProductCart(productId, quantity, size, total, "Bearer " + loginResponse.getToken(), responseCallback);
            FancyToast.makeText(getApplicationContext(), "Added to your cart", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
        }
    }

    public ResponseCallback rateReview() {
        ResponseCallback rateReviewProductCallback = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                rateReviewList = (List<RateReview>) response.body();
                int reviewCount = rateReviewList.size();
                activityProductDetailBinding.includeFeedbackId.feedbackReviewCountId.setText("( " + reviewCount + " )");
                onCalculateRatingAverage();
                activityProductDetailBinding.detailFeedbackCardId.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rateReviewList.isEmpty()) {
                            FancyToast.makeText(getApplicationContext(), "No Reviews available", Toast.LENGTH_SHORT, FancyToast.INFO, false).show();
                        } else {
                            Intent intent = new Intent(getApplicationContext(), ProductFeedbackActivity.class);
                            intent.putExtra("productFeedbackId", product.getProductId());
                            startActivity(intent);
                        }
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                System.out.println("error");
            }
        };
        return rateReviewProductCallback;
    }

    public ResponseCallback productDetailResponseCallback() {
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
                }
            }
        };
        return productResponseCallback;
    }

    public ResponseCallback favouriteCallBack() {
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
        return productFavouriteResponseCallback;
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
        for (RateReview rateReview : rateReviewList) {
            totalRate += rateReview.getRate();
            average = totalRate / rateReviewList.size();
        }
        activityProductDetailBinding.includeFeedbackId.feedbackRatingbarId.setRating((float) average);
        activityProductDetailBinding.includeFeedbackId.feedbackRateValueId.setText(df2.format(average));
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

    public void onSelectButton(Button button) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setStroke(2, getResources().getColor(R.color.colorPrimary));
        button.setBackground(gradientDrawable);
    }

    public void removeSelectButton(Button button) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setStroke(2, getResources().getColor(R.color.darker_gray));
        button.setBackground(gradientDrawable);
    }
}