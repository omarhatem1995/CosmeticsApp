package com.example.blaumtask.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.blaumtask.R;
import com.example.blaumtask.presenter.myorderspresenter.MyOrdersPresenter;
import com.example.blaumtask.presenter.myorderspresenter.MyOrdersPresenterListener;
import com.example.blaumtask.utils.SpinnerDialog;

public class MyOrdersActivity extends AppCompatActivity implements
        View.OnClickListener,
        MyOrdersPresenterListener {
    private ImageView backButton;
    private Button checkout;

    private SpinnerDialog spinnerDialog;

    private MyOrdersPresenter myOrdersPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__orders);

        initView();
        spinnerDialog = new SpinnerDialog(this);
        myOrdersPresenter = new MyOrdersPresenter(this,this,this);

        myOrdersPresenter.getBasketList();

        backButton.setOnClickListener(this);
        checkout.setOnClickListener(this);
        myOrdersPresenter.updateUsersBasket();


    }

    private void initView(){
        backButton = findViewById(R.id.back_arrow);
        checkout = findViewById(R.id.checkout);

        backButton.setBackgroundResource(R.drawable.ic_backarrow);
        backButton.setRotation(180);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.checkout:
                myOrdersPresenter.checkOut();
                break;
            case R.id.back_arrow:
                myOrdersPresenter.finishActivity();
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