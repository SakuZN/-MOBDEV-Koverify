// VetDrugInfo.java
package com.example.koverify.database.drugs;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "vet_drug_info", foreignKeys = @ForeignKey(
        entity = DrugProduct.class,
        parentColumns = "reg_num",
        childColumns = "reg_num",
        onDelete = ForeignKey.NO_ACTION,
        onUpdate = ForeignKey.NO_ACTION
))
public class VetDrugInfo {

    @PrimaryKey
    @NonNull
    private String reg_num;

    private String classification;
    private String application_type;
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

    public String getApplication_type() {
        return application_type;
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

    public void setApplication_type(String application_type) {
        this.application_type = application_type;
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