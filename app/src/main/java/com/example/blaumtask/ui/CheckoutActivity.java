package com.example.blaumtask.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.blaumtask.R;

public class CheckoutActivity extends AppCompatActivity {
    Button placeOrderButton;

    ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        backButton = findViewById(R.id.back_arrow);
        placeOrderButton = findViewById(R.id.place_order);
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentFinishOrder = new Intent(CheckoutActivity.this,FinishOrderActivity.class);
                startActivity(intentFinishOrder);
            }
        });

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