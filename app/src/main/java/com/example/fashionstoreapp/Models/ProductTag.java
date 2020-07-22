package com.example.fashionstoreapp.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductTag {
    private Integer productTagId;
    private Product product;
    private Tag tag;

}
