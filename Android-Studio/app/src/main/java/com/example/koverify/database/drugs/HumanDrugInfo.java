// HumanDrugInfo.java
package com.example.koverify.database.drugs;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(
        tableName = "human_drug_info",
        foreignKeys = @ForeignKey(
                entity = DrugProduct.class,
                parentColumns = "reg_num",
                childColumns = "reg_num",
                onDelete = ForeignKey.NO_ACTION,
                onUpdate = ForeignKey.NO_ACTION
        )
)
public class HumanDrugInfo {

    @PrimaryKey
    @NonNull
    private String reg_num;

    private String classification;
    private String pharmacologic_category;
    private String trader;
    private String importer;
    private String distributor;

    @NonNull
    public String getReg_num() {
        return reg_num;
    }

    public String getClassification() {
        return classification;
    }

    public String getPharmacologic_category() {
        return pharmacologic_category;
    }

    public String getTrader() {
        return trader;
    }

    public String getImporter() {
        return importer;
    }

    public String getDistributor() {
        return distributor;
    }

    // Setters
    public void setReg_num(@NonNull String reg_num) {
        this.reg_num = reg_num;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public void setPharmacologic_category(String pharmacologic_category) {
        this.pharmacologic_category = pharmacologic_category;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public void setImporter(String importer) {
        this.importer = importer;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }
}