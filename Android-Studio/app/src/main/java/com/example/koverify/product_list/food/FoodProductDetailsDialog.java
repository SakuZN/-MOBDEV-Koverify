package com.example.koverify.product_list.food;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koverify.R;
import com.example.koverify.database.drugs.HumanDrug;
import com.example.koverify.database.foods.FoodProduct;

public class FoodProductDetailsDialog extends DialogFragment {

    private String regNum;
    private String sku;
    private OnDismissListener dismissListener;

    public interface OnDismissListener {
        void onDismiss();
    }

    public void setOnDismissListener(OnDismissListener listener) {
        this.dismissListener = listener;
    }

    public static FoodProductDetailsDialog newInstance(String regNum) {
        FoodProductDetailsDialog fragment = new FoodProductDetailsDialog();
        Bundle args = new Bundle();
        args.putString("reg_num", regNum);
        fragment.setArguments(args);
        return fragment;
    }

    public static FoodProductDetailsDialog newInstanceSKU(String sku) {
        FoodProductDetailsDialog fragment = new FoodProductDetailsDialog();
        Bundle args = new Bundle();
        args.putString("sku", sku);
        fragment.setArguments(args);
        return fragment;
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
            sku = getArguments().getString("sku");
        }

        // Initialize views
        ProgressBar loadingSpinner = view.findViewById(R.id.loadingSpinner);
        GridLayout detailsGrid = view.findViewById(R.id.detailsGrid);
        Button okButton = view.findViewById(R.id.okButton);

        // Initially show the spinner and hide other views
        loadingSpinner.setVisibility(View.VISIBLE);
        detailsGrid.setVisibility(View.GONE);
        okButton.setVisibility(View.GONE);

        // Load product details
        loadProductDetails(view, loadingSpinner, detailsGrid, okButton);

        // Set up OK button
        okButton.setOnClickListener(v -> dismiss());

        return view;
    }

    private void loadProductDetails(View view, ProgressBar loadingSpinner, GridLayout detailsGrid, Button okButton) {
        // Use ViewModel to fetch product details
        FoodProductDetailsViewModel viewModel = new ViewModelProvider(this).get(FoodProductDetailsViewModel.class);
        if (regNum != null) {
            viewModel.getFoodProductDetails(regNum).observe(getViewLifecycleOwner(), foodProduct -> {
                loadingSpinner.setVisibility(View.GONE);
                if (foodProduct != null) {
                    displayFoodProductDetails(view, foodProduct, detailsGrid);
                    detailsGrid.setVisibility(View.VISIBLE);
                    okButton.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getContext(), "Food Product not found", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            });
        } else if (sku != null) {
            viewModel.getFoodProductDetailsSKU(sku).observe(getViewLifecycleOwner(), foodProduct -> {
                loadingSpinner.setVisibility(View.GONE);
                if (foodProduct != null) {
                    displayFoodProductDetails(view, foodProduct, detailsGrid);
                    detailsGrid.setVisibility(View.VISIBLE);
                    okButton.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getContext(), "Food Product not found", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            });
        }
    }

    private void displayFoodProductDetails(View view, FoodProduct foodProduct, GridLayout detailsGrid) {
        // Clear existing views
        detailsGrid.removeAllViews();

        // Add details from FoodProduct
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

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dismissListener != null) {
            dismissListener.onDismiss();
        }
    }
}
