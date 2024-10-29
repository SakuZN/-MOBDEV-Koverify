package com.example.koverify.product_list.food;
import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.koverify.R;
import com.example.koverify.database.FilterParam;
import java.util.Calendar;
import java.util.List;

public class FoodFilterDialog extends DialogFragment {

    public interface FilterDialogListener {
        void onFilterApplied(FilterParam filter);
    }

    private FoodFilterDialog.FilterDialogListener listener;


    private EditText issuanceDateEditText;
    private EditText expiryDateEditText;
    private Spinner foodTypeSpinner;
    private Button resetButton;
    private Button applyButton;

    private String foodType; // Passed from the activity
    private String existingType;
    private FilterParam defaultFilter;

    public static FoodFilterDialog newInstance(String foodType, FilterParam filter) {
        FoodFilterDialog fragment = new FoodFilterDialog();
        Bundle args = new Bundle();
        String existingType = filter.getFilter("food_type");
        args.putString("food_type", foodType);
        args.putString("existingType", (existingType != null && !existingType.isEmpty()) ? existingType : "all");
        args.putString("issuance_date", filter.getFilter("issuance_date"));
        args.putString("expiry_date", filter.getFilter("expiry_date"));
        fragment.setArguments(args);
        return fragment;
    }

    public void setFilterDialogListener(FoodFilterDialog.FilterDialogListener listener) {
        this.listener = listener;
    }

    public FoodFilterDialog() {
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
        View view = inflater.inflate(R.layout.dialog_filter_food, container, false);

        defaultFilter = new FilterParam();
        if (getArguments() != null) {
            foodType = getArguments().getString("food_type");
            existingType = getArguments().getString("existingType");
            defaultFilter.setFilter("issuance_date", getArguments().getString("issuance_date"));
            defaultFilter.setFilter("expiry_date", getArguments().getString("expiry_date"));
        }

        // Initialize views
        issuanceDateEditText = view.findViewById(R.id.issuanceDateEditText);
        expiryDateEditText = view.findViewById(R.id.expiryDateEditText);
        foodTypeSpinner = view.findViewById(R.id.foodTypeSpinner);
        resetButton = view.findViewById(R.id.resetButton);
        applyButton = view.findViewById(R.id.applyButton);

        // Hide foodTypeSpinner if foodType is not "all"
        if ("all".equalsIgnoreCase(foodType)) {
            foodTypeSpinner.setVisibility(View.VISIBLE);
            view.findViewById(R.id.drugTypeLabel).setVisibility(View.VISIBLE);
        }

        // Load options for spinners
        loadFilterOptions();

        // Set up date pickers
        setupDatePickers();

        // Set up buttons
        resetButton.setOnClickListener(v -> resetFilters());
        applyButton.setOnClickListener(v -> applyFilters());

        return view;
    }

    private void loadFilterOptions() {
        // Load drug types if applicable
        if ("all".equalsIgnoreCase(foodType)) {
            List<String> options = List.of("All", "High Risk", "Medium Risk", "Low Risk", "Raw");
            int typeIndex = options.indexOf(existingType);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, options);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            foodTypeSpinner.setAdapter(adapter);
            if (typeIndex != -1) {
                foodTypeSpinner.setSelection(typeIndex);
            }
        }
    }

    private void setupDatePickers() {
        if (defaultFilter.getFilter("issuance_date") != null) {
            issuanceDateEditText.setText(defaultFilter.getFilter("issuance_date"));
        }
        if (defaultFilter.getFilter("expiry_date") != null) {
            expiryDateEditText.setText(defaultFilter.getFilter("expiry_date"));
        }
        issuanceDateEditText.setOnClickListener(v -> showDatePickerDialog(issuanceDateEditText));
        expiryDateEditText.setOnClickListener(v -> showDatePickerDialog(expiryDateEditText));
    }

    private void showDatePickerDialog(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year, month, dayOfMonth) -> {
                    String dateStr = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                    editText.setText(dateStr);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void resetFilters() {
        issuanceDateEditText.setText("");
        expiryDateEditText.setText("");
        if ("all".equalsIgnoreCase(foodType)) {
            foodTypeSpinner.setSelection(0);
        }
    }

    private void applyFilters() {
        FilterParam filter = new FilterParam();

        String issuanceDate = issuanceDateEditText.getText().toString();
        String expiryDate = expiryDateEditText.getText().toString();
        if (!issuanceDate.isEmpty()) filter.setFilter("issuance_date", issuanceDate);
        if (!expiryDate.isEmpty()) filter.setFilter("expiry_date", expiryDate);
        if ("all".equalsIgnoreCase(foodType)) {
            String selectedFoodType = foodTypeSpinner.getSelectedItem().toString();
            if (!"All".equalsIgnoreCase(selectedFoodType)) {
                filter.setFilter("food_type", selectedFoodType);
            }
        } else {
            filter.setFilter("food_type", foodType);
        }

        // Pass the filter back to the activity
        if (listener != null) {
            listener.onFilterApplied(filter);
        }
        dismiss();
    }
}
