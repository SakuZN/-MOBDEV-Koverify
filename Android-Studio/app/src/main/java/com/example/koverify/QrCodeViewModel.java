package com.example.koverify;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;

import com.google.mlkit.vision.barcode.common.Barcode;

/**
 * A ViewModel for encapsulating the data for a Barcode, including the encoded data,
 * the bounding box, and the touch behavior on the Barcode.
 */
public class QrCodeViewModel {
    private final Rect boundingRect;
    private final String qrContent;
    private final View.OnTouchListener qrCodeTouchCallback;

    public QrCodeViewModel(Barcode barcode) {
        if (barcode.getBoundingBox() != null) {
            this.boundingRect = barcode.getBoundingBox();
        } else {
            this.boundingRect = new Rect();
        }

        switch (barcode.getValueType()) {
            case Barcode.TYPE_URL:
                if (barcode.getUrl() != null && barcode.getUrl().getUrl() != null) {
                    this.qrContent = barcode.getUrl().getUrl();
                    this.qrCodeTouchCallback = new View.OnTouchListener() {
                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_DOWN &&
                                    boundingRect.contains((int) event.getX(), (int) event.getY())) {
                                Intent openBrowserIntent = new Intent(Intent.ACTION_VIEW);
                                openBrowserIntent.setData(Uri.parse(qrContent));
                                v.getContext().startActivity(openBrowserIntent);
                            }
                            return true; // Event handled
                        }
                    };
                } else {
                    this.qrContent = "Invalid URL";
                    this.qrCodeTouchCallback = null;
                }
                break;

            case Barcode.TYPE_PRODUCT:
                if (barcode.getRawValue() != null) {
                    this.qrContent = barcode.getRawValue();
                    this.qrCodeTouchCallback = new View.OnTouchListener() {
                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_DOWN &&
                                    boundingRect.contains((int) event.getX(), (int) event.getY())) {
                                // Handle product barcode tap (e.g., navigate to product details)
                                Intent productDetailIntent = new Intent(v.getContext(), ProductListActivity.class);
                                productDetailIntent.putExtra("PRODUCT_CODE", qrContent);
                                v.getContext().startActivity(productDetailIntent);
                            }
                            return true; // Event handled
                        }
                    };
                } else {
                    this.qrContent = "Invalid Product Code";
                    this.qrCodeTouchCallback = null;
                }
                break;

            // Add other Barcode types here if needed

            default:
                this.qrContent = "Unsupported data type: " + (barcode.getRawValue() != null ? barcode.getRawValue() : "null");
                this.qrCodeTouchCallback = null;
                break;
        }
    }

    public Rect getBoundingRect() {
        return boundingRect;
    }

    public String getQrContent() {
        return qrContent;
    }

    public View.OnTouchListener getQrCodeTouchCallback() {
        return qrCodeTouchCallback;
    }
}
