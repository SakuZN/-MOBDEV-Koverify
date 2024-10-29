package com.example.koverify.product_list.food;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.koverify.database.FilterParam;
import com.example.koverify.database.drugs.DrugListItem;
import com.example.koverify.database.foods.FoodProduct;
import com.example.koverify.database.ScreenParam;
import com.example.koverify.database.SearchParam;
import com.example.koverify.database.foods.FoodRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class FoodProductListViewModel extends AndroidViewModel {
    private MutableLiveData<List<FoodProduct>> foodProductsLiveData;
    private MutableLiveData<Boolean> isLoadingLiveData;
    private List<FoodProduct> foodProductList;
    private FoodRepository foodRepository;
    private String foodType;

    private int limit = 20;
    private int offset = 0;
    private boolean isLastPage = false;

    private String searchQuery = "";
    private FilterParam filterParam = new FilterParam();

    public FoodProductListViewModel(@NonNull Application application) {
        super(application);
        foodProductsLiveData = new MutableLiveData<>();
        isLoadingLiveData = new MutableLiveData<>(false);
        foodProductList = new ArrayList<>();
        foodRepository = new FoodRepository(application.getApplicationContext());
    }

    public void init(String foodType) {
        this.foodType = foodType;
        loadFoodProducts();
    }

    public LiveData<List<FoodProduct>> getFoodProductsLiveData() {
        return foodProductsLiveData;
    }
    public LiveData<Boolean> getIsLoadingLiveData() {
        return isLoadingLiveData;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
        resetData();
    }

    public void setFilterParam(FilterParam filterParam) {
        this.filterParam = filterParam;
        resetData();
    }

    private void resetData() {
        offset = 0;
        isLastPage = false;
        foodProductList.clear();
        foodProductsLiveData.postValue(foodProductList);
        loadFoodProducts();
    }

    public void loadFoodProducts() {
        if (isLastPage) return;

        ScreenParam screenParam = new ScreenParam(limit, offset);
        SearchParam searchParam = new SearchParam();
        searchParam.setSearch(searchQuery);
        FilterParam filterParam = new FilterParam(this.filterParam.getFilters());
        if (!"all".equals(foodType)) {
            filterParam.setFilter("food_type", foodType);
        }

        isLoadingLiveData.postValue(true);

        Executors.newSingleThreadExecutor().execute(() -> {
            List<FoodProduct> newProducts = foodRepository.getFoodsList(screenParam, searchParam, filterParam);
            System.out.println("newProducts: " + newProducts);
            if (newProducts == null || newProducts.isEmpty()) {
                isLastPage = true;
                if (offset == 0) {
                    foodProductsLiveData.postValue(new ArrayList<>());
                }
            } else {
                offset += limit;
                foodProductList.addAll(newProducts);
                foodProductsLiveData.postValue(new ArrayList<>(foodProductList));
            }
            isLoadingLiveData.postValue(false);
        });
    }

    public void loadMoreFoodProducts(Runnable onComplete) {
        if (isLastPage) {
            if (onComplete != null) onComplete.run();
            return;
        }

        ScreenParam screenParam = new ScreenParam(limit, offset);
        SearchParam searchParam = new SearchParam();
        searchParam.setSearch(searchQuery);
        FilterParam filterParam = new FilterParam(this.filterParam.getFilters());
        if (!"all".equals(foodType)) {
            filterParam.setFilter("food_type", foodType);
        }
        isLoadingLiveData.postValue(true);

        Executors.newSingleThreadExecutor().execute(() -> {
            List<FoodProduct> newProducts = foodRepository.getFoodsList(screenParam, searchParam, filterParam);
            System.out.println("newProducts: " + newProducts);
            if (newProducts == null || newProducts.isEmpty()) {
                isLastPage = true;
            } else {
                offset += limit;
                foodProductList.addAll(newProducts);
                foodProductsLiveData.postValue(foodProductList);
            }
            isLoadingLiveData.postValue(false);
            if (onComplete != null) onComplete.run();
        });
    }
}
