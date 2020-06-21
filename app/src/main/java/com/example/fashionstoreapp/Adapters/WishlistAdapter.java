package com.example.fashionstoreapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionstoreapp.Activities.ProductDetailActivity;
import com.example.fashionstoreapp.CallBacks.ItemClickCallback;
import com.example.fashionstoreapp.Models.Product;
import com.example.fashionstoreapp.Models.Wishlist;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.databinding.WishlistItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    WishlistItemBinding wishlistItemBinding;
    Context context;
    List<Wishlist> wishlists;
    private ItemClickCallback itemClickCallback;


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewName;
        TextView textViewPrice;
        TextView textViewDescription;
        RatingBar ratingBar;
        CardView cardView;
        ImageButton deleteFavourite;
        ImageButton addToCart;

        public ViewHolder(WishlistItemBinding itemBinding) {
            super(itemBinding.getRoot());
            imageView = itemBinding.wishlistImageviewId;
            textViewName = itemBinding.wishlistTextNameId;
            textViewPrice = itemBinding.wishlistTextPriceId;
            textViewDescription = itemBinding.wishlistTextDescriptionId;
            ratingBar = itemBinding.wishlistRatingId;
            cardView = itemBinding.wishlistCardviewId;
            deleteFavourite = itemBinding.wishlistDeleteBtnId;
            addToCart = itemBinding.wishlistAddCartBtnId;
        }
    }

    public WishlistAdapter(ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public void setAllWishlistProductData(List<Wishlist> wishlists) {
        this.wishlists = wishlists;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        wishlistItemBinding = WishlistItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(wishlistItemBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Wishlist wishlist = wishlists.get(position);
        final Product product = wishlist.getProduct();
        System.out.println("wishlist adapter product name: "+product.getProductName());
        System.out.println("wishlist adapter product price: "+product.getPrice());
        System.out.println("wishlist adapter product desc: "+product.getShortDescription());
        Picasso.get()
                .load(product.getScaledImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imageView);
        holder.textViewName.setText(product.getProductName());
        holder.textViewPrice.setText("USD $ " + String.valueOf(product.getPrice()));
        holder.textViewDescription.setText(product.getShortDescription());
        holder.ratingBar.getNumStars();
        holder.deleteFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Should Remove from your wishlist", Toast.LENGTH_LONG).show();
            }
        });
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "Adding to cart work", Toast.LENGTH_LONG).show();
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("productId", product.getProductId());
                context.startActivity(intent);
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("productId", product.getProductId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wishlists.size();
    }
}
