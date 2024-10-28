package com.example.koverify.product_list.drugs;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.koverify.database.MyDatabase;
import com.example.koverify.database.drugs.CountryList;
import com.example.koverify.database.drugs.DrugClassificationList;
import com.example.koverify.database.drugs.DrugProductDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class DrugFilterViewModel extends AndroidViewModel {

    private DrugProductDao drugProductDao;

    public DrugFilterViewModel(@NonNull Application application) {
        super(application);
        MyDatabase db = MyDatabase.getDatabase(application);
        drugProductDao = db.drugProductDao();
    }

    public LiveData<List<String>> getCountries() {
        MutableLiveData<List<String>> data = new MutableLiveData<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            List<CountryList> countries = drugProductDao.getDrugCountries();
            // Extract country names
            List<String> countryNames = new ArrayList<>();
            for (CountryList country : countries) {
                countryNames.add(country.getCountry_of_origin());
            }
            data.postValue(countryNames);
        });
        return data;
    }

    public LiveData<List<String>> getClassifications() {
        MutableLiveData<List<String>> data = new MutableLiveData<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            List<DrugClassificationList> classifications = drugProductDao.getDrugClassifications();
            // Extract classification names
            List<String> classificationNames = new ArrayList<>();
            for (DrugClassificationList classification : classifications) {
                classificationNames.add(classification.getClassification());
            }
            data.postValue(classificationNames);
        });
        return data;
    }
}
