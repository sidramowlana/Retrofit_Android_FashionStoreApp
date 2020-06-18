package com.example.fashionstoreapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fashionstoreapp.R;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1000;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        if (!sharedPreferences.getBoolean("seeded", false))
//            readJSON();
    }
//
//    private void readJSON() {
//        String product;
//        String tag;
//        //seeding Tag table
//        try {
//            InputStream tagInputStream = getAssets().open("Tags");
//            int tagSize = tagInputStream.available();
//            byte[] bufferingTag = new byte[tagSize];
//            tagInputStream.read(bufferingTag);
//            tagInputStream.close();
//
//            tag = new String(bufferingTag, "UTF-8");
//            Type tagListType = new TypeToken<List<Tag>>() {
//            }.getType();
//            List<Tag> tagList = new Gson().fromJson(tag, tagListType);
//
//            if (Tag.count(Tag.class) == 0) {
//                for (Tag t : tagList) {
//                    t.save();
//                }
//            }
//
//            //seeding Product and ProductTag table
//            InputStream productInputStream = getAssets().open("Product");
//            int size = productInputStream.available();
//            byte[] bufferingProduct = new byte[size];
//            productInputStream.read(bufferingProduct);
//            productInputStream.close();
//
//            product = new String(bufferingProduct, "UTF-8");
//            Type productListType = new TypeToken<List<Product>>() {
//            }.getType();
//
//            List<Product> productList = new Gson().fromJson(product, productListType);
//            if (Product.count(Product.class) == 0) {
//                for (Product selected_product : productList) {
//                    selected_product.save();
//                    for (String tagId : selected_product.getTags()) {
//                        Tag selectedTag = Tag.findById(Tag.class, Long.parseLong(tagId));
//                        ProductTag productTag = new ProductTag(selected_product, selectedTag);
//                        productTag.save();
//                    }
//                }
//                List<Product> p = Product.listAll(Product.class);
//                //for debugging purpose
//                //List<ProductTag> productTagTableTest = ProductTag.listAll(ProductTag.class);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("seeded", true).apply();
//        System.out.println("Seeding completed.");
//    }

}
