package com.example.koverify.product_list.drugs;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.koverify.R;
import com.example.koverify.database.drugs.HumanDrug;
import com.example.koverify.database.drugs.VetDrug;
import com.example.koverify.product_list.food.FoodProductDetailsDialog;

public class DrugProductDetailsDialog extends DialogFragment {

    private String regNum;
    private String drugType;
    private String sku;
    private DrugProductDetailsDialog.OnDismissListener dismissListener;

    public interface OnDismissListener {
        void onDismiss();
    }

    public void setOnDismissListener(DrugProductDetailsDialog.OnDismissListener listener) {
        this.dismissListener = listener;
    }

    public static DrugProductDetailsDialog newInstance(String regNum, String drugType) {
        DrugProductDetailsDialog fragment = new DrugProductDetailsDialog();
        Bundle args = new Bundle();
        args.putString("reg_num", regNum);
        args.putString("drug_type", drugType);
        fragment.setArguments(args);
        return fragment;
    }

    public static DrugProductDetailsDialog newInstanceSKU(String sku) {
        DrugProductDetailsDialog fragment = new DrugProductDetailsDialog();
        Bundle args = new Bundle();
        args.putString("sku", sku);
        if (sku.charAt(0) == '2')
            args.putString("drug_type", "Human");
        else
            args.putString("drug_type", "Vet");
        fragment.setArguments(args);
        return fragment;
    }

    public DrugProductDetailsDialog() {
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_product_details, container, false);

        // Retrieve arguments
        if (getArguments() != null) {
            regNum = getArguments().getString("reg_num");
            drugType = getArguments().getString("drug_type");
            sku = getArguments().getString("sku");
        }

        // Initialize views
        // ...

        // Load product details
        loadProductDetails(view);

        // Set up OK button
        Button okButton = view.findViewById(R.id.okButton);
        okButton.setOnClickListener(v -> dismiss());

        return view;
    }

    private void loadProductDetails(View view) {
        // Use ViewModel to fetch product details
        DrugProductDetailsViewModel viewModel = new ViewModelProvider(this).get(DrugProductDetailsViewModel.class);
        if ("Human".equals(drugType)) {
            if (regNum != null) {
                viewModel.getHumanDrugDetails(regNum).observe(getViewLifecycleOwner(), humanDrug -> {
                    if (humanDrug != null) {
                        displayHumanDrugDetails(view, humanDrug);
                    }
                });
            }
            else if (sku != null) {
                viewModel.getHumanDrugDetailsSKU(sku).observe(getViewLifecycleOwner(), humanDrug -> {
                    if (humanDrug != null) {
                        displayHumanDrugDetails(view, humanDrug);
                    }
                });
            }
        } else if ("Vet".equals(drugType)) {
            if (regNum != null) {
                viewModel.getVetDrugDetails(regNum).observe(getViewLifecycleOwner(), vetDrug -> {
                    if (vetDrug != null) {
                        displayVetDrugDetails(view, vetDrug);
                    }
                });
            }
            else if (sku != null) {
                viewModel.getVetDrugDetailsSKU(sku).observe(getViewLifecycleOwner(), vetDrug -> {
                    if (vetDrug != null) {
                        displayVetDrugDetails(view, vetDrug);
                    }
                });
            }
        } else {
            // Handle unknown drug type
        }
    }

    private void displayHumanDrugDetails(View view, HumanDrug humanDrug) {
        GridLayout detailsGrid = view.findViewById(R.id.detailsGrid);

        // Clear existing views
        detailsGrid.removeAllViews();
        System.out.println("Displaying human drug details:" + humanDrug.drugProduct.getReg_num());

        // Add details from DrugProduct
        addDetailRow(detailsGrid, "Registration Number", humanDrug.drugProduct.getReg_num());
        addDetailRow(detailsGrid, "Brand Name", humanDrug.drugProduct.getBrand_name());
        addDetailRow(detailsGrid, "Generic Name", humanDrug.drugProduct.getGeneric_name());
        addDetailRow(detailsGrid, "Dosage Strength", humanDrug.drugProduct.getDosage_strength());
        addDetailRow(detailsGrid, "Dosage Form", humanDrug.drugProduct.getDosage_form());
        addDetailRow(detailsGrid, "Manufacturer", humanDrug.drugProduct.getManufacturer());
        addDetailRow(detailsGrid, "Country of Origin", humanDrug.drugProduct.getCountry_of_origin());
        addDetailRow(detailsGrid, "Issuance Date", humanDrug.drugProduct.getIssuance_date());
        addDetailRow(detailsGrid, "Expiry Date", humanDrug.drugProduct.getExpiry_date());
        addDetailRow(detailsGrid, "SKU", humanDrug.drugProduct.getSku());

        // Add details from HumanDrugInfo
        addDetailRow(detailsGrid, "Classification", humanDrug.humanDrugInfo.getClassification());
        addDetailRow(detailsGrid, "Pharmacologic Category", humanDrug.humanDrugInfo.getPharmacologic_category());
        addDetailRow(detailsGrid, "Trader", humanDrug.humanDrugInfo.getTrader());
        addDetailRow(detailsGrid, "Importer", humanDrug.humanDrugInfo.getImporter());
        addDetailRow(detailsGrid, "Distributor", humanDrug.humanDrugInfo.getDistributor());
    }



    private void displayVetDrugDetails(View view, VetDrug vetDrug) {
        GridLayout detailsGrid = view.findViewById(R.id.detailsGrid);

        // Clear existing views
        detailsGrid.removeAllViews();

        // Add details from DrugProduct
        addDetailRow(detailsGrid, "Registration Number", vetDrug.drugProduct.getReg_num());
        addDetailRow(detailsGrid, "Brand Name", vetDrug.drugProduct.getBrand_name());
        addDetailRow(detailsGrid, "Generic Name", vetDrug.drugProduct.getGeneric_name());
        addDetailRow(detailsGrid, "Dosage Strength", vetDrug.drugProduct.getDosage_strength());
        addDetailRow(detailsGrid, "Dosage Form", vetDrug.drugProduct.getDosage_form());
        addDetailRow(detailsGrid, "Manufacturer", vetDrug.drugProduct.getManufacturer());
        addDetailRow(detailsGrid, "Country of Origin", vetDrug.drugProduct.getCountry_of_origin());
        addDetailRow(detailsGrid, "Issuance Date", vetDrug.drugProduct.getIssuance_date());
        addDetailRow(detailsGrid, "Expiry Date", vetDrug.drugProduct.getExpiry_date());
        addDetailRow(detailsGrid, "SKU", vetDrug.drugProduct.getSku());

        // Add details from VetDrugInfo
        addDetailRow(detailsGrid, "Classification", vetDrug.vetDrugInfo.getClassification());
        addDetailRow(detailsGrid, "Application Type", vetDrug.vetDrugInfo.getApplication_type());
        addDetailRow(detailsGrid, "Trader", vetDrug.vetDrugInfo.getTrader());
        addDetailRow(detailsGrid, "Importer", vetDrug.vetDrugInfo.getImporter());
        addDetailRow(detailsGrid, "Distributor", vetDrug.vetDrugInfo.getDistributor());
    }


    private void addDetailRow(GridLayout gridLayout, String label, String value) {
        // Create a new LinearLayout
        LinearLayout rowLayout = new LinearLayout(getContext());
        rowLayout.setOrientation(LinearLayout.VERTICAL);
        rowLayout.setPadding(8, 8, 8, 8);

        // Set the specified attributes
        GridLayout.LayoutParams rowParams = new GridLayout.LayoutParams();
        rowParams.width = 0;
        rowParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        rowParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f); // Span 1 column with weight 1
        rowLayout.setLayoutParams(rowParams);

        // Create the label TextView
        TextView labelView = new TextView(getContext());
        labelView.setText(label);
        labelView.setTypeface(null, Typeface.BOLD);
        labelView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // Create the value TextView
        TextView valueView = new TextView(getContext());
        valueView.setText(value != null ? value : "N/A");
        valueView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // Add the TextViews to the LinearLayout
        rowLayout.addView(labelView);
        rowLayout.addView(valueView);

        // Add the LinearLayout to the GridLayout
        gridLayout.addView(rowLayout);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dismissListener != null) {
            dismissListener.onDismiss();
        }
    }
}

