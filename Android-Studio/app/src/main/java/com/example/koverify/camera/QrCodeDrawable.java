package com.example.koverify.camera;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * A Drawable that handles displaying a Barcode's data and a bounding box around the Barcode.
 */
public class QrCodeDrawable extends Drawable {
    private final Paint boundingRectPaint;
    private final Paint contentRectPaint;
    private final Paint contentTextPaint;

    private final QrCodeViewModel qrCodeViewModel;
    private final int textWidth;
    private int contentPadding = 25;

    public QrCodeDrawable(QrCodeViewModel qrCodeViewModel) {
        this.qrCodeViewModel = qrCodeViewModel;

        // Initialize Paint for bounding rectangle
        boundingRectPaint = new Paint();
        boundingRectPaint.setStyle(Paint.Style.STROKE);
        boundingRectPaint.setStrokeWidth(5f);

        // Differentiate colors based on barcode type
        if (qrCodeViewModel.getQrContent().startsWith("http")) {
            boundingRectPaint.setColor(Color.GREEN); // Green for QR Codes (URLs)
        } else {
            boundingRectPaint.setColor(Color.YELLOW); // Yellow for Product Barcodes
        }
        boundingRectPaint.setAlpha(200);

        // Initialize Paint for content background rectangle
        contentRectPaint = new Paint();
        contentRectPaint.setStyle(Paint.Style.FILL);
        contentRectPaint.setColor(Color.YELLOW);
        contentRectPaint.setAlpha(255);

        // Initialize Paint for content text
        contentTextPaint = new Paint();
        contentTextPaint.setColor(Color.DKGRAY);
        contentTextPaint.setAlpha(255);
        contentTextPaint.setTextSize(36f);

        if (qrCodeViewModel.getQrContent() != null) {
            textWidth = (int) contentTextPaint.measureText(qrCodeViewModel.getQrContent());
        } else {
            textWidth = 0;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Rect boundingRect = qrCodeViewModel.getBoundingRect();

        // Draw bounding rectangle
        canvas.drawRect(boundingRect, boundingRectPaint);

        // Draw content background rectangle
        canvas.drawRect(
                boundingRect.left,
                boundingRect.bottom + contentPadding / 2,
                boundingRect.left + textWidth + contentPadding * 2,
                boundingRect.bottom + (int) contentTextPaint.getTextSize() + contentPadding,
                contentRectPaint
        );

        // Draw Barcode content text
        canvas.drawText(
                qrCodeViewModel.getQrContent(),
                boundingRect.left + contentPadding,
                boundingRect.bottom + contentPadding * 2,
                contentTextPaint
        );
    }

    @Override
    public void setAlpha(int alpha) {
        boundingRectPaint.setAlpha(alpha);
        contentRectPaint.setAlpha(alpha);
        contentTextPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        boundingRectPaint.setColorFilter(colorFilter);
        contentRectPaint.setColorFilter(colorFilter);
        contentTextPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return android.graphics.PixelFormat.TRANSLUCENT;
    }
}
