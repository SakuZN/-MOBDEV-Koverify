package com.example.koverify.product_list.food;
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
import com.example.koverify.database.foods.FoodProduct;

public class FoodProductDetailsDialog extends DialogFragment {

    private String regNum;

    public static FoodProductDetailsDialog newInstance(String regNum) {
        FoodProductDetailsDialog fragment = new FoodProductDetailsDialog();
        Bundle args = new Bundle();
        args.putString("reg_num", regNum);
        fragment.setArguments(args);
        return fragment;
    }

    public FoodProductDetailsDialog() {
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
        }


        // Load product details
        loadProductDetails(view);

        // Set up OK button
        Button okButton = view.findViewById(R.id.okButton);
        okButton.setOnClickListener(v -> dismiss());

        return view;
    }

    private void loadProductDetails(View view) {
        // Use ViewModel to fetch product details
        FoodProductDetailsViewModel viewModel = new ViewModelProvider(this).get(FoodProductDetailsViewModel.class);
        viewModel.getFoodProductDetails(regNum).observe(getViewLifecycleOwner(), foodProduct -> {
            if (foodProduct != null) {
                displayFoodProductDetails(view, foodProduct);
            }
        });
    }

    private void displayFoodProductDetails(View view, FoodProduct foodProduct) {
        GridLayout detailsGrid = view.findViewById(R.id.detailsGrid);

        // Clear existing views
        detailsGrid.removeAllViews();
        System.out.println("Displaying FoodProduct details:" + foodProduct.getReg_num());

        // Add details from DrugProduct
        addDetailRow(detailsGrid, "Registration Number", foodProduct.getReg_num());
        addDetailRow(detailsGrid, "Brand Name", foodProduct.getBrand_name());
        addDetailRow(detailsGrid, "Product Name", foodProduct.getProduct_name());
        addDetailRow(detailsGrid, "Company Name", foodProduct.getCompany_name());
        addDetailRow(detailsGrid, "Product Type", foodProduct.getProduct_type());
        addDetailRow(detailsGrid, "Issuance Date", foodProduct.getIssuance_date());
        addDetailRow(detailsGrid, "Expiry Date", foodProduct.getExpiry_date());
        addDetailRow(detailsGrid, "SKU", foodProduct.getSku());
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

}
