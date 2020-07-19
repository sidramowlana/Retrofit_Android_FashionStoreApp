package com.example.fashionstoreapp.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionstoreapp.Adapters.ProductRatingAdapter;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Models.CartOrders;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitAPIService.CartOrdersService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.CommonlistviewBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import retrofit2.Response;

public class ProductRatingActivity extends AppCompatActivity implements ResponseCallback {

    CommonlistviewBinding commonlistviewBinding;
    RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commonlistviewBinding = CommonlistviewBinding.inflate(getLayoutInflater());
        View view = commonlistviewBinding.getRoot();
        setContentView(view);
        setSupportActionBar(commonlistviewBinding.detailToolbarId);
        LoginResponse loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getApplicationContext()).getUser();
        CartOrdersService cartOrdersService = new CartOrdersService();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Rate & Reviews");
            Window window = this.getWindow();
            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // finally change the color
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        recyclerView = commonlistviewBinding.commonRecycleviewId;
        Intent intent = getIntent();
        int cartOrdersId = intent.getIntExtra("completedOrdersId", 0);
        cartOrdersService.getAllCartByOrderId(cartOrdersId, "Bearer " + loginResponse.getToken(), this);
        recyclerView = commonlistviewBinding.commonRecycleviewId;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onSuccess(Response response) {
        List<CartOrders> cartOrdersList = (List<CartOrders>) response.body();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ProductRatingAdapter productRatingAdapter = new ProductRatingAdapter(getApplicationContext(), cartOrdersList);
        recyclerView.setAdapter(productRatingAdapter);
    }

    @Override
    public void onError(String errorMessage) {
        FancyToast.makeText(getApplicationContext(), errorMessage, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
    }
}
