package com.example.koverify.network;

import com.example.koverify.network.models.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OpenFoodFactsApiService {
    @GET("api/v2/product/{barcode}.json")
    Call<ProductResponse> getProductByBarcode(
            @Path("barcode") String barcode,
            @Query("fields") String fields, // Optional: Specify fields to retrieve
            @Query("lc") String languageCode // Optional: Language code for localization
    );
}

