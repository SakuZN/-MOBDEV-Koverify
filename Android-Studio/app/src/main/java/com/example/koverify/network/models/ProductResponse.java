package com.example.koverify.network.models;

import com.google.gson.annotations.SerializedName;

public class ProductResponse {
    @SerializedName("status")
    private int status;

    @SerializedName("product")
    private Product product;

    // Getters and setters

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isStatus() {
        return status == 1;
    }
}

