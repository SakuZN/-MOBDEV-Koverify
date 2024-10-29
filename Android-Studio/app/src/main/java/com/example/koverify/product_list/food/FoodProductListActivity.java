package com.example.koverify.product_list.food;
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

public class FoodProductListActivity extends AppCompatActivity {
    private String foodType;
    private TextView topbarHeader;
    private RecyclerView recyclerView;
    private FoodProductAdapter adapter;
    private FoodProductListViewModel viewModel;
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
        setContentView(R.layout.activity_food_product_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        foodType = getIntent().getStringExtra("food_type");
        if (foodType == null) {
            foodType = "all";
        }

        topbarHeader = findViewById(R.id.topbarHeader);
        recyclerView = findViewById(R.id.recyclerView);
        backButton = findViewById(R.id.prodBackButton);
        searchEditText = findViewById(R.id.searchFoodBar);
        headerFilterButton = findViewById(R.id.headerFilterButton);
        progressBar = findViewById(R.id.progressBar);
        emptyView = findViewById(R.id.emptyView);


        // Update header text based on drug type
        switch (foodType) {
            case "h_risk":
                topbarHeader.setText("High Risk Food Products");
                break;
            case "m_risk":
                topbarHeader.setText("Medium Risk Food Products");
                break;
            case "l_risk":
                topbarHeader.setText("Low Risk Food Products");
                break;
            case "raw":
                topbarHeader.setText("Raw Food Products");
                break;
            default:
                topbarHeader.setText("All Food Products");
                break;
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FoodProductAdapter();
        recyclerView.setAdapter(adapter);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(FoodProductListViewModel.class);
        viewModel.init(foodType);

        viewModel.getFoodProductsLiveData().observe(this, foodProducts -> {
            adapter.submitList(foodProducts);
            if (foodProducts == null || foodProducts.isEmpty()) {
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

        adapter.setOnItemClickListener(foodProduct -> {
            String regNum = foodProduct.getReg_num();
            String name = foodProduct.getBrand_name();
            System.out.println("Clicked: " + regNum + " - " + name);

            // Create and show the modal dialog
            showProductDetailsDialog(regNum);
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
            FoodFilterDialog dialog = FoodFilterDialog.newInstance(foodType, filterParam);
            dialog.setFilterDialogListener(filter -> {
                System.out.println("Filter: " + filter);
                viewModel.setFilterParam(filter);
                filterParam.setFilters(filter.getFilters());
                recyclerView.scrollToPosition(0);
            });
            dialog.show(getSupportFragmentManager(), "FoodFilterDialog");
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
                    viewModel.loadMoreFoodProducts(() -> isLoading = false);
                }
            }
        });
    }

    private void navigateToDashboard() {
        Intent intent = new Intent(FoodProductListActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void showProductDetailsDialog(String regNum) {
        FoodProductDetailsDialog dialog = FoodProductDetailsDialog.newInstance(regNum);
        dialog.show(getSupportFragmentManager(), "FoodProductDetailsDialog");
    }

}
