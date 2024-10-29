// MyDatabase.java
package com.example.koverify.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.koverify.database.drugs.DrugListItem;
import com.example.koverify.database.drugs.DrugProduct;
import com.example.koverify.database.drugs.DrugProductDao;
import com.example.koverify.database.drugs.HumanDrugInfo;
import com.example.koverify.database.drugs.VetDrugInfo;
import com.example.koverify.database.foods.FoodProduct;
import com.example.koverify.database.foods.FoodProductDao;

@Database(entities = {
        DrugProduct.class,
        HumanDrugInfo.class,
        VetDrugInfo.class,
        FoodProduct.class
}, views = {
        DrugListItem.class
}, version = 4, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    public abstract DrugProductDao drugProductDao();
    public abstract FoodProductDao foodProductDao();

    private static volatile MyDatabase INSTANCE;

    public static MyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MyDatabase.class, "FDAProductIndex.db")
                            .createFromAsset("FDAProductIndex.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
