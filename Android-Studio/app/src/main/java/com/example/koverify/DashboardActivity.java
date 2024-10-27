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

import com.example.koverify.camera.CameraActivity;
import com.example.koverify.camera.PermissionsActivity;
import com.example.koverify.product_list.drugs.DrugProductListActivity;

public class DashboardActivity extends AppCompatActivity {
    LinearLayout scanBarcodeButton, viewAllProductsButton, allFoodProductsButton, safeFoodButton, mediumRiskFoodButton,
            highRiskFoodButton, allDrugProductsButton, humanDrugsButton, veterinaryDrugsButton;

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
        viewAllProductsButton = findViewById(R.id.viewAllProductsButton);
        allFoodProductsButton = findViewById(R.id.allFoodProductsButton);
        safeFoodButton = findViewById(R.id.safeFoodButton);
        mediumRiskFoodButton = findViewById(R.id.mediumRiskFoodButton);
        highRiskFoodButton = findViewById(R.id.highRiskFoodButton);
        allDrugProductsButton = findViewById(R.id.allDrugProductsButton);
        humanDrugsButton = findViewById(R.id.humanDrugsButton);
        veterinaryDrugsButton = findViewById(R.id.veterinaryDrugsButton);

        // Set OnClickListener for general buttons
        setButtonClickListener(viewAllProductsButton);
        setButtonClickListener(allFoodProductsButton);
        setButtonClickListener(safeFoodButton);
        setButtonClickListener(mediumRiskFoodButton);
        setButtonClickListener(highRiskFoodButton);

        // Set OnClickListener specifically for scanBarcodeButton
        scanBarcodeButton.setOnClickListener(view -> handleScanBarcode());

        // Set OnClickListener for drug-related buttons
        allDrugProductsButton.setOnClickListener(view -> openDrugProductListActivity("all"));
        humanDrugsButton.setOnClickListener(view -> openDrugProductListActivity("human"));
        veterinaryDrugsButton.setOnClickListener(view -> openDrugProductListActivity("vet"));
    }

    // Method to handle the click and start the ProductListActivity
    private void setButtonClickListener(LinearLayout button) {
        button.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, DrugProductListActivity.class);
            startActivity(intent);
        });
    }

    private void openDrugProductListActivity(String drugType) {
        Intent intent = new Intent(DashboardActivity.this, DrugProductListActivity.class);
        intent.putExtra("drug_type", drugType);
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
