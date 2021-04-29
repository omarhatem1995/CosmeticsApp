package com.example.blaumtask.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.blaumtask.R;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private ImageView gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initViews();
    }

    private void initViews(){
        mAuth = FirebaseAuth.getInstance();

        if(mAuth!=null)
            handleGif(MainActivity.class,2500,"splash");
        else
            handleGif(LoginActivity.class,2500,"splash");

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