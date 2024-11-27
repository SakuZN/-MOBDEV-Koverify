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
import com.example.koverify.network.RetrofitClient;
import com.example.koverify.network.models.Product;
import com.example.koverify.network.models.ProductResponse;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

            // Make network request to Open Food Facts API
            Call<ProductResponse> call = RetrofitClient.getApiService().getProductByBarcode(
                    sku,
                    "product_name,generic_name",
                    "en" // Language code (optional)
            );

            call.enqueue(new Callback<ProductResponse>() {
                @Override
                public void onResponse(@NonNull Call<ProductResponse> call, @NonNull Response<ProductResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                        ProductResponse productResponse = response.body();
                        Product product = productResponse.getProduct();
                        String productName = product.getProductName();
                        System.out.println(productName);
                        String brandName = product.getGenericName();
                        System.out.println(brandName);

                        if (productName != null || brandName != null) {
                            String searchQuery = (productName != null) ? productName : brandName;

                            executor.execute(() -> {
                                // Check Food Products in DB
                                FoodProduct matchedFoodProduct = foodProductDao.searchFoodProduct(searchQuery);
                                if (matchedFoodProduct != null) {
                                    // Update the SKU for the matched food product
                                    foodProductDao.updateSkuByRegNum(matchedFoodProduct.getReg_num(), sku);
                                    productType.postValue("Food");
                                    return;
                                }

                                // Check Human Drugs in DB
                                HumanDrug matchedHumanDrug = drugProductDao.searchHumanDrug(searchQuery);
                                if (matchedHumanDrug != null) {
                                    // Update the SKU for the matched human drug
                                    drugProductDao.updateSkuByRegNum(matchedHumanDrug.drugProduct.getReg_num(), sku);
                                    productType.postValue("HumanDrug");
                                    return;
                                }

                                // Check Vet Drugs in DB
                                VetDrug matchedVetDrug = drugProductDao.searchVetDrug(searchQuery);
                                if (matchedVetDrug != null) {
                                    // Update the SKU for the matched vet drug
                                    drugProductDao.updateSkuByRegNum(matchedVetDrug.drugProduct.getReg_num(), sku);
                                    productType.postValue("VetDrug");
                                    return;
                                }

                                // If no matches found in DB, set as Unknown
                                productType.postValue("Unknown");
                            });
                        } else {
                            // If product name and brand name are null, set as Unknown
                            productType.postValue("Unknown");
                        }
                    } else {
                        // Handle unsuccessful response or product not found
                        productType.postValue("Unknown");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProductResponse> call, @NonNull Throwable t) {
                    // Handle network failure
                    t.printStackTrace();
                    productType.postValue("Unknown");
                }
            });
        });

        return productType;
    }
}
