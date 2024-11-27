package com.example.koverify.database;

import java.util.HashMap;
import java.util.Map;

public class FilterParam {
    private Map<String, String> filters = new HashMap<>();

    public FilterParam() {
        this.filters = new HashMap<>();
    }
    public FilterParam(Map<String, String> filters) {
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
}
