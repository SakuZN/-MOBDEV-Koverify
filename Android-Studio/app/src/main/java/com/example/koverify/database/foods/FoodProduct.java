// FoodProduct.java
package com.example.koverify.database.foods;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "food_products")
public class FoodProduct {

    @PrimaryKey
    @NonNull
    private String reg_num;

    private String company_name;
    private String product_name;
    private String brand_name;
    private String product_type;
    private String issuance_date;
    private String expiry_date;
    private String sku;

    @NonNull
    public String getReg_num() {
        return reg_num;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public String getProduct_type() {
        return product_type;
    }

    public String getIssuance_date() {
        return issuance_date;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public String getSku() {
        return sku;
    }

    // Setters
    public void setReg_num(@NonNull String reg_num) {
        this.reg_num = reg_num;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public void setIssuance_date(String issuance_date) {
        this.issuance_date = issuance_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}