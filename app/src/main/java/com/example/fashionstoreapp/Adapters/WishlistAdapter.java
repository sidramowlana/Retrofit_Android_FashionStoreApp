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
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Models.Product;
import com.example.fashionstoreapp.Models.Wishlist;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitAPIService.ProductService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.WishlistItemBinding;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Response;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder>
        implements ItemClickCallback, ResponseCallback {

    WishlistItemBinding wishlistItemBinding;
    Context context;
    List<Wishlist> wishlists;
    LoginResponse loginResponse;
    private ProductService productService;
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
        System.out.println("wishy: "+wishlist);
        final Product product = wishlist.getProduct();
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(context).getUser();
        productService = new ProductService();

        Picasso.get()
                .load(product.getScaledImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imageView);
        holder.textViewName.setText(product.getProductName());
        holder.textViewPrice.setText("USD $ " + String.valueOf(product.getPrice()));
        holder.textViewDescription.setText(product.getShortDescription());
        holder.ratingBar.getNumStars();
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
        holder.deleteFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishlists.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, wishlists.size());
                onItemClickListener(product.getProductId());
                FancyToast.makeText(context, "Removed from your WishList", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
            }
        });
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FancyToast.makeText(context, "Add to cart no code still", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return wishlists.size();
    }

    public void onAddRemoveProductWishlist(Integer productId, LoginResponse loginResponse, ResponseCallback productResponseCallback) {
        productService.onAddRemoveProductFavourite(productId, "Bearer " + loginResponse.getToken(), productResponseCallback);
    }
    @Override
    public void onItemClickListener(Integer id) {
        onAddRemoveProductWishlist(id, loginResponse, this);
    }

    @Override
    public void onSuccess(Response response) {
        System.out.println("deleted from your wishlist");
        notifyDataSetChanged();
    }

    @Override
    public void onError(String errorMessage) {
        System.out.println("error in ur deleting wishlist product from the list");
    }


}

