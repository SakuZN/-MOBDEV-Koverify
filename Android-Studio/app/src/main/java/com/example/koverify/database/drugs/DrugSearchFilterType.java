// DrugSearchFilterType.java
package com.example.koverify.database.drugs;

public enum DrugSearchFilterType {
    Trader("trader"),
    Importer("importer"),
    Distributor("distributor"),
    GenericName("generic_name"),
    BrandName("brand_name"),
    Manufacturer("manufacturer");

    private String value;

    DrugSearchFilterType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
