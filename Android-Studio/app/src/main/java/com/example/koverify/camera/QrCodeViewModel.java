package com.example.koverify.camera;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;

import com.example.koverify.product_list.drugs.DrugProductListActivity;
import com.example.koverify.product_list.food.FoodProductListActivity;
import com.google.mlkit.vision.barcode.common.Barcode;

/**
 * A ViewModel for encapsulating the data for a Barcode, including the encoded data,
 * the bounding box, and the touch behavior on the Barcode.
 */
public class QrCodeViewModel {
    private Rect boundingRect;
    private final String qrContent;
    private final View.OnTouchListener qrCodeTouchCallback;

    public QrCodeViewModel(Barcode barcode) {
        if (barcode.getBoundingBox() != null) {
            this.boundingRect = barcode.getBoundingBox();
        } else {
            this.boundingRect = new Rect();
        }

        switch (barcode.getFormat()) {
            case Barcode.FORMAT_CODE_39:
            case Barcode.FORMAT_CODE_93:
            case Barcode.FORMAT_CODE_128:
            case Barcode.FORMAT_EAN_13:
            case Barcode.TYPE_PRODUCT:
                System.out.println("Product Code: " + barcode.getRawValue());
                if (barcode.getRawValue() != null) {
                    this.qrContent = barcode.getRawValue();
                    this.qrCodeTouchCallback = createProductTouchListener(qrContent);
                } else {
                    this.qrContent = "Invalid Product Code";
                    this.qrCodeTouchCallback = null;
                }
                break;
            case Barcode.TYPE_SMS:
                if (barcode.getSms() != null && barcode.getSms().getMessage() != null) {
                    this.qrContent = barcode.getSms().getMessage();
                    this.qrCodeTouchCallback = createSmsTouchListener(qrContent);
                } else {
                    this.qrContent = "Invalid SMS Code";
                    this.qrCodeTouchCallback = null;
                }
                break;
            default:
                this.qrContent = "Unsupported data type: " + (barcode.getRawValue() != null ? barcode.getRawValue() : "null");
                this.qrCodeTouchCallback = null;
                break;
        }
    }

    private View.OnTouchListener createUrlTouchListener(final String url) {
        return new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN &&
                        boundingRect.contains((int) event.getX(), (int) event.getY())) {
                    Intent openBrowserIntent = new Intent(Intent.ACTION_VIEW);
                    openBrowserIntent.setData(Uri.parse(url));
                    v.getContext().startActivity(openBrowserIntent);
                }
                return true; // Event handled
            }
        };
    }

    private View.OnTouchListener createProductTouchListener(final String productCode) {
        return new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN &&
                        boundingRect.contains((int) event.getX(), (int) event.getY())) {
                    // Handle product barcode tap (e.g., navigate to product details)
                    Intent productDetailIntent = new Intent(v.getContext(), DrugProductListActivity.class);
                    productDetailIntent.putExtra("PRODUCT_CODE", productCode);
                    v.getContext().startActivity(productDetailIntent);
                }
                return true; // Event handled
            }
        };
    }

    private View.OnTouchListener createEmailTouchListener(final String email) {
        return new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN &&
                        boundingRect.contains((int) event.getX(), (int) event.getY())) {
                    // Handle Email barcode tap (e.g., open email client)
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse("mailto:" + email));
                    v.getContext().startActivity(emailIntent);
                }
                return true; // Event handled
            }
        };
    }

    private View.OnTouchListener createPhoneTouchListener(final String phoneNumber) {
        return new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN &&
                        boundingRect.contains((int) event.getX(), (int) event.getY())) {
                    // Handle Phone barcode tap (e.g., open dialer)
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                    dialIntent.setData(Uri.parse("tel:" + phoneNumber));
                    v.getContext().startActivity(dialIntent);
                }
                return true; // Event handled
            }
        };
    }

    private View.OnTouchListener createSmsTouchListener(final String message) {
        return new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN &&
                        boundingRect.contains((int) event.getX(), (int) event.getY())) {
                    // Handle SMS barcode tap (e.g., open SMS client with pre-filled message)
                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                    smsIntent.setData(Uri.parse("sms:"));
                    smsIntent.putExtra("sms_body", message);
                    v.getContext().startActivity(smsIntent);
                }
                return true; // Event handled
            }
        };
    }

    public Rect getBoundingRect() {
        return boundingRect;
    }

    public void setBoundingRect(Rect transformedRect) {
        this.boundingRect = transformedRect;
    }

    public String getQrContent() {
        return qrContent;
    }

    public View.OnTouchListener getQrCodeTouchCallback() {
        return qrCodeTouchCallback;
    }
}
