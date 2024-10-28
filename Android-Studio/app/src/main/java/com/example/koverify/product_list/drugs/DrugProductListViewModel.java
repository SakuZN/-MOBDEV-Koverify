package com.example.koverify.product_list.drugs;

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
    private MutableLiveData<Boolean> isLoadingLiveData;
    private List<DrugListItem> drugProductsList;
    private DrugRepository drugRepository;
    private String drugType;

    private int limit = 20;
    private int offset = 0;
    private boolean isLastPage = false;

    private String searchQuery = "";
    private DrugFilterType filterParam = new DrugFilterType();

    public DrugProductListViewModel(@NonNull Application application) {
        super(application);
        drugProductsLiveData = new MutableLiveData<>();
        isLoadingLiveData = new MutableLiveData<>(false);
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
    public LiveData<Boolean> getIsLoadingLiveData() {
        return isLoadingLiveData;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
        resetData();
    }

    public void setFilterParam(DrugFilterType filterParam) {
        this.filterParam = filterParam;
        resetData();
    }

    private void resetData() {
        offset = 0;
        isLastPage = false;
        drugProductsList.clear();
        drugProductsLiveData.postValue(drugProductsList);
        loadDrugProducts();
    }

    public void loadDrugProducts() {
        if (isLastPage) return;

        ScreenParam screenParam = new ScreenParam(limit, offset);
        SearchParam searchParam = new SearchParam();
        searchParam.setSearch(searchQuery);
        DrugFilterType filterParam = new DrugFilterType(this.filterParam.getFilters());
        if (!"all".equals(drugType)) {
            filterParam.setFilter("drug_type", drugType);
        }

        isLoadingLiveData.postValue(true);

        Executors.newSingleThreadExecutor().execute(() -> {
            List<DrugListItem> newProducts = drugRepository.getDrugsList(screenParam, searchParam, filterParam);
            System.out.println("newProducts: " + newProducts);
            if (newProducts == null || newProducts.isEmpty()) {
                isLastPage = true;
                if (offset == 0) {
                    drugProductsLiveData.postValue(new ArrayList<>());
                }
            } else {
                offset += limit;
                drugProductsList.addAll(newProducts);
                drugProductsLiveData.postValue(new ArrayList<>(drugProductsList));
            }
            isLoadingLiveData.postValue(false);
        });
    }

    public void loadMoreDrugProducts(Runnable onComplete) {
        if (isLastPage) {
            if (onComplete != null) onComplete.run();
            return;
        }

        ScreenParam screenParam = new ScreenParam(limit, offset);
        SearchParam searchParam = new SearchParam();
        searchParam.setSearch(searchQuery);
        DrugFilterType filterParam = new DrugFilterType(this.filterParam.getFilters());
        if (!"all".equals(drugType)) {
            filterParam.setFilter("drug_type", drugType);
        }
        isLoadingLiveData.postValue(true);

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
            isLoadingLiveData.postValue(false);
            if (onComplete != null) onComplete.run();
        });
    }
}
