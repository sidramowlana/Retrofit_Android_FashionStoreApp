package com.example.fashionstoreapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fashionstoreapp.DTO.Responses.LoginResponse;
import com.example.fashionstoreapp.Interface.SharedService;
import com.example.fashionstoreapp.Models.Product;
import com.example.fashionstoreapp.Models.Wishlist;
import com.example.fashionstoreapp.R;
import com.example.fashionstoreapp.RetrofitAPIService.ProductService;
import com.example.fashionstoreapp.RetrofitInterface.ResponseCallBackInterface;
import com.example.fashionstoreapp.Storage.SharedPreferenceManager;
import com.example.fashionstoreapp.databinding.ActivityProductDetailBinding;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity implements SharedService, ResponseCallBackInterface {

    ActivityProductDetailBinding activityProductDetailBinding;
    Product product;
    ProductService productService;

    Wishlist wishList;
    String selectedSize;
    public static String ORDER_KEY = "com.example.fashionstore.Activities.ORDER_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProductDetailBinding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        View view = activityProductDetailBinding.getRoot();
        setContentView(view);
        setSupportActionBar(activityProductDetailBinding.detailToolbarId);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        productService = new ProductService();
        final LoginResponse loginResponse = SharedPreferenceManager.getSharedPreferenceInstance(this).getUser();
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            final int productId = bundle.getInt("productId");
            System.out.println("product detail activity: " + productId);
            productService.getProduct(productId, this);


            //add to favourites
//        final List<Wishlist> favoriteList = Select.from(Wishlist.class)
//                .where(Condition.prop("favourite_product").eq(product.getId().toString()),
//                        Condition.prop("favourite_user").eq(user.getId().toString()))
//                .list();

//        activityProductDetailBinding.buttonFavorite.setChecked(favoriteList.size() != 0);

////        Favourites
            activityProductDetailBinding.buttonFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddProductWishlist(productId, loginResponse);
                }
            });

//        Share
            activityProductDetailBinding.buttonShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onShare(product);
                }
            });

            //setting the available qty of a product
//        if (product.getQuantity() == 0) {
//            Toast.makeText(getApplicationContext(), "Out of Stock", Toast.LENGTH_SHORT).show();
//        } else {
//            activityProductDetailBinding.textviewAvailableQtyId.setText(String.valueOf(product.getQuantity()));
//        }
//        Add to cart
            activityProductDetailBinding.detailAddCartBtnId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                onAddToCart();
                }
            });

            activityProductDetailBinding.detailSmallBtnId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activityProductDetailBinding.textviewSelectesSizeId.setText("S");
                    selectedSize = activityProductDetailBinding.textviewSelectesSizeId.getText().toString();
                }
            });
            activityProductDetailBinding.detailMediumBtnId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activityProductDetailBinding.textviewSelectesSizeId.setText("M");
                    selectedSize = activityProductDetailBinding.textviewSelectesSizeId.getText().toString();
                }
            });
            activityProductDetailBinding.detailLargeBtnId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activityProductDetailBinding.textviewSelectesSizeId.setText("L");
                    selectedSize = activityProductDetailBinding.textviewSelectesSizeId.toString();
                }
            });

            //displaying the count of feedback of a product
//        final List<RateReview> rateReviewList = RateReview.find(RateReview.class, "product=?", product.getId().toString());
//        int reviewCount = rateReviewList.size();
//        activityProductDetailBinding.includeFeedbackId.feedbackReviewCountId.setText("( " + reviewCount + " )");
//
//        //feedback viewall
//        activityProductDetailBinding.detailFeedbackCardId.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (rateReviewList.isEmpty()) {
//                    Toast.makeText(getApplicationContext(), "No Reviews available", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(getApplicationContext(), ProductFeedbackActivity.class);
//                    intent.putExtra("productFeedbackId", product.getId());
//                    startActivity(intent);
//                }
//            }
//        });
//
//        //Question and Answers
//        activityProductDetailBinding.detailQACardId.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), QuestionAnswerActivity.class);
//                intent.putExtra("productQAId", product.getId());
//                startActivity(intent);
//            }
//        });
        }
        onCalculateRatingAverage();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        onCalculateRatingAverage();
    }

    public void onAddProductWishlist(Integer productId, LoginResponse loginResponse) {
        if (activityProductDetailBinding.buttonFavorite.isChecked()) {
            productService.addProductFavourite(productId, loginResponse.getToken(), this);
            FancyToast.makeText(getApplicationContext(), "Added to your WishList", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
        } else {
            onRemoveProductWishlist(productId, loginResponse);
            FancyToast.makeText(getApplicationContext(), "Removed from your WishList", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
        }
    }

    public void onRemoveProductWishlist(Integer productId, LoginResponse loginResponse) {
        FancyToast.makeText(getApplicationContext(), "Should work on removing from your WishList", Toast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

    }
//    public void onAddToCart() {
//        User user = StoresUser.getCurrentLoggedUser(getApplicationContext());
//        String size = activityProductDetailBinding.textviewSelectesSizeId.getText().toString();
//        if (size.equals("")) {
//            Toast.makeText(getApplicationContext(), "Please select a size", Toast.LENGTH_SHORT).show();
//        } else {
//            List<Cart> cartList = Select.from(Cart.class)
//                    .where(
//                            Condition.prop("user").eq(user.getId().toString()),
//                            Condition.prop("product").eq(product.getId()),
//                            Condition.prop("size").eq(activityProductDetailBinding.textviewSelectesSizeId))
//                    .list();
//            if (cartList.isEmpty()) { //checking if the same product is there or not
//                int quantity = Integer.parseInt(activityProductDetailBinding.detailQtyId.getNumber());
//                double totalPrice = quantity * product.getPrice();
//                Cart cart = new Cart(user, product, quantity, size, totalPrice, false);
//                cart.save();
//                onBackPressed();
//                Toast.makeText(getApplicationContext(), "Added to your Cart", Toast.LENGTH_SHORT).show();
//            }
//            Toast.makeText(getApplicationContext(), "Added to your cart", Toast.LENGTH_SHORT).show();
//        }
//    }

    public void onShare(final Product shareProduct) {
        String share = "Look what I found!!!" + "\n" + "\n" + shareProduct.getProductName() + "\n" + "USD $ " + shareProduct.getPrice() + "\n" + shareProduct.getLongDescription() + "\n" + shareProduct.getScaledImage();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, share);
        sendIntent.setType("text/plain");
        Intent.createChooser(sendIntent, "Share via");
        startActivity(sendIntent);
    }

    @Override
    public void onCalculateRatingAverage() {
        DecimalFormat df2 = new DecimalFormat("0.0");
        double totalRate = 0;
        double average = 0;
//        List<RateReview> rateList = RateReview.find(RateReview.class, "product=?", product.getId().toString());
//        for (RateReview rateReview : rateList) {
//            totalRate += rateReview.getRate();
//            average = totalRate / rateList.size();
//        }
//        activityProductDetailBinding.includeFeedbackId.feedbackRatingbarId.setRating((float) average);
//        activityProductDetailBinding.includeFeedbackId.feedbackRateValueId.setText(df2.format(average));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSuccess(Response response) {
        Product product = (Product) response.body();
        System.out.println("product image is luffy: " + product.getScaledImage());
        System.out.println("product image is luffy: " + product.getFullImage());
        activityProductDetailBinding.detailProductNameId.setText(product.getProductName());
        activityProductDetailBinding.detailProductPriceId.setText("USD $ " + String.valueOf(product.getPrice()));
        activityProductDetailBinding.detailProductDescriptionId.setText(product.getShortDescription());
        Picasso.get()
                .load(product.getScaledImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(activityProductDetailBinding.detailImageViewId);

    }

    @Override
    public void onError(String errorMessage) {
        if (errorMessage != null) {
            FancyToast.makeText(this, errorMessage, Toast.LENGTH_SHORT, FancyToast.ERROR, false);
            System.out.println("error here is:" + errorMessage);
        }
    }
}
