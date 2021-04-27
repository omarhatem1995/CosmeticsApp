package com.example.blaumtask.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.blaumtask.R;

public class CartSettingsActivity extends AppCompatActivity {

    ImageView backButton;
    LinearLayout background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_settings);

        backButton = findViewById(R.id.back_arrow);
        background = findViewById(R.id.settings_background);

        backButton.setBackgroundResource(R.drawable.ic_backarrow);
        backButton.setRotation(180);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}