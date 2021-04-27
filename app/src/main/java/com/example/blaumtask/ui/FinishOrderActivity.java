package com.example.blaumtask.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.example.blaumtask.R;

public class FinishOrderActivity extends AppCompatActivity {

    CircleImageView circleImageView;
    ConstraintLayout backgroundFinishOrder;
    Button homeButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_order);

        circleImageView = findViewById(R.id.image_right);
        backgroundFinishOrder = findViewById(R.id.background_finish_order);
        homeButton = findViewById(R.id.home);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(FinishOrderActivity.this,MainActivity.class);
                finishAffinity();
                startActivity(homeIntent);
            }
        });
        Glide.with(getApplicationContext())
                .load(R.drawable.ic_resource_true).circleCrop()
                .into(circleImageView);
    }
}