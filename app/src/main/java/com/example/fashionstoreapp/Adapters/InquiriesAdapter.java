package com.example.fashionstoreapp.Adapters;

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

import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Models.ProductInquiry;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitAPIService.ProductInquiryService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.InquiriesItemBinding;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Response;

public class InquiriesAdapter extends RecyclerView.Adapter<InquiriesAdapter.ViewHolder> {

    private Context context;
    private InquiriesItemBinding inquiriesItemBinding;
    private List<ProductInquiry> productInquiryList;
    private LoginResponse loginResponse;
    private ProductInquiryService productInquiryService;
    AnsweredInquiriesAdapter answeredInquiriesAdapter;

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

    public InquiriesAdapter(Context context, List<ProductInquiry> productInquiryList) {
        this.context = context;
        this.productInquiryList = productInquiryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inquiriesItemBinding = InquiriesItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(context).getUser();
        productInquiryService = new ProductInquiryService();
        answeredInquiriesAdapter = new AnsweredInquiriesAdapter();
        return new ViewHolder(inquiriesItemBinding);
    }

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

        holder.textViewReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textViewCancel.setVisibility(View.VISIBLE);
                holder.textViewReply.setVisibility(View.GONE);
                holder.editTextReply.setVisibility(View.VISIBLE);
                holder.buttonSend.setVisibility(View.VISIBLE);
            }
        });
        holder.textViewReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textViewCancel.setVisibility(View.VISIBLE);
                holder.textViewReply.setVisibility(View.GONE);
                holder.editTextReply.setVisibility(View.VISIBLE);
                holder.buttonSend.setVisibility(View.VISIBLE);
            }
        });
        holder.buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reply = holder.editTextReply.getText().toString();
                if (reply.isEmpty()) {
                    FancyToast.makeText(context, "Reply is empty", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                } else {
                    ProductInquiry answerProductInquiry = new ProductInquiry(reply, true);
                    productInquiryService.onAddAnswerByProductInquiryId(productInquiry.getProductInquiryId(), answerProductInquiry, "Bearer " + loginResponse.getToken(), removeUpdateInquiryResponseCallback());
                    productInquiryList.remove(position);
                    onCancel(holder);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, productInquiryList.size());
                }
            }
        });
        holder.textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancel(holder);
            }
        });
    }

    public void onCancel(ViewHolder holder) {
        holder.editTextReply.setVisibility(View.GONE);
        holder.buttonSend.setVisibility(View.GONE);
        holder.textViewCancel.setVisibility(View.GONE);
        holder.textViewReply.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return productInquiryList.size();
    }


    public ResponseCallback removeUpdateInquiryResponseCallback() {
        ResponseCallback updateInquiryResponseCallback = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                FancyToast.makeText(context, "Replied to the inquiry", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
            }

            @Override
            public void onError(String errorMessage) {
                FancyToast.makeText(context, errorMessage, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        };
        return updateInquiryResponseCallback;
    }

}