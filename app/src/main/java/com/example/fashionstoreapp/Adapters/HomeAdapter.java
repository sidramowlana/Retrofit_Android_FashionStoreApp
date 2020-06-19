package com.example.fashionstoreapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionstoreapp.CallBacks.ItemClickCallback;
import com.example.fashionstoreapp.Models.Product;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.databinding.HomeItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    HomeItemBinding homeItemBinding;
    Context context;
    List<Product> productList;
    private ItemClickCallback itemClickCallback;

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


    public HomeAdapter(ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public void setAllProductData(List<Product> productsList) {
        this.productList = productsList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        homeItemBinding = HomeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(homeItemBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Product product = productList.get(position);
        Picasso.get()
                .load(product.getScaledImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imageView);
        holder.textViewProduct.setText(product.getProductName());
        holder.textViewPrice.setText("USD $" + String.valueOf(product.getPrice()));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("homeadapter product positio: " + product.getProductId());
                itemClickCallback.onItemClickListener(product.getProductId());
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickCallback.onItemClickListener(product.getProductId());
            }
        });
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
