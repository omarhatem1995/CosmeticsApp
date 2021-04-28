package com.example.blaumtask.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.blaumtask.R;
import com.github.ybq.android.spinkit.SpinKitView;

public class SpinnerDialog extends Dialog {
    Context context;

    public SpinnerDialog(Context context) {
        super(context);
        this.context = context;


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_loading_screen);
        this.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        SpinKitView spin_kit = this.findViewById(R.id.spin_kit);
        this.setCancelable(false);


    }

    @Override
    public void hide() {
        super.hide();

    }
}