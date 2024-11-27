// HumanDrug.java
package com.example.koverify.database.drugs;

import androidx.room.Embedded;

public class HumanDrug {

    @Embedded(prefix = "dp_")
    public DrugProduct drugProduct;

    @Embedded(prefix = "hdi_")
    public HumanDrugInfo humanDrugInfo;
}
