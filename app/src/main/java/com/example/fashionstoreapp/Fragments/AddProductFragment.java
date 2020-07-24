package com.example.fashionstoreapp.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.fashionstoreapp.Models.Tag;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitAPIService.ProductService;
import com.example.fashionstoreapp.RetrofitAPIService.TagService;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.FragmentAddProductBinding;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends Fragment implements ResponseCallback {

    private FragmentAddProductBinding fragmentAddProductBinding;
    private ProductService productService;
    private LoginResponse loginResponse;
    private boolean[] checkedItems; //to hold the checked items
    private ArrayList<Integer> mSelectedUserItem = new ArrayList<>();// to store the checked items
    private List<Tag> tagsList = new ArrayList<>();
    private String[] arr;

    public AddProductFragment() {
        // Required empty public constructor
    }

    String imageUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        fragmentAddProductBinding = FragmentAddProductBinding.inflate(getLayoutInflater());
        View view = fragmentAddProductBinding.getRoot();
        loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(getContext()).getUser();
        productService = new ProductService();
        TagService tagService = new TagService();
        tagService.getAllTags("Bearer " + loginResponse.getToken(), tagsResponseCallback());

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
                fragmentAddProductBinding.addProductPriceValueEditTextId.setText(" ");
                fragmentAddProductBinding.addProductImageURLEditTextId.setText(" ");
                fragmentAddProductBinding.addProductListItemCategoriesTextViewId.setText(" ");
            }
        });
        fragmentAddProductBinding.addProductCategoryDropdownButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getViewCatergoriesList();
            }
        });
        fragmentAddProductBinding.addProductLoadButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageUrl = fragmentAddProductBinding.addProductImageURLEditTextId.getText().toString();
                if (imageUrl.isEmpty()) {
                    FancyToast.makeText(getContext(), "Please give a valid image url", Toast.LENGTH_SHORT, FancyToast.WARNING, false).show();

                } else {
                    Picasso.get()
                            .load(imageUrl)
                            .placeholder(R.drawable.loading)
                            .error(R.drawable.error)
                            .into(fragmentAddProductBinding.addProductImageViewId);

                }
            }
        });
        return view;
    }

    public void saveProduct() {
        String productName = fragmentAddProductBinding.addProductNameEditTextId.getText().toString();
        String description = fragmentAddProductBinding.addProductDescriptionEditTextId.getText().toString();
        String categories = fragmentAddProductBinding.addProductListItemCategoriesTextViewId.getText().toString();
        if (productName.isEmpty() || imageUrl.isEmpty() || categories.isEmpty() || description.isEmpty()
                || (fragmentAddProductBinding.addProductQuantityValueEditTextId.getText().toString().trim().length() == 0)
                || (fragmentAddProductBinding.addProductPriceValueEditTextId.getText().toString().trim().length() == 0)) {
            FancyToast.makeText(getContext(), "Fill the empty fields", Toast.LENGTH_SHORT, FancyToast.WARNING, false).show();
        } else {
            Integer quantity = Integer.parseInt(fragmentAddProductBinding.addProductQuantityValueEditTextId.getText().toString());
            Double price = Double.parseDouble(fragmentAddProductBinding.addProductPriceValueEditTextId.getText().toString());
            String[] catergoryArray = categories.split(",");
            Product product = new Product(productName, description, price, quantity, imageUrl, catergoryArray);
            productService.addProduct(product, "Bearer " + loginResponse.getToken(), this);
        }
    }

    public void getViewCatergoriesList() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        alertBuilder.setTitle("Select the product Categories");
        alertBuilder.setMultiChoiceItems(arr, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                if (isChecked) {
                    if (!mSelectedUserItem.contains(position)) {
                        mSelectedUserItem.add(position);
                    } else {
                        mSelectedUserItem.remove(position);
                    }
                }
            }
        });
        alertBuilder.setCancelable(false);
        alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String item = "";
                for (int i = 0; i < mSelectedUserItem.size(); i++) {
                    item = item + arr[mSelectedUserItem.get(i)];
                    if (i != mSelectedUserItem.size() - 1) {
                        item = item + ", ";
                    }
                }
                fragmentAddProductBinding.addProductListItemCategoriesTextViewId.setText(item);
            }
        });
        alertBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();

            }
        });
        alertBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                for (int i = 0; i < checkedItems.length; i++) {
                    checkedItems[i] = false;
                    mSelectedUserItem.clear();
                    fragmentAddProductBinding.addProductListItemCategoriesTextViewId.setText("");
                }
            }
        });
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    public ResponseCallback tagsResponseCallback() {
        ResponseCallback tagsResponseCallback = new ResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                tagsList = (List<Tag>) response.body(); //listItem
                System.out.println("tagsList: " + tagsList);
                arr = new String[tagsList.size()];
                for (int i = 0; i < tagsList.size(); i++) {
                    arr[i] = tagsList.get(i).getTag();
                }
                checkedItems = new boolean[arr.length];
            }

            @Override
            public void onError(String errorMessage) {

            }
        };
        return tagsResponseCallback;
    }

    @Override
    public void onSuccess(Response response) {
        if (response != null) {
            MessageResponse messageResponse = (MessageResponse) response.body();
            fragmentAddProductBinding.addProductNameEditTextId.setText(" ");
            fragmentAddProductBinding.addProductDescriptionEditTextId.setText(" ");
            fragmentAddProductBinding.addProductQuantityValueEditTextId.setText(" ");
            fragmentAddProductBinding.addProductPriceValueEditTextId.setText(" ");
            fragmentAddProductBinding.addProductImageURLEditTextId.setText(" ");
            fragmentAddProductBinding.addProductListItemCategoriesTextViewId.setText(" ");
            Picasso.get()
                    .load(R.drawable.ic_launcher_background)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .into(fragmentAddProductBinding.addProductImageViewId);

            FancyToast.makeText(getContext(), messageResponse.getMessageResponse(), Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

        }
    }

    @Override
    public void onError(String errorMessage) {
        FancyToast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();

    }
}
