// FoodFilterType.java
package com.example.koverify.database.foods;

import java.util.HashMap;
import java.util.Map;

public class FoodFilterType {
    private Map<String, String> filters = new HashMap<>();

    // Setters
    public void setFilter(String key, String value) {
        filters.put(key, value);
    }

    // Getters
    public Map<String, String> getFilters() {
        return filters;
    }

    // Helper methods
    public void setIssuanceDate(String date) {
        filters.put("issuance_date", date);
    }

    public void setExpiryDate(String date) {
        filters.put("expiry_date", date);
    }

    public void setFoodType(String type) {
        filters.put("food_type", type);
    }

    // Add other filter methods as needed
}
