package com.example.fashionstoreapp.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.fashionstoreapp.Activities.MainActivity;
import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Requests.AddressRequest;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Models.Cart;
import com.example.fashionstoreapp.Models.CartOrders;
import com.example.fashionstoreapp.Models.Orders;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitAPIService.CartService;
import com.example.fashionstoreapp.RetrofitAPIService.OrderService;
import com.example.fashionstoreapp.RetrofitAPIService.ProductService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.FragmentPaymentDialogBinding;
import com.google.android.material.navigation.NavigationView;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentDialogFragment extends DialogFragment implements ResponseCallback {

    public static String ADDRESS = "com.example.fashionstoreapp.ADDRESS";
    private SharedPreferences sharedPreferences;
    FragmentPaymentDialogBinding fragmentPaymentDialogBinding;
    private LoginResponse loginResponse;
    private AddressRequest addressRequest;
    private OrderService orderService;
    private ProductService productService;
    private CartService cartService;
    List<Cart> cartList = new ArrayList<>();
    double cartTotal;
    ResponseCallback cartListCallback;
    ResponseCallback saveCallback;
    private Orders savedOrder = new Orders();

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
        orderService = new OrderService();
        cartService = new CartService();

        cartTotal = getArguments().getDouble("TOTAL_AMOUNT_KEY");
        System.out.println("total is: " + cartTotal);
        fragmentPaymentDialogBinding.paymentDialogTotalAmountTextViewId.setText(String.valueOf(cartTotal));
        fragmentPaymentDialogBinding.paymentDialogCloseBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCloseDialog();
            }
        });
        fragmentPaymentDialogBinding.paymentDialogRememberAddressCheckboxId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveAddressSharedPref();
            }
        });
        fragmentPaymentDialogBinding.paymentDialogPayBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPayment();
            }
        });
        cartListCallback = new ResponseCallback() {

            @Override
            public void onSuccess(Response response) {
                if (response != null) {
                    NavigationView navigationView = getActivity().findViewById(R.id.navigationView);
                    ((MainActivity) getActivity()).onNavigationItemSelected(navigationView.getMenu().findItem(R.id.navOrders));
                    navigationView.setCheckedItem(R.id.navOrders);
                    FancyToast.makeText(getContext(), "Checked out successfully.", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                }
            }

            @Override
            public void onError(String errorMessage) {
                FancyToast.makeText(getActivity(), "Check your internet connection for the interruption.", Toast.LENGTH_SHORT).show();
            }
        };
        saveCallback = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                cartList = (List<Cart>) response.body();
                for (Cart cart : cartList) {
                    CartOrders cartOrders = new CartOrders(cart, savedOrder);
                    orderService.addCartOrders(cartOrders, "Bearer " + loginResponse.getToken(), cartListCallback);
                }
            }

            @Override
            public void onError(String errorMessage) {
                System.out.println("not working save");
            }
        };
        return view;
    }

    public void onCloseDialog() {
        getDialog().dismiss();
    }

    public void setUserData(LoginResponse user) {
        fragmentPaymentDialogBinding.paymentDialogNameEditViewId.setText(user.getUsername());
        fragmentPaymentDialogBinding.paymentDialogEmailEditViewId.setText(user.getEmail());
    }

    public void onSaveAddressSharedPref() {
        String postalCode = fragmentPaymentDialogBinding.paymentDialogPostalEditViewId.getText().toString();
        String city = fragmentPaymentDialogBinding.paymentDialogCityEditViewId.getText().toString();
        String address = fragmentPaymentDialogBinding.paymentDialogAddressEditViewId.getText().toString();
        AddressRequest addressRequest = new AddressRequest(postalCode, city, address);
        if (fragmentPaymentDialogBinding.paymentDialogRememberAddressCheckboxId.isChecked()) {
            if (postalCode.isEmpty() || city.isEmpty() || address.isEmpty()) {
                fragmentPaymentDialogBinding.paymentDialogRememberAddressCheckboxId.setChecked(false);
                FancyToast.makeText(getContext(), "Shipping address is empty", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
            } else {
                fragmentPaymentDialogBinding.paymentDialogRememberAddressCheckboxId.setChecked(true);
//                        onSaveRemoveAddress();
            }
        } else if (fragmentPaymentDialogBinding.paymentDialogRememberAddressCheckboxId.isChecked() == false) {
            System.out.println("not checked willl save to adress db");
//                    onSaveRemoveAddress();
        }


    }

    public void onPayment() {
        String username = fragmentPaymentDialogBinding.paymentDialogNameEditViewId.getText().toString();
        String email = fragmentPaymentDialogBinding.paymentDialogEmailEditViewId.getText().toString();
        String postalCode = fragmentPaymentDialogBinding.paymentDialogPostalEditViewId.getText().toString();
        String city = fragmentPaymentDialogBinding.paymentDialogCityEditViewId.getText().toString();
        String address = fragmentPaymentDialogBinding.paymentDialogAddressEditViewId.getText().toString();
        if (username.isEmpty() || email.isEmpty() || postalCode.isEmpty() || city.isEmpty() || address.isEmpty()) {
            FancyToast.makeText(getContext(), "Fields cannot be empty", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String date = sdf.format(new Date());
            Orders order = new Orders(date, "Pending", getArguments().getDouble("TOTAL_AMOUNT_KEY"));
            orderService.addOrder(order, "Bearer " + loginResponse.getToken(), this);
        }

    }

    @Override
    public void onSuccess(Response response) {
        savedOrder = (Orders) response.body();
        if (response.isSuccessful()) {
            cartService.getAllCartProducts("Bearer " + loginResponse.getToken(), saveCallback);
        }
    }

    @Override
    public void onError(String errorMessage) {
        System.out.println("errorMessage: " + errorMessage);
        FancyToast.makeText(getContext(), "Error: " + errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false);
    }
}