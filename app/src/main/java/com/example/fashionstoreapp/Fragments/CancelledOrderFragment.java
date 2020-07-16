package com.example.fashionstoreapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fashionstoreapp.Adapters.OrderAdapter;
import com.example.fashionstoreapp.Interface.OrderInterface;
import com.example.fashionstoreapp.Models.Orders;
import com.example.fashionstoreapp.databinding.CommonlistviewBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class CancelledOrderFragment extends Fragment implements OrderInterface {

//    FragmentCancelledOrderBinding fragmentCancelledOrderBinding;
    CommonlistviewBinding commonlistviewBinding;
    OrderAdapter orderAdapter;

    public CancelledOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        commonlistviewBinding = commonlistviewBinding.inflate(getLayoutInflater());
        View view = commonlistviewBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        User user = StoresUser.getCurrentLoggedUser(getContext());
//        List<Orders> pendingOrderList = Orders.find(Orders.class, "user = ? and status = ?", user.getId().toString(), "Cancelled");
//        orderAdapter = new OrderAdapter(getActivity(), pendingOrderList, this);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        RecyclerView recyclerView = commonlistviewBinding.commonRecycleviewId;
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(orderAdapter);
    }

    @Override
    public void onDetailsFragment(Orders order) {
        Bundle bundle = new Bundle();
//        bundle.putLong("orderId", order.getId());
//        OrderDetailFragment orderDetailFragment = new OrderDetailFragment();
//        orderDetailFragment.setArguments(bundle);
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frameLayout, orderDetailFragment);
//        fragmentTransaction.commit();
    }
}