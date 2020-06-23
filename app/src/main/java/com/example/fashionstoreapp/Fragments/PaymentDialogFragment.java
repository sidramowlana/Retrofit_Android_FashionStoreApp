package com.example.fashionstoreapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.FragmentPaymentDialogBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentDialogFragment extends DialogFragment {

    FragmentPaymentDialogBinding fragmentPaymentDialogBinding;
    private LoginResponse loginResponse;

    public PaymentDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentPaymentDialogBinding = FragmentPaymentDialogBinding.inflate(getLayoutInflater());
        View view = fragmentPaymentDialogBinding.getRoot();
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).getUser();
        setUserData(loginResponse);
        double cartTotal = getArguments().getDouble("TOTAL_AMOUNT_KEY");
        fragmentPaymentDialogBinding.paymentDialogTotalAmountTextViewId.setText(String.valueOf(cartTotal));

        fragmentPaymentDialogBinding.paymentDialogCloseBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCloseDialog();
            }
        });

        fragmentPaymentDialogBinding.paymentDialogPayBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPayment();
            }
        });
        return view;
    }

    public void onCloseDialog() {
        getDialog().dismiss();
    }

    public void setUserData(LoginResponse user) {
//        fragmentPaymentDialogBinding.paymentDialogNameEditViewId.setText(user.getName());
//        fragmentPaymentDialogBinding.paymentDialogEmailEditViewId.setText(user.getEmail());
//        fragmentPaymentDialogBinding.paymentDialogAddressEditViewId.setText(user.getAddress());
    }

    public void onPayment() {
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).getUser();
//        List<Cart> cartList = Cart.find(Cart.class, "user = ? and is_purchased=?", user.getId().toString(), "0");
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//        String date = sdf.format(new Date());
//        Orders order = new Orders(date, "Pending", user, getArguments().getDouble("TOTAL_AMOUNT_KEY"));
//        order.save();
//        for (Cart cart : cartList) {
//            Product product = Product.findById(Product.class, cart.getProduct().getId());
//            product.setQuantity(product.getQuantity() - cart.getQuantity());
//            product.save();
//            cart.setPurchased(true);
//            cart.save();
//            ProductOrders productOrders = new ProductOrders(cart, order);
//            productOrders.save();
//        }
//        getDialog().dismiss();
//        NavigationView navigationView = getActivity().findViewById(R.id.navigationView);
//        ((MainActivity) getActivity()).onNavigationItemSelected(navigationView.getMenu().findItem(R.id.navOrders));
//        navigationView.setCheckedItem(R.id.navOrders);
        Toast.makeText(getActivity(), "Checked out successfully.", Toast.LENGTH_SHORT).show();
    }
}
