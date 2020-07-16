package com.example.fashionstoreapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fashionstoreapp.CallBacks.ItemClickCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Interface.OrderInterface;
import com.example.fashionstoreapp.Models.Orders;
import com.example.fashionstoreapp.RetrofitAPIService.OrderService;
import com.example.fashionstoreapp.databinding.OrderItemBinding;

import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context context;
    private OrderItemBinding orderItemBinding;
    private List<Orders> orderList;
    LoginResponse loginResponse;
    private OrderService orderService;
    private ItemClickCallback itemClickCallback;
    private OrderInterface orderInterface;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewOrderNo;
        TextView textViewOrderStatus;
        TextView textViewOrderDate;
        TextView textViewOrderTotal;
        CardView cardViewOrderItem;

        public ViewHolder(OrderItemBinding itemBinding) {
            super(itemBinding.getRoot());
            textViewOrderNo = itemBinding.orderNoTextviewId;
            textViewOrderStatus = itemBinding.orderStatusTextviewId;
            textViewOrderDate = itemBinding.orderDateTextviewId;
            textViewOrderTotal = itemBinding.orderTotalViewId;
            cardViewOrderItem = itemBinding.orderCardviewId;
        }
    }

    public OrderAdapter(ItemClickCallback itemClickCallback, OrderInterface orderInterface) {
        this.orderInterface = orderInterface;
    }

    public void setAllPendingProductData(List<Orders> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        orderItemBinding = OrderItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(orderItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Orders order = orderList.get(position);
        System.out.println("orderslist: "+orderList);
        System.out.println("order:" +order);
        for (Orders o : orderList) {
            holder.textViewOrderNo.setText(o.getOrdersId().toString());
            holder.textViewOrderStatus.setText(o.getStatus());
            holder.textViewOrderDate.setText(o.getDate());
            holder.textViewOrderTotal.setText("USD $" + order.getTotal());
            holder.cardViewOrderItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderInterface.onDetailsFragment(order);
                }
            });
        }
//
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
