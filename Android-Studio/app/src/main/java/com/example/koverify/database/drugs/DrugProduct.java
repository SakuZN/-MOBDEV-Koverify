// DrugProduct.java
package com.example.koverify.database.drugs;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "drug_products")
public class DrugProduct {

    @PrimaryKey
    @NonNull
    private String reg_num;

    private String generic_name;
    private String brand_name;
    private String dosage_strength;
    private String dosage_form;
    private String manufacturer;
    private String country_of_origin;
    private String issuance_date;
    private String expiry_date;
    private String sku;

    // Getters
    @NonNull
    public String getReg_num() {
        return reg_num;
    }

    public String getGeneric_name() {
        return generic_name;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public String getDosage_strength() {
        return dosage_strength;
    }

    public String getDosage_form() {
        return dosage_form;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getCountry_of_origin() {
        return country_of_origin;
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

    public void setGeneric_name(String generic_name) {
        this.generic_name = generic_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public void setDosage_strength(String dosage_strength) {
        this.dosage_strength = dosage_strength;
    }

    public void setDosage_form(String dosage_form) {
        this.dosage_form = dosage_form;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setCountry_of_origin(String country_of_origin) {
        this.country_of_origin = country_of_origin;
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