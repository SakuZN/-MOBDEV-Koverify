// FoodProductDao.java
package com.example.koverify.database.foods;

import androidx.room.Dao;
import androidx.room.RawQuery;
import androidx.room.Query;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

@Dao
public interface FoodProductDao {

    @Query("SELECT * FROM food_products WHERE reg_num = :regNum LIMIT 1")
    FoodProduct getFoodInfo(String regNum);

    @Query("SELECT * FROM food_products WHERE sku = :sku LIMIT 1")
    FoodProduct getFoodInfoSKU(String sku);

    @Query("SELECT * FROM food_products WHERE product_name LIKE '%' || :search || '%' COLLATE NOCASE AND sku IS NULL LIMIT 1")
    FoodProduct searchFoodProduct(String search);

    @Query("UPDATE food_products SET sku = :sku WHERE reg_num = :regNum")
    void updateSkuByRegNum(String regNum, String sku);

    @RawQuery
    List<FoodProduct> getFoodsList(SupportSQLiteQuery query);
}
