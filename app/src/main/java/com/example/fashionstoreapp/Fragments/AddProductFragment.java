package com.example.fashionstoreapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.fashionstoreapp.CallBacks.ResponseCallback;
import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.DTO.Responses.MessageResponse;
import com.example.fashionstoreapp.Models.Product;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitAPIService.ProductService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.FragmentAddProductBinding;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends Fragment implements ResponseCallback {

    FragmentAddProductBinding fragmentAddProductBinding;
    ProductService productService;
    LoginResponse loginResponse;

    public AddProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        fragmentAddProductBinding = FragmentAddProductBinding.inflate(getLayoutInflater());
        View view = fragmentAddProductBinding.getRoot();
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).getUser();
        productService = new ProductService();
        fragmentAddProductBinding.addProductAddButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct();
            }
        });
        fragmentAddProductBinding.addProductCancelButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentAddProductBinding.addProductNameEditTextId.setText(" ");
                fragmentAddProductBinding.addProductDescriptionEditTextId.setText(" ");
                fragmentAddProductBinding.addProductQuantityValueEditTextId.setText(" ");
                fragmentAddProductBinding.addProductQuantityValueEditTextId.setText(" ");
                fragmentAddProductBinding.addProductImageURLEditTextId.setText(" ");
            }
        });
        return view;
    }

    public void saveProduct() {
        String productName = fragmentAddProductBinding.addProductNameEditTextId.getText().toString();
        String description = fragmentAddProductBinding.addProductDescriptionEditTextId.getText().toString();
        int quantity = Integer.valueOf(fragmentAddProductBinding.addProductQuantityValueEditTextId.getText().toString());
        double price = Double.parseDouble(fragmentAddProductBinding.addProductQuantityValueEditTextId.getText().toString());
        String imageUrl = fragmentAddProductBinding.addProductImageURLEditTextId.getText().toString();
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(fragmentAddProductBinding.addProductImageViewId);

        Product product = new Product(productName, description, price, quantity, imageUrl);
        productService.addProduct(product, "Bearer " + loginResponse.getToken(), this);

    }

    @Override
    public void onSuccess(Response response) {
        if (response != null) {
            MessageResponse messageResponse = (MessageResponse) response.body();
            fragmentAddProductBinding.addProductNameEditTextId.setText(" ");
            fragmentAddProductBinding.addProductDescriptionEditTextId.setText(" ");
            fragmentAddProductBinding.addProductQuantityValueEditTextId.setText(" ");
            fragmentAddProductBinding.addProductQuantityValueEditTextId.setText(" ");
            fragmentAddProductBinding.addProductImageURLEditTextId.setText(" ");
            FancyToast.makeText(getContext(), messageResponse.getMessageResponse(), Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

        }
    }

    @Override
    public void onError(String errorMessage) {
        FancyToast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

    }
}
