package com.example.fashionstoreapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionstoreapp.Models.Product;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.databinding.HomeItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    Context context;
    HomeItemBinding homeItemBinding;
    List<Product> productList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewProduct;
        TextView textViewPrice;
        CardView cardView;

        public ViewHolder(HomeItemBinding itemBinding) {
            super(itemBinding.getRoot());
            imageView = itemBinding.itemImageViewId;
            textViewProduct = itemBinding.itemTextViewName;
            textViewPrice = itemBinding.itemTextViewPrice;
            cardView = itemBinding.cardViewId;
        }
    }


    public HomeAdapter(Context context, List<Product> products) {
        this.context = context;
        this.productList = products;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        homeItemBinding = HomeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(homeItemBinding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Product product = productList.get(position);
        Picasso.get()
                .load(product.getScaledImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imageView);
        holder.textViewProduct.setText(product.getName());
        holder.textViewPrice.setText("USD $"+ String.valueOf(product.getPrice()));

//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, ProductDetailActivity.class);
//                intent.putExtra("productId",product.getId());
//                context.startActivity(intent);
//            }
//        });
//
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, ProductDetailActivity.class);
//                intent.putExtra("productId",product.getId());
//                context.startActivity(intent);
//         }
//        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
