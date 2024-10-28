package com.example.koverify.product_list.drugs;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.koverify.R;
import com.example.koverify.database.drugs.DrugFilterType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DrugFilterDialog extends DialogFragment {

    public interface FilterDialogListener {
        void onFilterApplied(DrugFilterType filter);
    }

    private FilterDialogListener listener;

    private Spinner countrySpinner;
    private Spinner classificationSpinner;
    private EditText issuanceDateEditText;
    private EditText expiryDateEditText;
    private Spinner drugTypeSpinner;
    private Button resetButton;
    private Button applyButton;

    private String drugType; // Passed from the activity
    private DrugFilterType defaultFilter;

    public static DrugFilterDialog newInstance(String drugType, DrugFilterType filter) {
        DrugFilterDialog fragment = new DrugFilterDialog();
        Bundle args = new Bundle();
        args.putString("drug_type", drugType);
        args.putString("country_of_origin", filter.getFilter("country_of_origin"));
        args.putString("classification", filter.getFilter("classification"));
        args.putString("issuance_date", filter.getFilter("issuance_date"));
        args.putString("expiry_date", filter.getFilter("expiry_date"));
        fragment.setArguments(args);
        return fragment;
    }

    public void setFilterDialogListener(FilterDialogListener listener) {
        this.listener = listener;
    }

    public DrugFilterDialog() {
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
        View view = inflater.inflate(R.layout.dialog_filter_drugs, container, false);

        defaultFilter = new DrugFilterType();
        if (getArguments() != null) {
            drugType = getArguments().getString("drug_type");
            defaultFilter.setFilter("country_of_origin", getArguments().getString("country_of_origin"));
            defaultFilter.setFilter("classification", getArguments().getString("classification"));
            defaultFilter.setFilter("issuance_date", getArguments().getString("issuance_date"));
            defaultFilter.setFilter("expiry_date", getArguments().getString("expiry_date"));
        }

        // Initialize views
        countrySpinner = view.findViewById(R.id.countrySpinner);
        classificationSpinner = view.findViewById(R.id.classificationSpinner);
        issuanceDateEditText = view.findViewById(R.id.issuanceDateEditText);
        expiryDateEditText = view.findViewById(R.id.expiryDateEditText);
        drugTypeSpinner = view.findViewById(R.id.drugTypeSpinner);
        resetButton = view.findViewById(R.id.resetButton);
        applyButton = view.findViewById(R.id.applyButton);

        // Hide drugTypeSpinner if drugType is not "all"
        if (!"all".equalsIgnoreCase(drugType)) {
            drugTypeSpinner.setVisibility(View.GONE);
            view.findViewById(R.id.drugTypeLabel).setVisibility(View.GONE);
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
        // ViewModel to fetch options
        DrugFilterViewModel viewModel = new ViewModelProvider(this).get(DrugFilterViewModel.class);

        // Load countries
        viewModel.getCountries().observe(getViewLifecycleOwner(), countries -> {
            List<String> countryList = new ArrayList<>();
            countryList.add("Select Country"); // Add placeholder
            countryList.addAll(countries);
            //find index of default filter countrt origin string
            int countryIndex = countryList.indexOf(defaultFilter.getFilter("country_of_origin"));
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, countryList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            countrySpinner.setAdapter(adapter);
            if (countryIndex != -1) {
                countrySpinner.setSelection(countryIndex);
            }
        });

        // Load classifications
        viewModel.getClassifications().observe(getViewLifecycleOwner(), classifications -> {
            List<String> classificationList = new ArrayList<>();
            classificationList.add("Select Classification"); // Add placeholder
            classificationList.addAll(classifications);
            //find index of default filter classification string
            int classificationIndex = classificationList.indexOf(defaultFilter.getFilter("classification"));
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, classificationList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            classificationSpinner.setAdapter(adapter);
            if (classificationIndex != -1) {
                classificationSpinner.setSelection(classificationIndex);
            }
        });

        // Load drug types if applicable
        if ("all".equalsIgnoreCase(drugType)) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new String[]{"All", "Human", "Vet"});
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            drugTypeSpinner.setAdapter(adapter);
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
        countrySpinner.setSelection(0);
        classificationSpinner.setSelection(0);
        issuanceDateEditText.setText("");
        expiryDateEditText.setText("");
        if ("all".equalsIgnoreCase(drugType)) {
            drugTypeSpinner.setSelection(0);
        }
    }

    private void applyFilters() {
        DrugFilterType filter = new DrugFilterType();

        String country = countrySpinner.getSelectedItem() != null && !countrySpinner.getSelectedItem().toString().equalsIgnoreCase(countrySpinner.getItemAtPosition(0).toString()) ? countrySpinner.getSelectedItem().toString() : "";
        String classification = classificationSpinner.getSelectedItem() != null && !classificationSpinner.getSelectedItem().toString().equalsIgnoreCase(classificationSpinner.getItemAtPosition(0).toString()) ? classificationSpinner.getSelectedItem().toString() : "";
        String issuanceDate = issuanceDateEditText.getText().toString();
        String expiryDate = expiryDateEditText.getText().toString();

        if (!country.isEmpty()) filter.setFilter("country_of_origin", country);
        if (!classification.isEmpty()) filter.setFilter("classification", classification);
        if (!issuanceDate.isEmpty()) filter.setFilter("issuance_date", issuanceDate);
        if (!expiryDate.isEmpty()) filter.setFilter("expiry_date", expiryDate);
        if ("all".equalsIgnoreCase(drugType)) {
            String selectedDrugType = drugTypeSpinner.getSelectedItem().toString();
            if (!"All".equalsIgnoreCase(selectedDrugType)) {
                filter.setFilter("drug_type", selectedDrugType);
            }
        } else {
            filter.setFilter("drug_type", drugType);
        }

        // Pass the filter back to the activity
        if (listener != null) {
            listener.onFilterApplied(filter);
        }
        dismiss();
    }
}
