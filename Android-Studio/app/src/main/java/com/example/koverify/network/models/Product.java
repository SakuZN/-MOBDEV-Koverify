package com.example.koverify.network.models;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("product_name")
    private String productName;

    @SerializedName("generic_name")
    private String genericName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    // Other data not needed
}
