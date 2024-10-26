package com.example.koverify;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView prompt;
    private Button nextButton, skipButton;
    private int index = 0;

    private final String[] prompts = {
            "Welcome to Koverify",
            "Find your products’ nutritional data fast",
            "Scan a barcode or search for your item",
            "⚠️ Not made to replace professional medical help"
    };

    // Circle indicators
    private View[] circles = new View[4];
    private final int[] circleColors = {
            Color.parseColor("#9B9CA8"), // Inactive
            Color.parseColor("#C8CADB")  // Active
    };
    private ValueAnimator colorAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        prompt = findViewById(R.id.prompt);
        nextButton = findViewById(R.id.next);
        skipButton = findViewById(R.id.skip);

        // Initialize circle indicators
        circles[0] = findViewById(R.id.circle0);
        circles[1] = findViewById(R.id.circle1);
        circles[2] = findViewById(R.id.circle2);
        circles[3] = findViewById(R.id.circle3);

        // Set initial active circle
        updateCircleIndicators();

        // Set onClick listeners
        nextButton.setOnClickListener(view -> changeText());

        skipButton.setOnClickListener(view -> navigateToDashboard());
    }

    private void changeText() {
        if (index < prompts.length - 1) {
            animateTextChange(prompts[index + 1]);
            index++;
            updateNextButtonText();
            updateCircleIndicators();
        } else {
            navigateToDashboard();
        }
    }

    private void animateTextChange(final String newText) {
        // Slide out to the left
        ObjectAnimator slideOut = ObjectAnimator.ofFloat(prompt, "translationX", 0f, -300f);
        slideOut.setDuration(300);
        slideOut.setInterpolator(new AccelerateDecelerateInterpolator());

        // After slide out, change text and slide in from right
        slideOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                prompt.setText(newText);
                ObjectAnimator slideIn = ObjectAnimator.ofFloat(prompt, "translationX", 300f, 0f);
                slideIn.setDuration(300);
                slideIn.setInterpolator(new AccelerateDecelerateInterpolator());
                slideIn.start();
            }
        });

        slideOut.start();
    }

    private void updateNextButtonText() {
        if (index == 0) {
            nextButton.setText("Get Started");
        } else {
            nextButton.setText("Continue");
        }
    }

    private void updateCircleIndicators() {
        for (int i = 0; i < circles.length; i++) {
            if (i == index) {
                animateCircleColor(circles[i], circleColors[0], circleColors[1]);
            } else {
                animateCircleColor(circles[i], circleColors[1], circleColors[0]);
            }
        }
    }

    private void animateCircleColor(final View circle, int startColor, int endColor) {
        if (colorAnimator != null && colorAnimator.isRunning()) {
            colorAnimator.cancel();
        }

        colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor);
        colorAnimator.setDuration(300); // Duration similar to React Native's withTiming
        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                circle.setBackgroundColor((int) animator.getAnimatedValue());
            }
        });
        colorAnimator.start();
    }

    private void navigateToDashboard() {
        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
