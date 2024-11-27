// SearchParam.java
package com.example.koverify.database;

public class SearchParam {
    private String search;
    private String searchType;

    // Constructors
    public SearchParam(String search, String searchType) {
        this.search = search;
        this.searchType = searchType;
    }

    public SearchParam() {
        this.search = "";
        this.searchType = "";
    }

    // Getters
    public String getSearch() {
        return search;
    }

    public String getSearchType() {
        return searchType;
    }

    // Setters
    public void setSearch(String search) {
        this.search = search;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
}
