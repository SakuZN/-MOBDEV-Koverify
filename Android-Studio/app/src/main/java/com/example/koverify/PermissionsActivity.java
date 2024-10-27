package com.example.koverify;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

public class PermissionsActivity extends AppCompatActivity {

    private Button grantCameraAccessButton;
    private TextView permissionDescription;
    private ImageView cameraIcon;

    private ActivityResultLauncher<String> requestPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_permissions);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.permissionsMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        grantCameraAccessButton = findViewById(R.id.grantCameraAccessButton);
        permissionDescription = findViewById(R.id.permissionDescription);
        cameraIcon = findViewById(R.id.cameraIcon);

        // Initialize the ActivityResultLauncher
        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        // Permission is granted, navigate to CameraActivity
                        navigateToCamera();
                    } else {
                        // Permission denied, show a message or navigate to app settings
                        Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
                        // Optionally, navigate to app settings
                        navigateToAppSettings();
                    }
                }
        );

        // Set up click listener for the grant button
        grantCameraAccessButton.setOnClickListener(view -> checkAndRequestPermission());
    }

    // Method to check and request camera permission
    private void checkAndRequestPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already granted
            navigateToCamera();
        } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
            // Show an educational UI explaining why the app needs camera permission
            showPermissionRationale();
        } else {
            // Directly request the camera permission
            requestCameraPermission();
        }
    }

    private void showPermissionRationale() {
        new AlertDialog.Builder(this)
                .setTitle("Camera Permission Needed")
                .setMessage("Koverify needs access to your camera to scan QR codes. Please grant camera access.")
                .setPositiveButton("Grant", (dialog, which) -> requestCameraPermission())
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }


    // Request camera permission using ActivityResultLauncher
    private void requestCameraPermission() {
        requestPermissionLauncher.launch(android.Manifest.permission.CAMERA);
    }

    // Navigate to CameraActivity
    private void navigateToCamera() {
        Intent intent = new Intent(PermissionsActivity.this, CameraActivity.class);
        startActivity(intent);
        finish();
    }

    // Navigate to App Settings to manually grant permission
    private void navigateToAppSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
        finish();
    }
}