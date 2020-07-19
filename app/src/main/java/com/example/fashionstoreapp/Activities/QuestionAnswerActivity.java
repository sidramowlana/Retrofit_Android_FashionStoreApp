package com.example.fashionstoreapp.Activities;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionstoreapp.Adapters.QuestionAnswerAdapter;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Models.ProductInquiry;
import com.example.fashionstoreapp.RetrofitAPIService.ProductInquiryService;
import com.example.fashionstoreapp.databinding.ActivityQuestionAnswerBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Response;

public class QuestionAnswerActivity extends AppCompatActivity {

    private ActivityQuestionAnswerBinding activityQuestionAnswerBinding;
    private List<ProductInquiry> productInquiryList = new ArrayList<>();
    private ProductInquiryService productInquiryService;
    private LoginResponse loginResponse;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityQuestionAnswerBinding = ActivityQuestionAnswerBinding.inflate(getLayoutInflater());
        View view = activityQuestionAnswerBinding.getRoot();
        setContentView(view);
        setTitle("Question Answers");
        productInquiryService = new ProductInquiryService();
        loginResponse = new LoginResponse();

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            final int productId = bundle.getInt("productQAId");
            productInquiryService.onGetAllProductInquiry(productId, "Bearer " + loginResponse.getToken(), askInquiry());
            recyclerView = activityQuestionAnswerBinding.questionAnswerRecyclerviewId;
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            //        product = Product.findById(Product.class, id);
            activityQuestionAnswerBinding.questionAnswerSendBtnId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String question = activityQuestionAnswerBinding.questionAnswerQuestionTextboxId.getText().toString();
                    if (question.isEmpty()) {
                        FancyToast.makeText(getApplicationContext(), "Please give us your inquiry", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                    } else {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        String date = sdf.format(new Date());
                        ProductInquiry productInquiry = new ProductInquiry(question, date);
                        productInquiryService.onAddProductInquiryByProductId(productId, productInquiry, "Bearer " + loginResponse.getToken(), askInquiry());
                        activityQuestionAnswerBinding.questionAnswerQuestionTextboxId.setText("");
                        FancyToast.makeText(getApplicationContext(), "Inquiry Submitted. We will reach you soon", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    }
                }
            });
        }

    }

    public ResponseCallback askInquiry() {
        ResponseCallback askInquiryResponseCallback = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                productInquiryList = (List<ProductInquiry>) response.body();
                onDisplayProductInquiries(productInquiryList);
            }

            @Override
            public void onError(String errorMessage) {

            }
        };
        return askInquiryResponseCallback;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onDisplayProductInquiries(List<ProductInquiry> productInquiryList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        QuestionAnswerAdapter questionAnswerAdapter = new QuestionAnswerAdapter(getApplicationContext(), productInquiryList);
        recyclerView.setAdapter(questionAnswerAdapter);
    }

}
