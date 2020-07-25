package com.example.fashionstoreapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Models.ProductInquiry;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitAPIService.ProductInquiryService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.InquiriesItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnsweredInquiriesAdapter extends RecyclerView.Adapter<AnsweredInquiriesAdapter.ViewHolder> {

    private Context context;
    private InquiriesItemBinding inquiriesItemBinding;
    private List<ProductInquiry> productInquiryList;
    private LoginResponse loginResponse;
    private ProductInquiryService productInquiryService;


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewDate;
        TextView textViewUsername;
        TextView textViewProductName;
        TextView textViewQuestion;
        TextView textViewReply;
        TextView textViewCancel;
        EditText editTextReply;
        ImageButton buttonSend;


        public ViewHolder(InquiriesItemBinding itemBinding) {
            super(itemBinding.getRoot());
            imageView = itemBinding.adminInquiryImageId;
            textViewDate = itemBinding.adminInquiryItemDateandtimeId;
            textViewUsername = itemBinding.adminInquiryItemUsernameId;
            textViewQuestion = itemBinding.adminInquiryItemQuestionId;
            textViewProductName = itemBinding.adminInquiryItemProductnameId;
            textViewReply = itemBinding.adminInquiryItemReplyId;
            textViewCancel = itemBinding.adminInquiryItemCancelId;
            editTextReply = itemBinding.adminInquiryItemReplyEditTextId;
            buttonSend = itemBinding.adminInquirySendButtonId;
        }
    }

    public AnsweredInquiriesAdapter(Context context, List<ProductInquiry> productInquiryList) {
        this.context = context;
        this.productInquiryList = productInquiryList;
    }

    public AnsweredInquiriesAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inquiriesItemBinding = InquiriesItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(context).getUser();
        productInquiryService = new ProductInquiryService();
        return new ViewHolder(inquiriesItemBinding);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final ProductInquiry productInquiry = productInquiryList.get(position);
        Picasso.get()
                .load(productInquiry.getProduct().getScaledImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imageView);
        holder.textViewDate.setText(productInquiry.getDate());
        holder.textViewUsername.setText(productInquiry.getUser().getUsername());
        holder.textViewQuestion.setText(productInquiry.getQuestion());
        holder.textViewProductName.setText(productInquiry.getProduct().getProductName());
        holder.textViewReply.setVisibility(View.GONE);
        holder.editTextReply.setVisibility(View.VISIBLE);
        holder.editTextReply.setEnabled(false);
        holder.editTextReply.setText(productInquiry.getAnswers());
    }

    @Override
    public int getItemCount() {
        return productInquiryList.size();
    }
}