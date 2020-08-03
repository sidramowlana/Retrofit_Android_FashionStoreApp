package com.example.fashionstoreapp.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionstoreapp.Adapters.HomeAdapter;
import com.example.fashionstoreapp.CallBacks.ItemClickCallback;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Models.Product;
import com.example.fashionstoreapp.Models.ProductTag;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitAPIService.ProductService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.CommonlistviewBinding;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class CategoryProductActivity extends AppCompatActivity implements ResponseCallback, ItemClickCallback {

    private CommonlistviewBinding commonlistviewBinding;
    private LoginResponse loginResponse;
    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private List<Product> productList = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_product);
        commonlistviewBinding = CommonlistviewBinding.inflate(getLayoutInflater());
        View view = commonlistviewBinding.getRoot();
        setContentView(view);
        setSupportActionBar(commonlistviewBinding.detailToolbarId);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Window window = this.getWindow();
            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // finally change the color
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        recyclerView = commonlistviewBinding.commonRecycleviewId;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        homeAdapter = new HomeAdapter(this);

        ProductService productService = new ProductService();
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).getUser();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            final String tagName = bundle.getString("productTagName");
            final int tagId = bundle.getInt("productTagId");
            getSupportActionBar().setTitle(tagName);
            productService.getProductByTagId(tagId, "Bearer " + loginResponse.getToken(), this);
        }
    }


    @Override
    public void onSuccess(Response response) {
        List<ProductTag> productTagList = (List<ProductTag>) response.body();
        if(productTagList.isEmpty())
        {
            recyclerView.setVisibility(View.GONE);
            commonlistviewBinding.emptyView.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(R.drawable.empty_home)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(commonlistviewBinding.emptyView);
        }else {
            for (ProductTag productTag : productTagList) {
                productList.add(productTag.getProduct());
            }
            homeAdapter.setAllProductData(productList);
            recyclerView.setAdapter(homeAdapter);
            homeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String errorMessage) {
        if (errorMessage == null) {
            FancyToast.makeText(this, "Server down. Please try again later", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();

        } else {
            FancyToast.makeText(this, errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            System.out.println("error messages hererere" + errorMessage);
        }
    }

    @Override
    public void onItemClickListener(Integer id) {
        if (loginResponse.getRoles().equals("ROLE_ADMIN")) {
            startActivity(new Intent(this, AdminProductDetailActivity.class).putExtra("productId", id));
        } else if (loginResponse.getRoles().equals("ROLE_USER")) {
            startActivity(new Intent(this, ProductDetailActivity.class).putExtra("productId", id));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
