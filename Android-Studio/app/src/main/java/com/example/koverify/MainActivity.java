package com.example.koverify;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView prompt;
    Button nextButton;
    int index = 0;

    String[] prompts = {
            "Welcome to Koverify!",
            "Find your products’ nutritional data fast",
            "Scan a barcode or search for your item",
            "⚠️ Not made to replace professional medical help"
    };

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
    }

    public void nextPrompt(View view) {
        if (index != prompts.length - 1) {
            index++;
            prompt.setText(prompts[index]);
            if (index != 0) {
                nextButton.setText("Continue");
            }
        }
        else {
            // add new activity here
            finish();
        }
    }
}