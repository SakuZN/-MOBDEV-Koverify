package com.example.koverify.camera;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.koverify.database.ProductRepository;

public class ProductViewModel extends AndroidViewModel {
    private final ProductRepository productRepository;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);
    }

    public LiveData<String> getProductType(String sku) {
        return productRepository.getProductType(sku);
    }
}
