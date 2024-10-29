package com.example.koverify;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.pm.PackageManager;
import android.widget.RelativeLayout;

import com.example.koverify.camera.CameraActivity;
import com.example.koverify.camera.PermissionsActivity;
import com.example.koverify.product_list.drugs.DrugProductListActivity;
import com.example.koverify.product_list.food.FoodProductListActivity;

public class DashboardActivity extends AppCompatActivity {
    LinearLayout scanBarcodeButton, allFoodProductsButton, safeFoodButton, mediumRiskFoodButton,
            highRiskFoodButton, rawFoodButton, allDrugProductsButton, humanDrugsButton, veterinaryDrugsButton;
    RelativeLayout headerScanButton;

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

        // Initialize buttons
        scanBarcodeButton = findViewById(R.id.scanBarcodeButton);
        allFoodProductsButton = findViewById(R.id.allFoodProductsButton);
        safeFoodButton = findViewById(R.id.safeFoodButton);
        mediumRiskFoodButton = findViewById(R.id.mediumRiskFoodButton);
        highRiskFoodButton = findViewById(R.id.highRiskFoodButton);
        rawFoodButton = findViewById(R.id.rawFoodButton);
        allDrugProductsButton = findViewById(R.id.allDrugProductsButton);
        humanDrugsButton = findViewById(R.id.humanDrugsButton);
        veterinaryDrugsButton = findViewById(R.id.veterinaryDrugsButton);
        headerScanButton = findViewById(R.id.headerScanButton);



        // Set OnClickListener specifically for scanBarcodeButton
        scanBarcodeButton.setOnClickListener(view -> handleScanBarcode());
        headerScanButton.setOnClickListener(view -> handleScanBarcode());

        // Set OnClickListener for drug-related buttons
        allDrugProductsButton.setOnClickListener(view -> openDrugProductListActivity("all"));
        humanDrugsButton.setOnClickListener(view -> openDrugProductListActivity("human"));
        veterinaryDrugsButton.setOnClickListener(view -> openDrugProductListActivity("vet"));

        // Set OnClickListener for food-related buttons
        allFoodProductsButton.setOnClickListener(view -> openFoodProductListActivity("all"));
        safeFoodButton.setOnClickListener(view -> openFoodProductListActivity("l_risk"));
        mediumRiskFoodButton.setOnClickListener(view -> openFoodProductListActivity("m_risk"));
        highRiskFoodButton.setOnClickListener(view -> openFoodProductListActivity("h_risk"));
        rawFoodButton.setOnClickListener(view -> openFoodProductListActivity("raw"));
    }

    private void openDrugProductListActivity(String drugType) {
        Intent intent = new Intent(DashboardActivity.this, DrugProductListActivity.class);
        intent.putExtra("drug_type", drugType);
        startActivity(intent);
    }
    private void openFoodProductListActivity(String foodType) {
        Intent intent = new Intent(DashboardActivity.this, FoodProductListActivity.class);
        intent.putExtra("food_type", foodType);
        startActivity(intent);
    }

    // Method to handle Scan Barcode button click
    private void handleScanBarcode() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            navigateToCamera();
        } else {
            navigateToPermissions();
        }
    }

    // Navigate to PermissionsActivity
    private void navigateToPermissions() {
        Intent intent = new Intent(DashboardActivity.this, PermissionsActivity.class);
        startActivity(intent);
    }

    // Navigate to CameraActivity
    private void navigateToCamera() {
        Intent intent = new Intent(DashboardActivity.this, CameraActivity.class);
        startActivity(intent);
    }
}
