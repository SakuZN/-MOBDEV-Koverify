package com.example.koverify;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.TextView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.koverify.database.drugs.DrugProductAdapter;

public class DrugProductListActivity extends AppCompatActivity {

    private String drugType;
    private TextView topbarHeader;
    private RecyclerView recyclerView;
    private DrugProductAdapter adapter;
    private DrugProductListViewModel viewModel;

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

        // Observe LiveData
        viewModel.getDrugProductsLiveData().observe(this, drugProducts -> {
            System.out.println("LiveData updated: " + drugProducts);
            adapter.submitList(drugProducts);
        });

        // Implement pagination
        implementPagination();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateToDashboard();
            }
        });

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
}