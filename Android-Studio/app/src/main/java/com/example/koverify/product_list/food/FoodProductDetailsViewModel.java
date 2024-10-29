package com.example.koverify.product_list.food;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.koverify.database.MyDatabase;
import com.example.koverify.database.foods.FoodProduct;
import com.example.koverify.database.foods.FoodProductDao;
import java.util.concurrent.Executors;
public class FoodProductDetailsViewModel extends AndroidViewModel {
    private FoodProductDao foodProductDao;

    public FoodProductDetailsViewModel(@NonNull Application application) {
        super(application);
        MyDatabase db = MyDatabase.getDatabase(application);
        foodProductDao = db.foodProductDao();
    }

    public LiveData<FoodProduct> getFoodProductDetails(String regNum) {
        MutableLiveData<FoodProduct> data = new MutableLiveData<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            FoodProduct foodProduct = foodProductDao.getFoodInfo(regNum);
            data.postValue(foodProduct);
        });
        return data;
    }
}
