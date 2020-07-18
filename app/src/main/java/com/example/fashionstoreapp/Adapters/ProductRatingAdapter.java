package com.example.fashionstoreapp.Adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionstoreapp.Activities.RatingActivity;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Interface.SharedService;
import com.example.fashionstoreapp.Models.CartOrders;
import com.example.fashionstoreapp.Models.Product;
import com.example.fashionstoreapp.Models.RateReview;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitAPIService.CartOrdersService;
import com.example.fashionstoreapp.databinding.ProductRateItemBinding;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductRatingAdapter extends RecyclerView.Adapter<ProductRatingAdapter.ViewHolder> implements SharedService {

    private ProductRateItemBinding productRateItemBinding;
    private Context context;
    private List<CartOrders> cartOrdersList = new ArrayList<>();
    private List<RateReview> rateList = new ArrayList<>();
    private CartOrders cartOrders;
    private LoginResponse loginResponse;
    private CartOrdersService cartOrdersService;
    Product product;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewProductName;
        TextView textViewProductSize;
        TextView textViewRateHere;
        CardView cardView;

        public ViewHolder(ProductRateItemBinding itemBinding) {
            super(itemBinding.getRoot());
            imageView = itemBinding.productRateImageviewId;
            textViewProductName = itemBinding.productRateNameId;
            textViewProductSize = itemBinding.productRateSizeId;
            textViewRateHere = itemBinding.productsRateHereRating;
            cardView = itemBinding.productRateCardviewId;
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

        onCalculateRatingAverage();

    }

    @Override
    public void onCalculateRatingAverage() {
        DecimalFormat df2 = new DecimalFormat("0.0");
        double totalRate = 0;
        double average = 0;
//        List<RateReview> rateList = RateReview.find(RateReview.class, "product=?", product.getId().toString());
        for (RateReview rateReview : rateList) {
            totalRate += rateReview.getRate();
            average = totalRate / rateList.size();
        }
        productRateItemBinding.includeProductRateItemFeedbackId.feedbackRatingbarId.setRating((float) average);
        productRateItemBinding.includeProductRateItemFeedbackId.feedbackRateValueId.setText(df2.format(average));
    }

    @Override
    public int getItemCount() {
        return cartOrdersList.size();
    }
}
