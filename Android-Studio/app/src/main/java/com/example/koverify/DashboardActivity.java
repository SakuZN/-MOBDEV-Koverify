package com.example.koverify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashboardActivity extends AppCompatActivity {
    LinearLayout scanBarcodeButton, viewAllProductsButton, allFoodProductsButton, safeFoodButton, mediumRiskFoodButton,
            highRiskFoodButton, allDrugProductsButton, humanDrugsButton, veterinaryDrugsButton;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        scanBarcodeButton = findViewById(R.id.scanBarcodeButton);
        viewAllProductsButton = findViewById(R.id.viewAllProductsButton);
        allFoodProductsButton = findViewById(R.id.allFoodProductsButton);
        safeFoodButton = findViewById(R.id.safeFoodButton);
        mediumRiskFoodButton = findViewById(R.id.mediumRiskFoodButton);
        highRiskFoodButton = findViewById(R.id.highRiskFoodButton);
        allDrugProductsButton = findViewById(R.id.allDrugProductsButton);
        humanDrugsButton = findViewById(R.id.humanDrugsButton);
        veterinaryDrugsButton = findViewById(R.id.veterinaryDrugsButton);

        // Set OnClickListener for each button
        setButtonClickListener(scanBarcodeButton);
        setButtonClickListener(viewAllProductsButton);
        setButtonClickListener(allFoodProductsButton);
        setButtonClickListener(safeFoodButton);
        setButtonClickListener(mediumRiskFoodButton);
        setButtonClickListener(highRiskFoodButton);
        setButtonClickListener(allDrugProductsButton);
        setButtonClickListener(humanDrugsButton);
        setButtonClickListener(veterinaryDrugsButton);
    }

    // Method to handle the click and start the ProductListActivity
    private void setButtonClickListener(LinearLayout button) {
        button.setOnClickListener(view -> {
            // Start ProductListActivity when any button is clicked
            intent = new Intent(DashboardActivity.this, ProductListActivity.class);
            startActivity(intent);
        });
    }
}