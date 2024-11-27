package com.example.koverify.database.drugs;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;

@DatabaseView("SELECT dp.*, " +
        "hdi.classification AS hdi_classification, " +
        "hdi.pharmacologic_category AS hdi_pharmacologic_category, " +
        "hdi.trader AS hdi_trader, " +
        "hdi.importer AS hdi_importer, " +
        "hdi.distributor AS hdi_distributor, " +
        "vdi.classification AS vdi_classification, " +
        "vdi.application_type AS vdi_application_type, " +
        "vdi.trader AS vdi_trader, " +
        "vdi.importer AS vdi_importer, " +
        "vdi.distributor AS vdi_distributor, " +
        "CASE " +
        "WHEN hdi.reg_num IS NOT NULL THEN 'Human' " +
        "WHEN vdi.reg_num IS NOT NULL THEN 'Vet' " +
        "ELSE 'Unknown' " +
        "END AS drug_type " +
        "FROM drug_products dp " +
        "LEFT JOIN human_drug_info hdi ON dp.reg_num = hdi.reg_num " +
        "LEFT JOIN vet_drug_info vdi ON dp.reg_num = vdi.reg_num")
public class DrugListItem {

    // Fields from drug_products
    @ColumnInfo(name = "reg_num")
    private String reg_num;

    @ColumnInfo(name = "generic_name")
    private String generic_name;

    @ColumnInfo(name = "brand_name")
    private String brand_name;

    @ColumnInfo(name = "dosage_strength")
    private String dosage_strength;

    @ColumnInfo(name = "dosage_form")
    private String dosage_form;

    @ColumnInfo(name = "manufacturer")
    private String manufacturer;

    @ColumnInfo(name = "country_of_origin")
    private String country_of_origin;

    @ColumnInfo(name = "issuance_date")
    private String issuance_date;

    @ColumnInfo(name = "expiry_date")
    private String expiry_date;

    @ColumnInfo(name = "sku")
    private String sku;

    // Additional fields from the view
    @ColumnInfo(name = "hdi_classification")
    private String hdi_classification;

    @ColumnInfo(name = "hdi_pharmacologic_category")
    private String hdi_pharmacologic_category;

    @ColumnInfo(name = "hdi_trader")
    private String hdi_trader;

    @ColumnInfo(name = "hdi_importer")
    private String hdi_importer;

    @ColumnInfo(name = "hdi_distributor")
    private String hdi_distributor;

    @ColumnInfo(name = "vdi_classification")
    private String vdi_classification;

    @ColumnInfo(name = "vdi_application_type")
    private String vdi_application_type;

    @ColumnInfo(name = "vdi_trader")
    private String vdi_trader;

    @ColumnInfo(name = "vdi_importer")
    private String vdi_importer;

    @ColumnInfo(name = "vdi_distributor")
    private String vdi_distributor;

    @ColumnInfo(name = "drug_type")
    private String drug_type; // "Human", "Vet", or "Unknown"

    public String getReg_num() {
        return reg_num;
    }

    public void setReg_num(String reg_num) {
        this.reg_num = reg_num;
    }

    public String getGeneric_name() {
        return generic_name;
    }

    public void setGeneric_name(String generic_name) {
        this.generic_name = generic_name;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getDosage_strength() {
        return dosage_strength;
    }

    public void setDosage_strength(String dosage_strength) {
        this.dosage_strength = dosage_strength;
    }

    public String getDosage_form() {
        return dosage_form;
    }

    public void setDosage_form(String dosage_form) {
        this.dosage_form = dosage_form;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCountry_of_origin() {
        return country_of_origin;
    }

    public void setCountry_of_origin(String country_of_origin) {
        this.country_of_origin = country_of_origin;
    }

    public String getIssuance_date() {
        return issuance_date;
    }

    public void setIssuance_date(String issuance_date) {
        this.issuance_date = issuance_date;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    // Additional fields from the view
    public String getHdi_classification() {
        return hdi_classification;
    }

    public void setHdi_classification(String hdi_classification) {
        this.hdi_classification = hdi_classification;
    }

    public String getHdi_pharmacologic_category() {
        return hdi_pharmacologic_category;
    }

    public void setHdi_pharmacologic_category(String hdi_pharmacologic_category) {
        this.hdi_pharmacologic_category = hdi_pharmacologic_category;
    }

    public String getHdi_trader() {
        return hdi_trader;
    }

    public void setHdi_trader(String hdi_trader) {
        this.hdi_trader = hdi_trader;
    }

    public String getHdi_importer() {
        return hdi_importer;
    }

    public void setHdi_importer(String hdi_importer) {
        this.hdi_importer = hdi_importer;
    }

    public String getHdi_distributor() {
        return hdi_distributor;
    }

    public void setHdi_distributor(String hdi_distributor) {
        this.hdi_distributor = hdi_distributor;
    }

    public String getVdi_classification() {
        return vdi_classification;
    }

    public void setVdi_classification(String vdi_classification) {
        this.vdi_classification = vdi_classification;
    }

    public String getVdi_application_type() {
        return vdi_application_type;
    }

    public void setVdi_application_type(String vdi_application_type) {
        this.vdi_application_type = vdi_application_type;
    }

    public String getVdi_trader() {
        return vdi_trader;
    }

    public void setVdi_trader(String vdi_trader) {
        this.vdi_trader = vdi_trader;
    }

    public String getVdi_importer() {
        return vdi_importer;
    }

    public void setVdi_importer(String vdi_importer) {
        this.vdi_importer = vdi_importer;
    }

    public String getVdi_distributor() {
        return vdi_distributor;
    }

    public void setVdi_distributor(String vdi_distributor) {
        this.vdi_distributor = vdi_distributor;
    }

    public String getDrug_type() {
        return drug_type;
    }

    public void setDrug_type(String drug_type) {
        this.drug_type = drug_type;
    }

    // Custom getter for classification
    public String getClassification() {
        // Return the appropriate classification based on drug type
        if ("Human".equals(drug_type)) {
            return hdi_classification;
        } else if ("Vet".equals(drug_type)) {
            return vdi_classification;
        } else {
            return "Unknown";
        }
    }
    @NonNull
    @Override
    public String toString() {
        return "Item{" + this.reg_num + "}";
    }
}