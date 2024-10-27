// FoodRepository.java
package com.example.koverify.database.foods;

import android.content.Context;

import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.koverify.database.MyDatabase;
import com.example.koverify.database.ScreenParam;
import com.example.koverify.database.SearchParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FoodRepository {

    private FoodProductDao foodProductDao;

    public FoodRepository(Context context) {
        MyDatabase db = MyDatabase.getDatabase(context);
        foodProductDao = db.foodProductDao();
    }

    public List<FoodProduct> getFoodsList(ScreenParam param, SearchParam searchParam, FoodFilterType filterParam) {
        int limit = param.getLimit();
        int offset = param.getOffset();

        StringBuilder baseQuery = new StringBuilder("SELECT * FROM food_products");
        List<Object> queryParams = new ArrayList<>();
        String searchQuery = " LIKE '%' || ? || '%'";

        // Push search params into query
        boolean hasWhereClause = false;

        if (searchParam != null && searchParam.getSearch() != null && !searchParam.getSearch().isEmpty()) {
            baseQuery.append(" WHERE");
            hasWhereClause = true;

            String searchType = searchParam.getSearchType();
            String searchValue = searchParam.getSearch();

            baseQuery.append(" ").append(searchType).append(searchQuery);
            queryParams.add(searchValue);
        }

        // Push filter params into query
        if (filterParam != null && filterParam.getFilters() != null && !filterParam.getFilters().isEmpty()) {
            String filterQuery;
            if (hasWhereClause) {
                filterQuery = " AND";
            } else {
                baseQuery.append(" WHERE");
                filterQuery = " ";
                hasWhereClause = true;
            }

            for (Map.Entry<String, String> entry : filterParam.getFilters().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                if (value != null && !value.isEmpty()) {
                    switch (key) {
                        case "issuance_date":
                            baseQuery.append(filterQuery).append(" ").append(key).append(" >= ?");
                            queryParams.add(value);
                            filterQuery = " AND";
                            break;
                        case "expiry_date":
                            baseQuery.append(filterQuery).append(" ").append(key).append(" <= ?");
                            queryParams.add(value);
                            filterQuery = " AND";
                            break;
                        case "food_type":
                            if (!value.equals("all")) {
                                baseQuery.append(filterQuery).append(" product_type = ?");
                                queryParams.add(getFoodType(value));
                                filterQuery = " AND";
                            }
                            break;
                        default:
                            baseQuery.append(filterQuery).append(" ").append(key).append(searchQuery);
                            queryParams.add(value);
                            filterQuery = " AND";
                            break;
                    }
                }
            }
        }

        baseQuery.append(" LIMIT ? OFFSET ?");
        queryParams.add(limit);
        queryParams.add(offset);

        // For debugging purposes
        System.out.println("Final Query Statement: " + baseQuery.toString());
        System.out.println("Query Params: " + queryParams.toString());

        SupportSQLiteQuery query = new SimpleSQLiteQuery(baseQuery.toString(), queryParams.toArray());

        return foodProductDao.getFoodsList(query);
    }

    // Helper method to map food type values
    private String getFoodType(String value) {
        switch (value) {
            case "h_risk":
                return "High Risk";
            case "m_risk":
                return "Medium Risk";
            case "l_risk":
                return "Low Risk";
            case "raw":
                return "Raw";
            default:
                return value;
        }
    }

    // Implement getFoodInfo method
    public FoodProduct getFoodInfo(String regNum) {
        return foodProductDao.getFoodInfo(regNum);
    }
}
