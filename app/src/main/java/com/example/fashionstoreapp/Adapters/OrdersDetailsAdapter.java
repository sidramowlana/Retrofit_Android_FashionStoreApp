package com.example.fashionstoreapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionstoreapp.Models.CartOrders;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.databinding.OrderDetailItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrdersDetailsAdapter extends RecyclerView.Adapter<OrdersDetailsAdapter.ViewHolder> {
    private Context context;
    private OrderDetailItemBinding orderDetailItemBinding;
    List<CartOrders> cartOrdersList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewProductName;
        TextView textViewPrice;
        TextView textViewQuantity;
        TextView textViewTotalAmount;
        ImageView imageView;

        public ViewHolder(OrderDetailItemBinding itemBinding) {
            super(itemBinding.getRoot());
            textViewProductName = itemBinding.purchasedProductNameId;
            textViewPrice = itemBinding.purchasedProductPriceId;
            textViewQuantity = itemBinding.purchasedProductQuantityId;
            textViewTotalAmount = itemBinding.purchasedTotalId;
            imageView = itemBinding.orderDetailImageviewId;
        }
    }

    public OrdersDetailsAdapter(Context context, List<CartOrders> cartOrdersList) {
        this.context = context;
        this.cartOrdersList = cartOrdersList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        orderDetailItemBinding = OrderDetailItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(orderDetailItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartOrders cartOrders = cartOrdersList.get(position);
        Picasso.get()
                .load(cartOrders.getCart().getProduct().getScaledImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imageView);
        holder.textViewProductName.setText(cartOrders.getCart().getProduct().getProductName());
        holder.textViewPrice.setText(String.valueOf(cartOrders.getCart().getProduct().getPrice()));
        holder.textViewQuantity.setText(String.valueOf(cartOrders.getCart().getQuantity()));
        holder.textViewTotalAmount.setText("USD $" + String.valueOf(cartOrders.getOrders().getTotal()));
    }

    @Override
    public int getItemCount() {
        return cartOrdersList.size();
    }
}
