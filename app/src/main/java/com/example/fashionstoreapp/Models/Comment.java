package com.example.fashionstoreapp.Models;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private Integer postId;
    private int id;
    private String name;
    private String email;
    @SerializedName("body")
    private String text;

}