package com.example.fashionstoreapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.fashionstoreapp.Activities.ProductDetailActivity;
import com.example.fashionstoreapp.CallBacks.ItemClickCallback;
import com.example.fashionstoreapp.CallBacks.ItemSizeClickCallback;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Interface.CartInterface;
import com.example.fashionstoreapp.Models.Cart;
import com.example.fashionstoreapp.Models.Product;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitAPIService.CartService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.CartItemBinding;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>
        implements ItemSizeClickCallback, ResponseCallback {

    CartItemBinding cartItemBinding;
    Context context;
    List<Cart> cartList;
    LoginResponse loginResponse;
    private CartService cartService;
    private ItemClickCallback itemClickCallback;
    private CartInterface cartInterface;
//    private ItemQuantityClickCallback itemQuantityClickCallback;


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView textViewName;
        TextView textViewPrice;
        TextView textViewDescription;
        TextView textViewSize;
        ImageButton imageButtonDelete;
        TextView textViewTotalPrice;
        ElegantNumberButton elegantNumberButtonQuantity;

        public ViewHolder(CartItemBinding itemView) {
            super(itemView.getRoot());
            cardView = itemView.cartCardviewId;
            imageView = itemView.cartImageviewId;
            textViewName = itemView.cartTextNameId;
            textViewPrice = itemView.cartTextPriceAmountId;
            textViewDescription = itemView.cartTextDescriptionId;
            textViewSize = itemView.cartTextSizeValueId;
            imageButtonDelete = itemView.cartDeleteBtnId;
            textViewTotalPrice = itemView.itemTotalPriceTvId;
            elegantNumberButtonQuantity = itemView.itemQtyId;

        }
    }


    public CartAdapter(ItemClickCallback itemClickCallback, CartInterface cartInterface) {
        this.itemClickCallback = itemClickCallback;
        this.cartInterface = cartInterface;
//        this.itemQuantityClickCallback = itemQuantityClickCallback;
    }


    public void setAllCartProductData(List<Cart> cartList) {
        this.cartList = cartList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        cartItemBinding = CartItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(cartItemBinding);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Cart cart = cartList.get(position);
        final Product product = cart.getProduct();
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(context).getUser();
        cartService = new CartService();
        Picasso.get()
                .load(product.getScaledImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imageView);
        holder.textViewName.setText(product.getProductName());
        holder.textViewDescription.setText(product.getShortDescription());
        holder.textViewSize.setText(cart.getSize());
        holder.textViewPrice.setText(String.valueOf(product.getPrice()));
        double price = product.getPrice();
        int quantity = cart.getQuantity();
        double total = price * quantity;
        holder.textViewTotalPrice.setText(String.valueOf(total));
        holder.elegantNumberButtonQuantity.setNumber(String.valueOf(cart.getQuantity()));

        holder.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemSizeClickListener(product.getProductId(), cart.getSize());
                cartList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartList.size());
                cartInterface.setUpdateTotal(calculateTotal());
                FancyToast.makeText(context, "Removed from your cart", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

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
        holder.elegantNumberButtonQuantity.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                double price = product.getPrice();
                int quantity = Integer.parseInt(holder.elegantNumberButtonQuantity.getNumber());
                double total = price * quantity;
                cart.setQuantity(quantity);
                cart.setTotal(total);
//                onItemQuantityClickListener(cart.getCartId(), quantity, total);
                onUpdateCart(cart.getCartId(),quantity, total, loginResponse);
                holder.textViewTotalPrice.setText(String.valueOf(total));
                cartInterface.setUpdateTotal(calculateTotal());
            }
        });
    }
    public void onUpdateCart(Integer cartId, int quantity, Double total, LoginResponse loginResponse) {
        cartService.onUpdateQuantityProductCart(cartId, quantity, total, "Bearer " + loginResponse.getToken(),this);
    }

    public double calculateTotal() {
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(context).getUser();
        double totalPrice = 0;
        for (Cart cartProduct : cartList) {
            totalPrice += (cartProduct.getQuantity() * cartProduct.getProduct().getPrice());
        }
        return totalPrice;
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    @Override
    public void onItemSizeClickListener(Integer id, String size) {
        cartService.onDeleteProductCart(id, size, "Bearer " + loginResponse.getToken(), this);
    }

    @Override
    public void onSuccess(Response response) {
        notifyDataSetChanged();
        System.out.println("working here");
    }

    @Override
    public void onError(String errorMessage) {

    }

    public interface ShoppingCartInterface {
        void setTextView_Price(double total);
    }
}
