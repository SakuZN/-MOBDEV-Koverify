package com.example.koverify.product_list.drugs;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.koverify.database.MyDatabase;
import com.example.koverify.database.drugs.DrugProductDao;
import com.example.koverify.database.drugs.HumanDrug;
import com.example.koverify.database.drugs.VetDrug;

import java.util.concurrent.Executors;
public class DrugProductDetailsViewModel extends AndroidViewModel {
    private DrugProductDao drugProductDao;

    public DrugProductDetailsViewModel(@NonNull Application application) {
        super(application);
        MyDatabase db = MyDatabase.getDatabase(application);
        drugProductDao = db.drugProductDao();
    }

    public LiveData<HumanDrug> getHumanDrugDetails(String regNum) {
        MutableLiveData<HumanDrug> data = new MutableLiveData<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            HumanDrug humanDrug = drugProductDao.getHumanDrugInfo(regNum);
            data.postValue(humanDrug);
        });
        return data;
    }

    public LiveData<HumanDrug> getHumanDrugDetailsSKU(String sku) {
        MutableLiveData<HumanDrug> data = new MutableLiveData<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            HumanDrug humanDrug = drugProductDao.getHumanDrugInfoSKU(sku);
            data.postValue(humanDrug);
        });
        return data;
    }

    public LiveData<VetDrug> getVetDrugDetails(String regNum) {
        MutableLiveData<VetDrug> data = new MutableLiveData<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            VetDrug vetDrug = drugProductDao.getVetDrugInfo(regNum);
            data.postValue(vetDrug);
        });
        return data;
    }

    public LiveData<VetDrug> getVetDrugDetailsSKU(String sku) {
        MutableLiveData<VetDrug> data = new MutableLiveData<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            VetDrug vetDrug = drugProductDao.getVetDrugInfoSKU(sku);
            data.postValue(vetDrug);
        });
        return data;
    }
}
