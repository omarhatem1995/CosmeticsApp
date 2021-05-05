package com.example.blaumtask.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.blaumtask.R;
import com.example.blaumtask.models.MyOrdersModel;
import com.example.blaumtask.presenter.checkoutpresenter.CheckoutPresenter;
import com.example.blaumtask.presenter.checkoutpresenter.CheckoutPresenterListener;
import com.example.blaumtask.utils.SpinnerDialog;

import java.util.List;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener , CheckoutPresenterListener {
    private Button placeOrderButton;
    private ImageView backButton;

    private String TAG = "CheckOutActivity";
    private SpinnerDialog spinnerDialog;

    private CheckoutPresenter checkoutPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        initViews();

    }

    private void initViews(){
        spinnerDialog = new SpinnerDialog(this);
        checkoutPresenter = new CheckoutPresenter(this,this,this);

        backButton = findViewById(R.id.back_arrow);
        placeOrderButton = findViewById(R.id.place_order);

        checkoutPresenter.updateUsersBasket();

        placeOrderButton.setOnClickListener(this);

        backButton.setBackgroundResource(R.drawable.ic_backarrow);
        backButton.setRotation(180);
        backButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.place_order:
                checkoutPresenter.openFinishActivity();
                break;
            case R.id.back_arrow:
                checkoutPresenter.finishActivity();
                break;
        }
    }

    @Override
    public void showProgress() {
        spinnerDialog.show();
    }


    @Override
    public void hideProgress() {
        if(spinnerDialog!=null && spinnerDialog.isShowing())
        spinnerDialog.dismiss();
    }
}