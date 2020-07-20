package com.example.fashionstoreapp.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Models.Product;
import com.example.fashionstoreapp.Models.RateReview;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitAPIService.CartService;
import com.example.fashionstoreapp.RetrofitAPIService.ProductService;
import com.example.fashionstoreapp.RetrofitAPIService.RateReviewService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.ActivityAdminProductDetailBinding;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class AdminProductDetailActivity extends AppCompatActivity implements ResponseCallback {

    ActivityAdminProductDetailBinding activityAdminProductDetailBinding;
    Product product;
    ProductService productService;
    RateReviewService rateReviewService;
    CartService cartService;
    String productName;
    String description;
    int quantity;
    double price;
    String selectedSize;
    LoginResponse loginResponse;
    List<RateReview> rateReviewList = new ArrayList<>();
    int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAdminProductDetailBinding = ActivityAdminProductDetailBinding.inflate(getLayoutInflater());
        View view = activityAdminProductDetailBinding.getRoot();
        setContentView(view);
        setSupportActionBar(activityAdminProductDetailBinding.adminDetailToolbarId);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productService = new ProductService();
        rateReviewService = new RateReviewService();
        cartService = new CartService();
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).getUser();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            productId = bundle.getInt("productId");
            productService.getProduct(productId, this);
            activityAdminProductDetailBinding.adminButtonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onUpdateProduct();
                }
            });
            activityAdminProductDetailBinding.adminButtonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    afterEditableChanges();
                }
            });
        }
    }

    public ResponseCallback productDetailResponseCallback() {
        final ResponseCallback productResponseCallback = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
               Product updatedProduct = (Product) response.body();
               //displaying the updated result
                activityAdminProductDetailBinding.adminDetailProductNameId.setText(updatedProduct.getProductName());
                activityAdminProductDetailBinding.adminDetailProductPriceId.setText(String.valueOf(updatedProduct.getPrice()));
                activityAdminProductDetailBinding.adminDetailProductDescriptionId.setText(updatedProduct.getShortDescription());
                activityAdminProductDetailBinding.adminEditviewAvailableQtyId.setText(String.valueOf(updatedProduct.getQuantity()));
                FancyToast.makeText(getApplicationContext(), "Successfully updated", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                afterEditableChanges();
            }

            @Override
            public void onError(String errorMessage) {
                FancyToast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        };
        return productResponseCallback;
    }

    public void editableChanges() {
        activityAdminProductDetailBinding.adminDetailProductNameId.setBackgroundResource(R.drawable.border_change);
        activityAdminProductDetailBinding.adminDetailProductPriceId.setBackgroundResource(R.drawable.border_change);
        activityAdminProductDetailBinding.adminDetailProductDescriptionId.setBackgroundResource(R.drawable.border_change);
        activityAdminProductDetailBinding.adminEditviewAvailableQtyId.setBackgroundResource(R.drawable.border_change);
        activityAdminProductDetailBinding.adminDetailProductNameId.setEnabled(true);
        activityAdminProductDetailBinding.adminDetailProductPriceId.setEnabled(true);
        activityAdminProductDetailBinding.adminDetailProductDescriptionId.setEnabled(true);
        activityAdminProductDetailBinding.adminEditviewAvailableQtyId.setEnabled(true);
        activityAdminProductDetailBinding.adminDetailUpdateCancelLinearId.setEnabled(true);
        activityAdminProductDetailBinding.adminDetailUpdateCancelLinearId.setVisibility(View.VISIBLE);
        activityAdminProductDetailBinding.adminDetailEditLinearId.setVisibility(View.GONE);

    }

    public void afterEditableChanges() {
        activityAdminProductDetailBinding.adminDetailProductNameId.setBackgroundResource(R.drawable.border);
        activityAdminProductDetailBinding.adminDetailProductPriceId.setBackgroundResource(R.drawable.border);
        activityAdminProductDetailBinding.adminDetailProductDescriptionId.setBackgroundResource(R.drawable.border);
        activityAdminProductDetailBinding.adminEditviewAvailableQtyId.setBackgroundResource(R.drawable.border);
        activityAdminProductDetailBinding.adminDetailProductNameId.setEnabled(false);
        activityAdminProductDetailBinding.adminDetailProductPriceId.setEnabled(false);
        activityAdminProductDetailBinding.adminDetailProductDescriptionId.setEnabled(false);
        activityAdminProductDetailBinding.adminEditviewAvailableQtyId.setEnabled(false);
        activityAdminProductDetailBinding.adminDetailUpdateCancelLinearId.setEnabled(false);
        activityAdminProductDetailBinding.adminDetailUpdateCancelLinearId.setVisibility(View.GONE);
        activityAdminProductDetailBinding.adminDetailEditLinearId.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess(Response response) {
        product = (Product) response.body();
        activityAdminProductDetailBinding.adminDetailProductNameId.setText(product.getProductName());
        activityAdminProductDetailBinding.adminDetailProductPriceId.setText(String.valueOf(product.getPrice()));
        activityAdminProductDetailBinding.adminDetailProductDescriptionId.setText(product.getShortDescription());
        activityAdminProductDetailBinding.adminEditviewAvailableQtyId.setText(String.valueOf(product.getQuantity()));
        Picasso.get()
                .load(product.getScaledImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(activityAdminProductDetailBinding.adminDetailImageViewId);
    }

    @Override
    public void onError(String errorMessage) {
        if (errorMessage != null) {
            FancyToast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false);
        }
    }

    public void onUpdateProduct(){
        editableChanges();
        activityAdminProductDetailBinding.adminButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String productName = activityAdminProductDetailBinding.adminDetailProductNameId.getText().toString();
                final String description = activityAdminProductDetailBinding.adminDetailProductDescriptionId.getText().toString();
                final int quantity = Integer.parseInt(String.valueOf(activityAdminProductDetailBinding.adminEditviewAvailableQtyId.getText()));
                final double price = Double.parseDouble(String.valueOf(activityAdminProductDetailBinding.adminDetailProductPriceId.getText()));
                Product updateProduct = new Product(productName, description, quantity, price);
                productService.onUpdateProductByProductId(product.getProductId(), updateProduct, "Bearer " + loginResponse.getToken(), productDetailResponseCallback());
            }
        });
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
}