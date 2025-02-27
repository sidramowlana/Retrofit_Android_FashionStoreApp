package com.example.fashionstoreapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Models.RateReview;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.FeedbackItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {
    Context context;
    FeedbackItemBinding feedbackItemBinding;
    List<RateReview> rateReviewsList;
    LoginResponse loginResponse;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewUser;
        RatingBar ratingBarValue;
        TextView textViewUsername;
        TextView textViewFeedback;
        TextView textViewDate;
        ImageButton imageButtonRemove;

        public ViewHolder(FeedbackItemBinding itemView) {
            super(itemView.getRoot());
            imageViewUser = itemView.feedbackItemImageId;
            textViewUsername = itemView.feedbackItemUsernameId;
            ratingBarValue = itemView.feedbackItemRateValueId;
            textViewFeedback = itemView.feedbackItemTextId;
            textViewDate = itemView.feedbackItemDateId;
            imageButtonRemove = itemView.feedbackItemDeleteButton;
        }
    }

    public FeedbackAdapter(Context context, List<RateReview> rateReviewsList) {
        this.context = context;
        this.rateReviewsList = rateReviewsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        feedbackItemBinding = FeedbackItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(context).getUser();
        return new ViewHolder(feedbackItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RateReview rateReview = rateReviewsList.get(position);
        if (loginResponse.getRoles().equals("ROLE_ADMIN")) {
            holder.imageButtonRemove.setEnabled(true);
            holder.textViewUsername.setText(rateReview.getUser().getUsername());
            holder.textViewFeedback.setText(rateReview.getFeedback());
            Picasso.get()
                    .load("https://upload.wikimedia.org/wikipedia/commons/thumb/7/7c/User_font_awesome.svg/1200px-User_font_awesome.svg.png")
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(holder.imageViewUser);            holder.textViewDate.setText(rateReview.getDate());
            holder.ratingBarValue.setRating(rateReview.getRate());
        } else if (loginResponse.getRoles().equals("ROLE_USER")) {
            Picasso.get()
                    .load("https://upload.wikimedia.org/wikipedia/commons/thumb/7/7c/User_font_awesome.svg/1200px-User_font_awesome.svg.png")
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(holder.imageViewUser);
            holder.textViewUsername.setText(rateReview.getUser().getUsername());
            holder.textViewFeedback.setText(rateReview.getFeedback());
            holder.textViewDate.setText(rateReview.getDate());
            holder.ratingBarValue.setRating(rateReview.getRate());
        }
    }

    @Override
    public int getItemCount() {
        return rateReviewsList.size();
    }

}
