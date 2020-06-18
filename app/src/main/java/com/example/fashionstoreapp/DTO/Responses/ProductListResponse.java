package com.example.fashionstoreapp.DTO.Responses;

import com.example.fashionstoreapp.Models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListResponse {
    private List<Product> productList=new ArrayList<>();

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
