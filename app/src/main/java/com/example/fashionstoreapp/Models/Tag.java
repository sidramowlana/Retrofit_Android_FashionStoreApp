package com.example.fashionstoreapp.Models;

import com.google.gson.annotations.SerializedName;

public class Tag{

    @SerializedName("Tag")
    private String tag;

    public Tag() {
    }

    public Tag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
