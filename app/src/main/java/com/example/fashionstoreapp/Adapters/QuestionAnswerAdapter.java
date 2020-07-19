package com.example.fashionstoreapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionstoreapp.Models.ProductInquiry;
import com.example.fashionstoreapp.databinding.QuestionAnswerItemBinding;

import java.util.List;

public class QuestionAnswerAdapter extends RecyclerView.Adapter<QuestionAnswerAdapter.ViewHolder> {

    private Context context;
    private QuestionAnswerItemBinding questionAnswerItemBinding;
    private List<ProductInquiry> productInquiryList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUsername;
        TextView textViewDate;
        TextView textViewQuestion;
        TextView textViewAnswer;

        public ViewHolder(QuestionAnswerItemBinding itemBinding) {
            super(itemBinding.getRoot());
            textViewUsername = itemBinding.questionAnswerItemUsernameId;
            textViewDate = itemBinding.questionAnswerItemDate;
            textViewQuestion = itemBinding.questionAnswerItemQuestion;
            textViewAnswer = itemBinding.questionAnswerItemAnswer;
        }
    }

    public QuestionAnswerAdapter(Context context, List<ProductInquiry> productInquiryList) {
        this.context = context;
        this.productInquiryList = productInquiryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        questionAnswerItemBinding = QuestionAnswerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(questionAnswerItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductInquiry productInquiry = productInquiryList.get(position);
        holder.textViewUsername.setText(productInquiry.getUser().getUsername());
        holder.textViewDate.setText(productInquiry.getDate());
        holder.textViewQuestion.setText(productInquiry.getQuestion());
//        if (productInquiry.getAnswers().getAnswer() == null) {
//            holder.editTextAnswer.setText(" We will reply to you soon");
//            System.out.println("answer is null");
//        } else {
//        System.out.println(productInquiry.getAnswers().getAnswer());
////            holder.editTextAnswer.setText(productInquiry.getAnswers().getAnswer());
//        }
    }

    @Override
    public int getItemCount() {
        return productInquiryList.size();
    }
}
