// DrugRepository.java
package com.example.koverify.database.drugs;

import android.content.Context;

import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.koverify.database.MyDatabase;
import com.example.koverify.database.ScreenParam;
import com.example.koverify.database.SearchParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DrugRepository {

    private DrugProductDao drugProductDao;

    public DrugRepository(Context context) {
        MyDatabase db = MyDatabase.getDatabase(context);
        drugProductDao = db.drugProductDao();
    }

    public List<DrugListItem> getDrugsList(ScreenParam param, SearchParam searchParam, DrugFilterType filterParam) {
        int limit = param.getLimit();
        int offset = param.getOffset();

        StringBuilder baseQuery = new StringBuilder("SELECT * FROM DrugListView");
        List<Object> queryParams = new ArrayList<>();
        String searchQuery = " LIKE '%' || ? || '%'";

        // Push search params into query
        boolean hasWhereClause = false;

        if (searchParam != null && searchParam.getSearch() != null && !searchParam.getSearch().isEmpty()) {
            baseQuery.append(" WHERE");
            hasWhereClause = true;

            String searchType = searchParam.getSearchType();
            String searchValue = searchParam.getSearch();

            if (searchType.equals("trader") || searchType.equals("importer") || searchType.equals("distributor")) {
                baseQuery.append(" hdi_").append(searchType).append(searchQuery);
                baseQuery.append(" OR vdi_").append(searchType).append(searchQuery);
                queryParams.add(searchValue);
                queryParams.add(searchValue);
            } else {
                baseQuery.append(" ").append(searchType).append(searchQuery);
                queryParams.add(searchValue);
            }
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
                        case "drug_type":
                            if (!value.equals("all")) {
                                baseQuery.append(filterQuery).append(" drug_type = ?");
                                queryParams.add(value.equals("human") ? "Human" : "Vet");
                                filterQuery = " AND";
                            }
                            break;
                        case "classification":
                            baseQuery.append(filterQuery).append(" hdi_").append(key).append(searchQuery);
                            baseQuery.append(" OR vdi_").append(key).append(searchQuery);
                            queryParams.add(value);
                            queryParams.add(value);
                            filterQuery = " AND";
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


        return drugProductDao.getDrugsList(query);
    }

    public HumanDrug getHumanDrugInfo(String regNum) {
        return drugProductDao.getHumanDrugInfo(regNum);
    }

    public VetDrug getVetDrugInfo(String regNum) {
        return drugProductDao.getVetDrugInfo(regNum);
    }

    public List<DrugClassificationList> getDrugClassifications() {
        return drugProductDao.getDrugClassifications();
    }

    public List<CountryList> getDrugCountries() {
        return drugProductDao.getDrugCountries();
    }
}
