package com.example.blaumtask.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.blaumtask.R;

public class SplashActivity extends AppCompatActivity {
    private ImageView gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initViews();
        handleGif(LoginActivity.class,2500,"splash");
    }

    private void initViews(){
        gif = findViewById(R.id.splash_gif);
        Glide.with(this).asGif().load(R.raw.animation).into(gif);

    }

    public void handleGif(Class activity, long delay, String extras){
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(this,activity);
            Bundle extra = new Bundle();
            extra.putString("activity", extras);
            intent.putExtras(extra);
            startActivity(intent);
            finish();
        },delay);
    }
}