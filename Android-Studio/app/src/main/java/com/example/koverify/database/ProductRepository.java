package com.example.koverify.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.koverify.database.drugs.DrugProductDao;
import com.example.koverify.database.drugs.HumanDrug;
import com.example.koverify.database.drugs.VetDrug;
import com.example.koverify.database.foods.FoodProduct;
import com.example.koverify.database.foods.FoodProductDao;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProductRepository {

    private final FoodProductDao foodProductDao;
    private final DrugProductDao drugProductDao;
    private final Executor executor;

    public ProductRepository(@NonNull Application application) {
        MyDatabase db = MyDatabase.getDatabase(application);
        foodProductDao = db.foodProductDao();
        drugProductDao = db.drugProductDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<String> getProductType(String sku) {
        MutableLiveData<String> productType = new MutableLiveData<>();

        executor.execute(() -> {
            // Check Food Products
            FoodProduct foodProduct = foodProductDao.getFoodInfoSKU(sku);
            if (foodProduct != null) {
                productType.postValue("Food");
                return;
            }

            // Check Human Drugs
            HumanDrug humanDrug = drugProductDao.getHumanDrugInfoSKU(sku);
            if (humanDrug != null) {
                productType.postValue("HumanDrug");
                return;
            }

            // Check Vet Drugs
            VetDrug vetDrug = drugProductDao.getVetDrugInfoSKU(sku);
            if (vetDrug != null) {
                productType.postValue("VetDrug");
                return;
            }

            // If not found
            productType.postValue("Unknown");
        });

        return productType;
    }
}
