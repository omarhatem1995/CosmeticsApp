package com.example.blaumtask.presenter.mainpresenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blaumtask.R;
import com.example.blaumtask.adapter.ProductsAdapter;
import com.example.blaumtask.adapter.RecyclerViewClickListener;
import com.example.blaumtask.models.ProductsModel;
import com.example.blaumtask.ui.CartSettingsActivity;
import com.example.blaumtask.ui.LoginActivity;
import com.example.blaumtask.ui.MyOrdersActivity;
import com.example.blaumtask.ui.ProductsDetailsActivity;
import com.example.blaumtask.ui.SearchActivity;
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivityPresenter implements RecyclerViewClickListener {
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private DocumentReference documentReference;

    private TextView userNameText , basketCounter;

    private MainActivityPresenterListener mainActivityPresenterListener;
    private Context context;

    private String TAG="MainActivityPresenter";
    private int targetPos = 0;

    private ProductsAdapter productsAdapter;
    private List<ProductsModel> productsModelArray;
    private RecyclerView recyclerView;

    public MainActivityPresenter(Context context , MainActivityPresenterListener mainActivityPresenterListener){
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        documentReference = firestore.collection("users").document(mAuth.getUid());

        productsModelArray = new ArrayList<>();

        this.context = context;
        this.mainActivityPresenterListener = mainActivityPresenterListener;

        constraintLayout = ((Activity)context).findViewById(R.id.constraint_layout);

        menuImageView = ((Activity)context).findViewById(R.id.menu_imageview);

    }

    public void updateUser(){

        mainActivityPresenterListener.showProgress();

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
                                productsModel.setId(document.getData().get("item_id").toString());

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
    PopupMenu popup;
    ImageView menuImageView;
    private ConstraintLayout constraintLayout;

    public void initPopMenu(View view) {
        popup = new PopupMenu(context, view);

        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popup.getMenuInflater().inflate(R.menu.main, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle().equals("Settings")) {
                    Intent intent = new Intent(context, CartSettingsActivity.class);
                    context.startActivity(intent);
                    return true;
                } else if (item.getTitle().equals("Logout")) {
                    mAuth.signOut();
                    Intent intentLogout = new Intent(context, LoginActivity.class);
                    context.startActivity(intentLogout);
                    return true;
                } else {
                    Toast.makeText(context, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        });
                popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                constraintLayout.setAlpha(1);
            }
        });
    }

    public void showPopup(){
        constraintLayout.setAlpha(0.5F);
        popup.show();
    }

    public void openBasket(){
        Log.d("openBasket"," is called");
        Intent intentMyOrders = new Intent(context, MyOrdersActivity.class);
        context.startActivity(intentMyOrders);
    }

    public void switchToSecondFragment(){
        Intent intentSearch = new Intent(context, SearchActivity.class);
        context.startActivity(intentSearch);
    }

    @Override
    public void recyclerViewClickListener(int position) {
        Log.d("clickListener", "clicked");
        targetPos = position;
        Intent intent = new Intent(context, ProductsDetailsActivity.class);
        Bundle extras = new Bundle();
        extras.putString("id", productsModelArray.get(targetPos).getId());
        extras.putString("image", productsModelArray.get(targetPos).getImage());
        extras.putString("name", productsModelArray.get(targetPos).getProductName());
        extras.putString("price", productsModelArray.get(targetPos).getProductPrice());
        extras.putString("rating", productsModelArray.get(targetPos).getRating());
        extras.putString("category", productsModelArray.get(targetPos).getCategory());
        extras.putString("condition", productsModelArray.get(targetPos).getCondition());
        extras.putString("serial", productsModelArray.get(targetPos).getSerial());

        intent.putExtras(extras);
        context.startActivity(intent);
    }
}
