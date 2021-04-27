package com.example.blaumtask.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blaumtask.R;
import com.example.blaumtask.ui.adapter.MyOrdersAdapter;
import com.example.blaumtask.ui.adapter.RecyclerViewClickListenerDecrement;
import com.example.blaumtask.ui.adapter.RecyclerViewClickListenerIncrement;
import com.example.blaumtask.ui.adapter.ReviewsAdapter;
import com.example.blaumtask.ui.models.MyOrdersModel;
import com.example.blaumtask.ui.models.ReviewsModel;

import java.util.ArrayList;
import java.util.List;

public class My_Orders extends AppCompatActivity implements RecyclerViewClickListenerIncrement,
        RecyclerViewClickListenerDecrement {
    ImageView backButton;
    MyOrdersAdapter myOrdersAdapter;
    RecyclerView recyclerView;
    List<MyOrdersModel> myOrdersModelList;
    Button checkout;
    private TextView totalText , totalItemsCounter;
    int firstPrice =30 , secondPrice =40, thirdPrice =50, fourthPrice = 80 , fifthPrice = 90 , sixthPrice =200;
    int total ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__orders);

        backButton = findViewById(R.id.back_arrow);
        recyclerView = findViewById(R.id.recyclerview_my_orders);
        totalText = findViewById(R.id.total_text);
        checkout = findViewById(R.id.checkout);
        totalItemsCounter = findViewById(R.id.cart_my_orders_counter);

        backButton.setBackgroundResource(R.drawable.ic_backarrow);
        backButton.setRotation(180);
        getReviews();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCheckout = new Intent(My_Orders.this,CheckoutActivity.class);
                startActivity(intentCheckout);
            }
        });
        for(int i=0;i<myOrdersModelList.size();i++){
            total = myOrdersModelList.get(i).getItemPrice() + total;
        }

        totalText.setText(total + "");
    }
    private MyOrdersModel firstItem,secondItem,thirdItem,fourthItem,fifthItem,sixthItem;

    void getReviews()   {

        myOrdersModelList = new ArrayList<>();

        firstItem = new MyOrdersModel();
        secondItem = new MyOrdersModel();
        thirdItem = new MyOrdersModel();
        fourthItem = new MyOrdersModel();
        fifthItem = new MyOrdersModel();
        sixthItem = new MyOrdersModel();
//        totalItemsCounter.setText(myOrdersModelList.size());
        firstItem.setItemName("Ron");
        secondItem.setItemName("EBA - ACT");
        thirdItem.setItemName("EBAFIX");
        fourthItem.setItemName("EBACARE");
        fifthItem.setItemName("EBACARE");
        sixthItem.setItemName("EBACARE");

        firstItem.setItemCategory("Hair Lotion");
        secondItem.setItemCategory("Hair Lotion");
        thirdItem.setItemCategory("Hair Lotion");
        fourthItem.setItemCategory("Hair Lotion");
        fifthItem.setItemCategory("Hair Lotion");
        sixthItem.setItemCategory("Hair Lotion");

        firstItem.setItemPrice(firstPrice);
        secondItem.setItemPrice(secondPrice);
        thirdItem.setItemPrice(thirdPrice);
        fourthItem.setItemPrice(fourthPrice);
        fifthItem.setItemPrice(fifthPrice);
        sixthItem.setItemPrice(sixthPrice);

        firstItem.setItemImage(R.drawable.curved_background);
        secondItem.setItemImage(R.drawable.curved_background);
        thirdItem.setItemImage(R.drawable.curved_background);
        fourthItem.setItemImage(R.drawable.curved_background);
        fifthItem.setItemImage(R.drawable.curved_background);
        sixthItem.setItemImage(R.drawable.curved_background);

 /*       firstItem.set(4.0f);
        secondItem.setReviewsRating(3.5f);
        thirdItem.setReviewsRating(2.5f);
        fourthItem.setReviewsRating(0.5f);
        fifthItem.setReviewsRating(2.5f);
        sixthItem.setReviewsRating(2.5f);*/

        myOrdersModelList.add(firstItem);
        myOrdersModelList.add(secondItem);
        myOrdersModelList.add(thirdItem);
        myOrdersModelList.add(fourthItem);
        myOrdersModelList.add(fifthItem);
        myOrdersModelList.add(sixthItem);


        setUpMyOrderList();
        myOrdersAdapter = new MyOrdersAdapter(this, myOrdersModelList,this,this::recyclerViewClickListenerDecrement);
        recyclerView.setAdapter(myOrdersAdapter);
    }

    private void setUpMyOrderList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);
    }

    int targetPos = 1;
    @Override
    public void recyclerViewClickListenerDecrement(int position,int number) {
        targetPos = position;
        total -= myOrdersModelList.get(position).getItemPrice();
        Log.d("myOrders",total+"");
        totalText.setText(total+"");
    }

    @Override
    public void recyclerViewClickListenerIncrement(int position , int number) {
        targetPos = position;
        total += myOrdersModelList.get(position).getItemPrice();
        Log.d("myOrders",total+"");
        totalText.setText(total+"");
    }
}