// DrugFilterType.java
package com.example.koverify.database.drugs;

import java.util.HashMap;
import java.util.Map;

public class DrugFilterType {
    private Map<String, String> filters = new HashMap<>();

    public DrugFilterType() {
        this.filters = new HashMap<>();
    }
    public DrugFilterType(Map<String, String> filters) {
        this.filters = filters;
    }

    // Setters
    public void setFilter(String key, String value) {
        filters.put(key, value);
    }
    public void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }

    // Getters
    public Map<String, String> getFilters() {
        return filters;
    }
    public String getFilter(String key) {
        return filters.get(key);
    }

    // Helper methods for specific filters (if needed)
    public void setIssuanceDate(String date) {
        filters.put("issuance_date", date);
    }

    public void setExpiryDate(String date) {
        filters.put("expiry_date", date);
    }

    public void setDrugType(String type) {
        filters.put("drug_type", type);
    }

    public void setClassification(String classification) {
        filters.put("classification", classification);
    }

}
