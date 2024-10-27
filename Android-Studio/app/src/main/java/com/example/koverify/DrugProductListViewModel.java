package com.example.koverify;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.koverify.database.drugs.DrugFilterType;
import com.example.koverify.database.drugs.DrugListItem;
import com.example.koverify.database.drugs.DrugRepository;
import com.example.koverify.database.ScreenParam;
import com.example.koverify.database.SearchParam;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class DrugProductListViewModel extends AndroidViewModel {
    private MutableLiveData<List<DrugListItem>> drugProductsLiveData;
    private List<DrugListItem> drugProductsList;
    private DrugRepository drugRepository;
    private String drugType;

    private int limit = 20;
    private int offset = 0;
    private boolean isLastPage = false;

    public DrugProductListViewModel(@NonNull Application application) {
        super(application);
        drugProductsLiveData = new MutableLiveData<>();
        drugProductsList = new ArrayList<>();
        drugRepository = new DrugRepository(application.getApplicationContext());
    }

    public void init(String drugType) {
        this.drugType = drugType;
        loadDrugProducts();
    }

    public LiveData<List<DrugListItem>> getDrugProductsLiveData() {
        return drugProductsLiveData;
    }

    public void loadDrugProducts() {
        if (isLastPage) return;

        ScreenParam screenParam = new ScreenParam(limit, offset);
        SearchParam searchParam = null;
        DrugFilterType filterParam = new DrugFilterType();
        if (!"all".equals(drugType)) {
            filterParam.setFilter("drug_type", drugType);
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            List<DrugListItem> newProducts = drugRepository.getDrugsList(screenParam, searchParam, filterParam);
            if (newProducts == null || newProducts.isEmpty()) {
                isLastPage = true;
            } else {
                offset += limit;
                drugProductsList.addAll(newProducts);
                drugProductsLiveData.postValue(drugProductsList);
            }
        });
    }

    public void loadMoreDrugProducts(Runnable onComplete) {
        if (isLastPage) {
            if (onComplete != null) onComplete.run();
            return;
        }

        ScreenParam screenParam = new ScreenParam(limit, offset);
        SearchParam searchParam = null;
        DrugFilterType filterParam = new DrugFilterType();
        if (!"all".equals(drugType)) {
            filterParam.setFilter("drug_type", drugType);
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            List<DrugListItem> newProducts = drugRepository.getDrugsList(screenParam, searchParam, filterParam);
            System.out.println("newProducts: " + newProducts);
            if (newProducts == null || newProducts.isEmpty()) {
                isLastPage = true;
            } else {
                offset += limit;
                drugProductsList.addAll(newProducts);
                drugProductsLiveData.postValue(drugProductsList);
            }
            if (onComplete != null) onComplete.run();
        });
    }
}
