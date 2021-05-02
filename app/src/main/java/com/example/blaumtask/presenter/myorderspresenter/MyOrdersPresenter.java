package com.example.blaumtask.presenter.myorderspresenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import com.example.blaumtask.R;
import com.example.blaumtask.adapter.MyOrdersAdapter;
import com.example.blaumtask.adapter.RecyclerViewClickListenerDecrement;
import com.example.blaumtask.adapter.RecyclerViewClickListenerIncrement;
import com.example.blaumtask.models.MyOrdersModel;
import com.example.blaumtask.ui.CheckoutActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyOrdersPresenter implements RecyclerViewClickListenerIncrement,
        RecyclerViewClickListenerDecrement {
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    private DocumentReference documentReference, addItemCountertReference;

    private MyOrdersPresenterListener myOrdersPresenterListener;
    private MyOrdersModel myOrdersModel;
    private List<MyOrdersModel> myOrdersModelList;
    private MyOrdersAdapter myOrdersAdapter;
    private RecyclerView recyclerView;

    private TextView totalText , basketCounter;

    private Context context;
    private Activity activity;

    private String TAG = "MyOrdersPresenter";
    int targetPos , basket;
    double total;

    public MyOrdersPresenter(Context context, Activity activity, MyOrdersPresenterListener myOrdersPresenterListener) {
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        documentReference = firestore.collection("users")
                .document(mAuth.getUid());
        addItemCountertReference = firestore.collection("users_cart")
                .document(mAuth.getUid());

        recyclerView = ((Activity) context).findViewById(R.id.recyclerview_my_orders);
        totalText = ((Activity) context).findViewById(R.id.total_text);
        basketCounter = ((Activity)context).findViewById(R.id.basket_counter);

        this.context = context;
        this.activity = activity;
        this.myOrdersPresenterListener = myOrdersPresenterListener;

    }

    int itemCount;

    public void getBasketList() {
        myOrdersPresenterListener.showProgress();
        myOrdersModelList = new ArrayList<>();

        firestore.collection("users_cart").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("item").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, "item Name : " + document.getData().get("item_name"));

                        String itemID = document.getData().get("id").toString();
                        double itemPrice = Double.parseDouble(document.getData().get("productPrice").toString());
                        String itemName = document.getData().get("productName").toString();
                        itemCount = Integer.parseInt(document.getData().get("productCount").toString());
                        String image = document.getData().get("image").toString();
                        Log.d(TAG, "firstName " + itemPrice + " " + itemName + " " + itemCount);
                        myOrdersModel = new MyOrdersModel(itemName, itemPrice, itemID, itemCount,image);
                        myOrdersModelList.add(myOrdersModel);

                        myOrdersPresenterListener.hideProgress();
                    }
                    setUpMyOrderList();
                    myOrdersAdapter = new MyOrdersAdapter(context, myOrdersModelList, MyOrdersPresenter.this::recyclerViewClickListenerIncrement, MyOrdersPresenter.this::recyclerViewClickListenerDecrement);
                    recyclerView.setAdapter(myOrdersAdapter);
                    totalTextUpdate();
                    Log.d("myItemList", " " + myOrdersModelList.size());
                }
            }
        });
    }

    public void totalTextUpdate() {
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                totalText.setText(value.get("Total").toString());
                total = Double.valueOf(value.get("Total").toString());
                Log.d("basketNumber", basket+ " ");
            }
        });
    }

    private void setUpMyOrderList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);
    }

    public void checkOut() {
        Intent intentCheckout = new Intent(context, CheckoutActivity.class);
        context.startActivity(intentCheckout);
    }

    public void finishActivity() {
        activity.finish();
    }

    public void updateUsersBasket(){
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                basketCounter.setText(value.get("Basket").toString());
                basket = Integer.valueOf(value.get("Basket").toString());
                Log.d("lsdlsldlsd",value.get("Basket").toString());
            }
        });
    }

    @Override
    public void recyclerViewClickListenerDecrement(int position, int number) {
        targetPos = position;
        itemCount = myOrdersModelList.get(position).getItemCount();
        String itemID = myOrdersModelList.get(position).getItemID();
        addItemCountertReference.collection("item").document(itemID)
                .update("productCount", itemCount - 1);
        documentReference.update("Total", total - myOrdersModelList.get(position).getItemPrice());
        documentReference.update("Basket", basket-1);

        getBasketList();
    }

    @Override
    public void recyclerViewClickListenerIncrement(int position, int number) {
        targetPos = position;
        itemCount = myOrdersModelList.get(position).getItemCount();
        String itemID = myOrdersModelList.get(position).getItemID();
        addItemCountertReference.collection("item").document(itemID)
                .update("productCount", itemCount + 1);
        Log.d("targetPosition", targetPos + " , number " + itemCount + " , " +
                itemID + " , " + (itemCount + 1));

        documentReference.update("Total", total + myOrdersModelList.get(position).getItemPrice());
        documentReference.update("Basket", basket+1);
        getBasketList();
    }
}
