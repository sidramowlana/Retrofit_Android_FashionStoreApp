package com.example.fashionstoreapp.Adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionstoreapp.Activities.ProductFeedbackActivity;
import com.example.fashionstoreapp.Activities.RatingActivity;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Interface.SharedService;
import com.example.fashionstoreapp.Models.CartOrders;
import com.example.fashionstoreapp.Models.RateReview;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitAPIService.CartOrdersService;
import com.example.fashionstoreapp.RetrofitAPIService.RateReviewService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.DetailProductFeedbackLayoutBinding;
import com.example.fashionstoreapp.databinding.ProductRateItemBinding;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class ProductRatingAdapter extends RecyclerView.Adapter<ProductRatingAdapter.ViewHolder> implements SharedService {

    private ProductRateItemBinding productRateItemBinding;
    private Context context;
    private List<CartOrders> cartOrdersList = new ArrayList<>();
    private List<RateReview> rateReviewList = new ArrayList<>();
    private CartOrders cartOrders;
    private LoginResponse loginResponse;
    private CartOrdersService cartOrdersService;
    int reviewCount;
    private RateReviewService rateReviewService;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewProductName;
        TextView textViewProductSize;
        TextView textViewRateHere;
        CardView cardView;
        LinearLayout linearLayout;
        DetailProductFeedbackLayoutBinding detailProductFeedbackLayoutBinding;
        TextView textViewRateValue;
        RatingBar ratingBar;


        public ViewHolder(ProductRateItemBinding itemBinding) {
            super(itemBinding.getRoot());
            imageView = itemBinding.productRateImageviewId;
            textViewProductName = itemBinding.productRateNameId;
            textViewProductSize = itemBinding.productRateSizeId;
            textViewRateHere = itemBinding.productsRateHereRating;
            cardView = itemBinding.productRateCardviewId;
            detailProductFeedbackLayoutBinding = itemBinding.includeProductRateItemFeedbackId;
            linearLayout = itemBinding.linearLayoutIncludeProductRateId;
//            ratingBar = itemBinding.includeProductRateItemFeedbackId.feedbackRatingbarId;
        }

    }

    public ProductRatingAdapter(Context context, List<CartOrders> cartOrdersList) {
        this.context = context;
        this.cartOrdersList = cartOrdersList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        productRateItemBinding = ProductRateItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(productRateItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRatingAdapter.ViewHolder holder, int position) {
        cartOrders = cartOrdersList.get(position);
        rateReviewService = new RateReviewService();
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(context).getUser();
        onViewRates(holder,cartOrders.getCart().getProduct().getProductId(),loginResponse);
//        reviewCount = rateReviewList.size();
        Picasso.get()
                .load(cartOrders.getCart().getProduct().getScaledImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imageView);
        holder.textViewProductName.setText(cartOrders.getCart().getProduct().getProductName());
        holder.textViewProductSize.setText(cartOrders.getCart().getSize());
                holder.textViewRateHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RatingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("rateProductId", cartOrders.getCart().getProduct().getProductId());
                context.startActivity(intent);
            }
        });
    }

    public void onViewRates(ViewHolder holder,Integer productId,LoginResponse loginResponse) {
        rateReviewService.getRateReviewByProductId(productId, "Bearer " + loginResponse.getToken(), rateReview(holder,productId));
    }
    public ResponseCallback rateReview(final ViewHolder holder, final Integer productId) {
        ResponseCallback rateReviewProductCallback = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                rateReviewList = (List<RateReview>) response.body();
                reviewCount = rateReviewList.size();
                holder.detailProductFeedbackLayoutBinding.feedbackReviewCountId.setText("( " + reviewCount + " )");
                onCalculateRatingAverage();
                holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rateReviewList.isEmpty()) {
                            FancyToast.makeText(context, "No Reviews available", Toast.LENGTH_SHORT, FancyToast.INFO, false);
                        } else {
                            Intent intent = new Intent(context, ProductFeedbackActivity.class);
                            intent.putExtra("productFeedbackId", productId);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
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


    @Override
    public void onCalculateRatingAverage() {
        DecimalFormat df2 = new DecimalFormat("0.0");
        double totalRate = 0;
        double average = 0;
        for (RateReview rateReview : rateReviewList) {
            totalRate += rateReview.getRate();
            average = totalRate / rateReviewList.size();
        }
        productRateItemBinding.includeProductRateItemFeedbackId.feedbackRatingbarId.setRating((float) average);
        productRateItemBinding.includeProductRateItemFeedbackId.feedbackRateValueId.setText(df2.format(average));
    }

    @Override
    public int getItemCount() {
        return cartOrdersList.size();
    }
}
