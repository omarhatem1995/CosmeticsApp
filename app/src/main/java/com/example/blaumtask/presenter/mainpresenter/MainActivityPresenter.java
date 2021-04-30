package com.example.blaumtask.presenter.mainpresenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blaumtask.R;
import com.example.blaumtask.adapter.ProductsAdapter;
import com.example.blaumtask.adapter.RecyclerViewClickListener;
import com.example.blaumtask.models.ProductsModel;
import com.example.blaumtask.ui.ProductsDetailsActivity;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivityPresenter implements RecyclerViewClickListener {
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private DocumentReference documentReference;
    private TextView userNameText , basketCounter;
    private MainActivityPresenterListener mainActivityPresenterListener;
    private String TAG="MainActivityPresenter";
    private Context context;
    private int targetPos = 0;
    private ProductsAdapter productsAdapter;
    private List<ProductsModel> productsModelArray;
    private RecyclerView recyclerView;
    
    public MainActivityPresenter(Context context , MainActivityPresenterListener mainActivityPresenterListener){
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        productsModelArray = new ArrayList<>();
        this.context = context;
        this.mainActivityPresenterListener = mainActivityPresenterListener;
    }

    public void updateUser(){

        mainActivityPresenterListener.showProgress();

        documentReference = firestore.collection("users").document(mAuth.getUid());
        firestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnCompleteListener(task -> {
            if(task.isSuccessful() && task.getResult() != null){
                String fullName = task.getResult().getString("FullName");
                String email = task.getResult().getString("Email");
                String phone = task.getResult().getString("userID");
                long basketNumber = task.getResult().getLong("Basket");
                Log.d(TAG,"firstName " + fullName + " " + email + " " + basketNumber  );
                userNameText = (TextView) ((Activity)context).findViewById(R.id.user_name);
                userNameText.setText(fullName);
                mainActivityPresenterListener.hideProgress();
            }else{
            }
        });
    }
    public void updateUsersBasket(){
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                basketCounter = (TextView) ((Activity)context).findViewById(R.id.basket_counter);

                basketCounter.setText(value.get("Basket").toString());
            }
        });
    }

    public void getProductsList() {
        setUpProductsList();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("cosmetic_item")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("LogTAG", document.getId() + " => " + document.getData().get("name"));
                                ProductsModel productsModel = new ProductsModel();
                                productsModel.setProductName(document.getData().get("name").toString());
                                productsModel.setProductPrice(document.getData().get("price").toString());
                                productsModel.setRating(document.getData().get("rate").toString());
                                productsModel.setImage(document.getData().get("image").toString());
                                productsModel.setCategory(document.getData().get("category").toString());
                                productsModel.setCondition(document.getData().get("condition").toString());
                                productsModel.setSerial(document.getData().get("serial").toString());

                                productsModelArray.add(productsModel);
                            }
                            setAdapter();
                        } else {
                            Log.w("LogTAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void setAdapter() {

        productsAdapter = new ProductsAdapter(context,
                productsModelArray, this);
        recyclerView.setAdapter(productsAdapter);

    }
    private void setUpProductsList() {
        recyclerView = ((Activity)context).findViewById(R.id.recyclerview_products);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void recyclerViewClickListener(int position) {
        Log.d("clickListener", "clicked");
        targetPos = position;
        Intent intent = new Intent(context, ProductsDetailsActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("id", productsModelArray.get(targetPos).getId());
        extras.putString("image", productsModelArray.get(targetPos).getImage());
        extras.putString("name", productsModelArray.get(targetPos).getProductName());
        extras.putString("price", productsModelArray.get(targetPos).getProductPrice());
        extras.putString("rating", productsModelArray.get(targetPos).getRating());
        extras.putString("category", productsModelArray.get(targetPos).getCategory());
        extras.putString("condition", productsModelArray.get(targetPos).getCondition());
        extras.putString("serial", productsModelArray.get(targetPos).getSerial());

        intent.putExtras(extras);
        context.startActivity(intent);    }
}
