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

    @RawQuery
    List<FoodProduct> getFoodsList(SupportSQLiteQuery query);
}
