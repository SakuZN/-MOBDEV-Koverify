package com.example.koverify.camera;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.ExperimentalGetImage;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.koverify.DashboardActivity;
import com.example.koverify.LoadingDialog;
import com.example.koverify.R;
import com.example.koverify.product_list.drugs.DrugProductDetailsDialog;
import com.example.koverify.product_list.food.FoodProductDetailsDialog;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraActivity extends AppCompatActivity {
    private PreviewView previewView;
    private ImageView scanAreaIcon;
    private QrCodeDrawable qrCodeDrawable;
    private QrCodeViewModel qrCodeViewModel;
    private BarcodeScanner barcodeScanner;
    private boolean isScanning = false;
    private ProductViewModel productViewModel;
    private final ExecutorService cameraExecutor = Executors.newSingleThreadExecutor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_camera);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cameraMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        previewView = findViewById(R.id.previewView);
        scanAreaIcon = findViewById(R.id.scanAreaIcon);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateToDashboard();
            }
        });

        // Initialize ML Kit Barcode Scanner with QR code format
        BarcodeScannerOptions options = new BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                        Barcode.FORMAT_CODE_128,
                        Barcode.FORMAT_CODE_39,
                        Barcode.FORMAT_EAN_13,
                        Barcode.TYPE_PRODUCT
                )
                .build();
        barcodeScanner = BarcodeScanning.getClient(options);

        // Start CameraX
        startCamera();
    }

    private void navigateToDashboard() {
        Intent intent = new Intent(CameraActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void startCamera() {
        // Obtain a ProcessCameraProvider instance
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                // CameraProvider
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                // Bind the camera lifecycle and use cases
                bindPreviewAndAnalysis(cameraProvider);

            } catch (ExecutionException | InterruptedException e) {
                // Handle any errors (including cancellation) here
                e.printStackTrace();
                Toast.makeText(this, "Error starting camera: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    @OptIn(markerClass = ExperimentalGetImage.class)
    private void bindPreviewAndAnalysis(@NonNull ProcessCameraProvider cameraProvider) {
        // Preview use case
        Preview preview = new Preview.Builder()
                .build();

        // ImageAnalysis use case for barcode scanning
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        // Set up the analyzer
        imageAnalysis.setAnalyzer(cameraExecutor, imageProxy -> {
            if (imageProxy.getImage() == null) {
                imageProxy.close();
                return;
            }

            // Create an InputImage from the ImageProxy
            InputImage image = InputImage.fromMediaImage(imageProxy.getImage(), imageProxy.getImageInfo().getRotationDegrees());

            // Process the image using ML Kit
            barcodeScanner.process(image)
                    .addOnSuccessListener(barcodes -> {
                        if (!barcodes.isEmpty()) {
                            Barcode barcode = barcodes.get(0); // Assuming single barcode
                            System.out.println("Barcode detected: " + barcode.getRawValue());
                            if (barcode.getRawValue() != null && !isScanning) {
                                //updateUIWithQrCode(barcode);
                                showProductDetailsDialog(barcode.getRawValue());
                            }
                        } else {
                            clearQrCodeOverlay();
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Handle any errors
                        runOnUiThread(() ->
                                Toast.makeText(CameraActivity.this, "Barcode scanning failed", Toast.LENGTH_SHORT).show()
                        );
                        clearQrCodeOverlay();
                    })
                    .addOnCompleteListener(task -> {
                        // Close the imageProxy
                        imageProxy.close();
                    });
        });


        // Select back camera as default
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        // Connect the preview use case to the PreviewView
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        try {
            // Unbind use cases before rebinding
            cameraProvider.unbindAll();

            // Bind use cases to lifecycle
            cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Binding failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Updates the UI by highlighting the detected QR code and displaying its content.
     *
     * @param barcode The detected barcode.
     */
    private void updateUIWithQrCode(Barcode barcode) {
        // Initialize QrCodeViewModel
        qrCodeViewModel = new QrCodeViewModel(barcode);

        // Initialize QrCodeDrawable
        qrCodeDrawable = new QrCodeDrawable(qrCodeViewModel);

        // Clear any existing overlays
        previewView.getOverlay().clear();

        // Map the bounding rectangle to view coordinates
        Rect boundingRect = qrCodeViewModel.getBoundingRect();
        Rect transformedRect = transformRect(boundingRect);
        qrCodeViewModel.setBoundingRect(transformedRect);

        // Add the new drawable
        previewView.getOverlay().add(qrCodeDrawable);

        // Set the touch listener
        if (qrCodeViewModel.getQrCodeTouchCallback() != null) {
            previewView.setOnTouchListener(qrCodeViewModel.getQrCodeTouchCallback());
        }
    }

    private Rect transformRect(Rect barcodeRect) {
        // Get the dimensions of the PreviewView
        int previewWidth = previewView.getWidth();
        int previewHeight = previewView.getHeight();

        // Get the dimensions of the camera image
        int imageWidth = previewView.getWidth();
        int imageHeight = previewView.getHeight();

        // Calculate the scale factors
        float scaleX = (float) previewWidth / imageWidth;
        float scaleY = (float) previewHeight / imageHeight;

        // Apply scaling to the bounding rectangle
        int left = (int) (barcodeRect.left * scaleX);
        int top = (int) (barcodeRect.top * scaleY);
        int right = (int) (barcodeRect.right * scaleX);
        int bottom = (int) (barcodeRect.bottom * scaleY);

        return new Rect(left, top, right, bottom);
    }


    /**
     * Clears the QR code overlay from the UI.
     */
    private void clearQrCodeOverlay() {
        previewView.getOverlay().clear();
        previewView.setOnTouchListener(null); // Remove touch listener
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the barcode scanner
        barcodeScanner.close();
        cameraExecutor.shutdown();
    }

    private void showProductDetailsDialog(String sku) {
        if (isScanning) return; // Prevent multiple scans

        isScanning = true; // Set scanning flag

        LoadingDialog loadingDialog = LoadingDialog.newInstance();
        loadingDialog.show(getSupportFragmentManager(), "LoadingDialog");
        System.out.println("Here inside showProductDetailsDialog!");
        productViewModel.getProductType(sku).observe(this, productType -> {
            loadingDialog.dismiss(); // Dismiss loading spinner

            switch (productType) {
                case "Food":
                    showFoodProductDialog(sku);
                    break;
                case "HumanDrug":
                case "VetDrug":
                    showDrugProductDialog(sku, productType);
                    break;
                default:
                    Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show();
                    isScanning = false;
                    break;
            }
        });
    }

    private void showFoodProductDialog(String sku) {
        FoodProductDetailsDialog dialog = FoodProductDetailsDialog.newInstanceSKU(sku);
        dialog.show(getSupportFragmentManager(), "FoodProductDetailsDialog");
        dialog.setOnDismissListener(() -> isScanning = false);
    }

    private void showDrugProductDialog(String sku, String drugType) {
        DrugProductDetailsDialog dialog = DrugProductDetailsDialog.newInstanceSKU(sku, drugType);
        dialog.show(getSupportFragmentManager(), "DrugProductDetailsDialog");
        dialog.setOnDismissListener(() -> isScanning = false);
    }
}