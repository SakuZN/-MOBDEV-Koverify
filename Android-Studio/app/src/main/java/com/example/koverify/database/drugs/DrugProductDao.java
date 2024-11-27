// DrugProductDao.java
package com.example.koverify.database.drugs;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;
import java.util.List;

@Dao
public interface DrugProductDao {

    @Transaction
    @Query("SELECT " +
            "dp.reg_num AS dp_reg_num, " +
            "dp.generic_name AS dp_generic_name, " +
            "dp.brand_name AS dp_brand_name, " +
            "dp.dosage_strength AS dp_dosage_strength, " +
            "dp.dosage_form AS dp_dosage_form, " +
            "dp.manufacturer AS dp_manufacturer, " +
            "dp.country_of_origin AS dp_country_of_origin, " +
            "dp.issuance_date AS dp_issuance_date, " +
            "dp.expiry_date AS dp_expiry_date, " +
            "dp.sku AS dp_sku, " +
            "hdi.reg_num AS hdi_reg_num, " +
            "hdi.classification AS hdi_classification, " +
            "hdi.pharmacologic_category AS hdi_pharmacologic_category, " +
            "hdi.trader AS hdi_trader, " +
            "hdi.importer AS hdi_importer, " +
            "hdi.distributor AS hdi_distributor " +
            "FROM drug_products dp " +
            "JOIN human_drug_info hdi ON dp.reg_num = hdi.reg_num " +
            "WHERE dp.reg_num = :regNum LIMIT 1")
    HumanDrug getHumanDrugInfo(String regNum);

    @Transaction
    @Query("SELECT " +
            "dp.reg_num AS dp_reg_num, " +
            "dp.generic_name AS dp_generic_name, " +
            "dp.brand_name AS dp_brand_name, " +
            "dp.dosage_strength AS dp_dosage_strength, " +
            "dp.dosage_form AS dp_dosage_form, " +
            "dp.manufacturer AS dp_manufacturer, " +
            "dp.country_of_origin AS dp_country_of_origin, " +
            "dp.issuance_date AS dp_issuance_date, " +
            "dp.expiry_date AS dp_expiry_date, " +
            "dp.sku AS dp_sku, " +
            "hdi.reg_num AS hdi_reg_num, " +
            "hdi.classification AS hdi_classification, " +
            "hdi.pharmacologic_category AS hdi_pharmacologic_category, " +
            "hdi.trader AS hdi_trader, " +
            "hdi.importer AS hdi_importer, " +
            "hdi.distributor AS hdi_distributor " +
            "FROM drug_products dp " +
            "JOIN human_drug_info hdi ON dp.reg_num = hdi.reg_num " +
            "WHERE dp.sku = :sku LIMIT 1")
    HumanDrug getHumanDrugInfoSKU(String sku);

    @Transaction
    @Query("SELECT " +
            "dp.reg_num AS dp_reg_num, " +
            "dp.generic_name AS dp_generic_name, " +
            "dp.brand_name AS dp_brand_name, " +
            "dp.dosage_strength AS dp_dosage_strength, " +
            "dp.dosage_form AS dp_dosage_form, " +
            "dp.manufacturer AS dp_manufacturer, " +
            "dp.country_of_origin AS dp_country_of_origin, " +
            "dp.issuance_date AS dp_issuance_date, " +
            "dp.expiry_date AS dp_expiry_date, " +
            "dp.sku AS dp_sku, " +
            "vdi.reg_num AS vdi_reg_num, " +
            "vdi.classification AS vdi_classification, " +
            "vdi.application_type AS vdi_application_type, " +
            "vdi.trader AS vdi_trader, " +
            "vdi.importer AS vdi_importer, " +
            "vdi.distributor AS vdi_distributor " +
            "FROM drug_products dp " +
            "JOIN vet_drug_info vdi ON dp.reg_num = vdi.reg_num " +
            "WHERE dp.reg_num = :regNum LIMIT 1")
    VetDrug getVetDrugInfo(String regNum);

    @Transaction
    @Query("SELECT " +
            "dp.reg_num AS dp_reg_num, " +
            "dp.generic_name AS dp_generic_name, " +
            "dp.brand_name AS dp_brand_name, " +
            "dp.dosage_strength AS dp_dosage_strength, " +
            "dp.dosage_form AS dp_dosage_form, " +
            "dp.manufacturer AS dp_manufacturer, " +
            "dp.country_of_origin AS dp_country_of_origin, " +
            "dp.issuance_date AS dp_issuance_date, " +
            "dp.expiry_date AS dp_expiry_date, " +
            "dp.sku AS dp_sku, " +
            "vdi.reg_num AS vdi_reg_num, " +
            "vdi.classification AS vdi_classification, " +
            "vdi.application_type AS vdi_application_type, " +
            "vdi.trader AS vdi_trader, " +
            "vdi.importer AS vdi_importer, " +
            "vdi.distributor AS vdi_distributor " +
            "FROM drug_products dp " +
            "JOIN vet_drug_info vdi ON dp.reg_num = vdi.reg_num " +
            "WHERE dp.sku = :sku LIMIT 1")
    VetDrug getVetDrugInfoSKU(String sku);


    @Query("SELECT DISTINCT classification FROM human_drug_info WHERE classification IS NOT NULL UNION SELECT DISTINCT classification FROM vet_drug_info WHERE classification IS NOT NULL ORDER BY classification")
    List<DrugClassificationList> getDrugClassifications();

    @Query("SELECT DISTINCT country_of_origin FROM drug_products WHERE country_of_origin IS NOT NULL ORDER BY country_of_origin")
    List<CountryList> getDrugCountries();

    @RawQuery
    List<DrugListItem> getDrugsList(SupportSQLiteQuery query);

    @Transaction
    @Query("SELECT " +
            "dp.reg_num AS dp_reg_num, " +
            "dp.generic_name AS dp_generic_name, " +
            "dp.brand_name AS dp_brand_name, " +
            "dp.dosage_strength AS dp_dosage_strength, " +
            "dp.dosage_form AS dp_dosage_form, " +
            "dp.manufacturer AS dp_manufacturer, " +
            "dp.country_of_origin AS dp_country_of_origin, " +
            "dp.issuance_date AS dp_issuance_date, " +
            "dp.expiry_date AS dp_expiry_date, " +
            "dp.sku AS dp_sku, " +
            "hdi.reg_num AS hdi_reg_num, " +
            "hdi.classification AS hdi_classification, " +
            "hdi.pharmacologic_category AS hdi_pharmacologic_category, " +
            "hdi.trader AS hdi_trader, " +
            "hdi.importer AS hdi_importer, " +
            "hdi.distributor AS hdi_distributor " +
            "FROM drug_products dp " +
            "JOIN human_drug_info hdi ON dp.reg_num = hdi.reg_num " +
            "WHERE dp.generic_name LIKE '%' || :search || '%' COLLATE NOCASE LIMIT 1")
    HumanDrug searchHumanDrug(String search);

    @Transaction
    @Query("SELECT " +
            "dp.reg_num AS dp_reg_num, " +
            "dp.generic_name AS dp_generic_name, " +
            "dp.brand_name AS dp_brand_name, " +
            "dp.dosage_strength AS dp_dosage_strength, " +
            "dp.dosage_form AS dp_dosage_form, " +
            "dp.manufacturer AS dp_manufacturer, " +
            "dp.country_of_origin AS dp_country_of_origin, " +
            "dp.issuance_date AS dp_issuance_date, " +
            "dp.expiry_date AS dp_expiry_date, " +
            "dp.sku AS dp_sku, " +
            "vdi.reg_num AS vdi_reg_num, " +
            "vdi.classification AS vdi_classification, " +
            "vdi.application_type AS vdi_application_type, " +
            "vdi.trader AS vdi_trader, " +
            "vdi.importer AS vdi_importer, " +
            "vdi.distributor AS vdi_distributor " +
            "FROM drug_products dp " +
            "JOIN vet_drug_info vdi ON dp.reg_num = vdi.reg_num " +
            "WHERE dp.generic_name LIKE '%' || :search || '%' COLLATE NOCASE LIMIT 1")
    VetDrug searchVetDrug(String search);
    @Transaction
    @Query("UPDATE drug_products SET sku = :sku WHERE reg_num = :regNum")
    void updateSkuByRegNum(String regNum, String sku);
}
