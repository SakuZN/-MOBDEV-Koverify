// VetDrug.java
package com.example.koverify.database.drugs;

import androidx.room.Embedded;

public class VetDrug {

    @Embedded(prefix = "dp_")
    public DrugProduct drugProduct;

    @Embedded(prefix = "vdi_")
    public VetDrugInfo vetDrugInfo;
}
