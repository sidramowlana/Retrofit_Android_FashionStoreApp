package com.example.fashionstoreapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionstoreapp.Activities.CategoryProductActivity;
import com.example.fashionstoreapp.Models.Tag;
import com.example.fashionstoreapp.databinding.CategoryItemBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private CategoryItemBinding categoryItemBinding;
    private Context context;
    private List<Tag> tagsList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCategoyrName;
        CardView cardViewCategoryItem;

        public ViewHolder(CategoryItemBinding itemBinding) {
            super(itemBinding.getRoot());
            textViewCategoyrName = itemBinding.categoryItemTextId;
            cardViewCategoryItem = itemBinding.categoryItemCardId;
        }
    }

    public CategoryAdapter(Context context, List<Tag> tagsList) {
        this.context = context;
        this.tagsList = tagsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        categoryItemBinding = CategoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(categoryItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Tag tag = tagsList.get(position);
        System.out.println("the tagis: "+tag);
        holder.textViewCategoyrName.setText(tag.getTag());
        holder.cardViewCategoryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("move to the product tags");
                Intent intent = new Intent(context, CategoryProductActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("productTagName", tag.getTag());
                intent.putExtra("productTagId", tag.getTagId());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return tagsList.size();
    }
}
