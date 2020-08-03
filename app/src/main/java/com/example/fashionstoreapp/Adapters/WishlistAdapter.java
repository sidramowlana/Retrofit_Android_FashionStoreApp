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
import com.example.fashionstoreapp.RetrofitAPIService.CartService;
import com.example.fashionstoreapp.RetrofitAPIService.ProductService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.WishlistItemBinding;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Response;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder>
        implements ItemClickCallback, ResponseCallback {

    private WishlistItemBinding wishlistItemBinding;
    private Context context;
    private List<Wishlist> wishlists;
    private LoginResponse loginResponse;
    private ProductService productService;
    private CartService cartService;
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
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(context).getUser();
        productService = new ProductService();
        cartService = new CartService();

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
//                FancyToast.makeText(context, "Add to cart no code still", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                        if (product.getQuantity() == 0) {
                            FancyToast.makeText(context, "Out of stock", Toast.LENGTH_SHORT, FancyToast.ERROR, false);
                        } else {
                            int quantity = 1;
                            String size = "S";
                            double total = quantity * product.getPrice();
                            onAddProductCart(product.getProductId(), quantity, size, total, loginResponse, productAddCartResponseCallback());
                        }
            }
        });
    }
    public void onAddProductCart(Integer productId, Integer quantity, String size, Double total, LoginResponse loginResponse, ResponseCallback responseCallback) {
        if (size.equals("")) {
            FancyToast.makeText(context, "Please select a size", Toast.LENGTH_SHORT, FancyToast.WARNING, false).show();
        } else {
            cartService.onAddProductCart(productId, quantity, size, total, "Bearer " + loginResponse.getToken(), responseCallback);
            FancyToast.makeText(context, "Added to your cart", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
        }
    }

    public ResponseCallback productAddCartResponseCallback() {
        final ResponseCallback productAddCartResponseCallback = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                if(response!=null)
                {
                    FancyToast.makeText(context, "Successfully Added to cart", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false);
                }else{
                    FancyToast.makeText(context, "Sorry couldnt add now", Toast.LENGTH_SHORT, FancyToast.ERROR, false);

                }
            }

            @Override
            public void onError(String errorMessage) {
                if (errorMessage != null) {
                    FancyToast.makeText(context, errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false);
                }
            }
        };
        return productAddCartResponseCallback;
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

