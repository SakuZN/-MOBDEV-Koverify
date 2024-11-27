package com.example.koverify.product_list.drugs;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.koverify.DashboardActivity;
import com.example.koverify.R;
import com.example.koverify.database.FilterParam;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Timer;
import java.util.TimerTask;

public class DrugProductListActivity extends AppCompatActivity {

    private String drugType;
    private TextView topbarHeader;
    private RecyclerView recyclerView;
    private DrugProductAdapter adapter;
    private DrugProductListViewModel viewModel;
    private ImageView backButton;

    private TextInputEditText searchEditText;
    private RelativeLayout headerFilterButton;
    private ProgressBar progressBar;
    private TextView emptyView;
    private FilterParam filterParam = new FilterParam();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_drug_product_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        drugType = getIntent().getStringExtra("drug_type");
        if (drugType == null) {
            drugType = "all";
        }

        topbarHeader = findViewById(R.id.topbarHeader);
        recyclerView = findViewById(R.id.recyclerView);
        backButton = findViewById(R.id.prodBackButton);
        searchEditText = findViewById(R.id.searchDrugBar);
        headerFilterButton = findViewById(R.id.headerFilterButton);
        progressBar = findViewById(R.id.progressBar);
        emptyView = findViewById(R.id.emptyView);


        // Update header text based on drug type
        switch (drugType) {
            case "human":
                topbarHeader.setText("Human Drugs");
                break;
            case "vet":
                topbarHeader.setText("Veterinary Drugs");
                break;
            default:
                topbarHeader.setText("All Drug Products");
                break;
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DrugProductAdapter();
        recyclerView.setAdapter(adapter);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(DrugProductListViewModel.class);
        viewModel.init(drugType);

        viewModel.getDrugProductsLiveData().observe(this, drugProducts -> {
            adapter.submitList(drugProducts);
            if (drugProducts == null || drugProducts.isEmpty()) {
                emptyView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                emptyView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
        viewModel.getIsLoadingLiveData().observe(this, isLoading -> {
            if (isLoading != null) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });

        adapter.setOnItemClickListener(drugItem -> {
            String regNum = drugItem.getReg_num();
            String drugType = drugItem.getDrug_type();
            String name = drugItem.getBrand_name();
            System.out.println("Clicked: " + regNum + " - " + name);

            // Create and show the modal dialog
            showProductDetailsDialog(regNum, drugType);
        });

        // Debounce the search input
        searchEditText.addTextChangedListener(new TextWatcher() {
            private Timer timer = new Timer();
            private final long DELAY = 300; // milliseconds

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // Run on UI thread
                        runOnUiThread(() -> {
                            viewModel.setSearchQuery(s.toString());
                            recyclerView.scrollToPosition(0);
                        });
                    }
                }, DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        headerFilterButton.setOnClickListener(v -> {
            DrugFilterDialog dialog = DrugFilterDialog.newInstance(drugType, filterParam);
            dialog.setFilterDialogListener(filter -> {
                System.out.println("Filter: " + filter);
                viewModel.setFilterParam(filter);
                filterParam.setFilters(filter.getFilters());
                recyclerView.scrollToPosition(0);
            });
            dialog.show(getSupportFragmentManager(), "DrugFilterDialog");
        });




        // Implement pagination
        implementPagination();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateToDashboard();
            }
        });

        backButton.setOnClickListener(v -> navigateToDashboard());

    }

    private void implementPagination() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean isLoading = false;
            private final int visibleThreshold = 5;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy <= 0) return; // Ignore upward scroll

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager == null) return;

                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItemPosition + visibleThreshold)) {
                    isLoading = true;
                    viewModel.loadMoreDrugProducts(() -> isLoading = false);
                }
            }
        });
    }

    private void navigateToDashboard() {
        Intent intent = new Intent(DrugProductListActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void showProductDetailsDialog(String regNum, String drugType) {
        DrugProductDetailsDialog dialog = DrugProductDetailsDialog.newInstance(regNum, drugType);
        dialog.show(getSupportFragmentManager(), "DrugProductDetailsDialog");
    }

}