package com.example.blaumtask.presenter.checkoutpresenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blaumtask.R;
import com.example.blaumtask.adapter.MyOrdersAdapter;
import com.example.blaumtask.adapter.RecyclerViewClickListenerDecrement;
import com.example.blaumtask.adapter.RecyclerViewClickListenerIncrement;
import com.example.blaumtask.models.MyOrdersModel;
import com.example.blaumtask.presenter.myorderspresenter.MyOrdersPresenter;
import com.example.blaumtask.ui.CheckoutActivity;
import com.example.blaumtask.ui.FinishOrderActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
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

public class CheckoutPresenter implements RecyclerViewClickListenerIncrement , RecyclerViewClickListenerDecrement {
    private Activity activity;
    private Context context;

    private RecyclerView recyclerView;
    private TextView totalText , basketCounter;
    private List<MyOrdersModel> list;
    private MyOrdersAdapter myOrdersAdapter;

    private String TAG = "CheckOutPresenter" , totalNumber ;
    private int targetPos , basket , itemCount;
    private double total;

    private FirebaseAuth mAuth;
    private DocumentReference documentReference, addItemCountertReference , deleteItemReference;
    private CheckoutPresenterListener checkoutPresenterListener;
    private FirebaseFirestore firestore;

    private MyOrdersModel myOrdersModel;

    public CheckoutPresenter(Activity activity, Context context, CheckoutPresenterListener checkoutPresenterListener) {
        this.activity = activity;
        this.context = context;
        this.checkoutPresenterListener = checkoutPresenterListener;

        recyclerView = ((Activity) context).findViewById(R.id.recyclerview_my_orders_checkout);
        totalText = ((Activity) context).findViewById(R.id.price);
        basketCounter = ((Activity)context).findViewById(R.id.cart_my_orders_counter);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        documentReference = firestore.collection("users")
                .document(mAuth.getUid());
        addItemCountertReference = firestore.collection("users_cart")
                .document(mAuth.getUid());
        deleteItemReference = firestore.collection("users_cart")
                .document(mAuth.getUid());

        getIntent();

    }

    public void getIntent(){
        Intent i = activity.getIntent();
        list = (List<MyOrdersModel>) i .getSerializableExtra("List");
        totalNumber = i.getStringExtra("totalNumber");
        total = Double.parseDouble(totalNumber);
        totalText.setText(totalNumber);
        Log.d(TAG, " get List " + list.size());
        putItemsInRecyclerView();
    }

    public void putItemsInRecyclerView(){
        setUpMyOrderList();
        Log.d(TAG, " get List " + list.size());
        myOrdersAdapter = new MyOrdersAdapter(context, list, CheckoutPresenter.this::recyclerViewClickListenerIncrement, CheckoutPresenter.this::recyclerViewClickListenerDecrement);
        recyclerView.setAdapter(myOrdersAdapter);
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

    private void setUpMyOrderList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);
    }
    List<MyOrdersModel> myOrdersModelList;
    public void openFinishActivity(){
        Intent intentFinishOrder = new Intent(context, FinishOrderActivity.class);
        documentReference.update("Total", 0);
        documentReference.update("Basket", 0);
        myOrdersModelList = new ArrayList<>();

        addItemCountertReference.collection("item")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {


                        String itemID = document.getData().get("id").toString();
                        myOrdersModel = new MyOrdersModel(null, 0, itemID,0,null);
                        myOrdersModelList.add(myOrdersModel);
                        addItemCountertReference.collection("item").document(itemID)
                                .update("id", FieldValue.delete());
                        addItemCountertReference.collection("item").document(itemID)
                                .update("category", FieldValue.delete());
                        addItemCountertReference.collection("item").document(itemID)
                                .update("condition", FieldValue.delete());
                        addItemCountertReference.collection("item").document(itemID)
                                .update("image", FieldValue.delete());
                        addItemCountertReference.collection("item").document(itemID)
                                .update("productCount", FieldValue.delete());
                        addItemCountertReference.collection("item").document(itemID)
                                .update("productName", FieldValue.delete());
                        addItemCountertReference.collection("item").document(itemID)
                                .update("productPrice", FieldValue.delete());
                        addItemCountertReference.collection("item").document(itemID)
                                .update("rating", FieldValue.delete());
                        addItemCountertReference.collection("item").document(itemID)
                                .update("serial", FieldValue.delete());

                    }
                    for (int i = 0 ; i < myOrdersModelList.size(); i++){

                        Log.d("getModel", myOrdersModelList.get(i).getItemID());
                        addItemCountertReference.collection("item").document( myOrdersModelList.get(i).getItemID()).delete();
                    }
                }
                context.startActivity(intentFinishOrder);

            }
        });
                /*.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(activity, "Deleted Successfully", Toast.LENGTH_SHORT).show();

            }
        });
*/
    }

    public void finishActivity(){
        activity.finish();
    }

    public void getBasketList() {
        checkoutPresenterListener.showProgress();
        list = new ArrayList<>();

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
                        list.add(myOrdersModel);

                        checkoutPresenterListener.hideProgress();
                    }
                    setUpMyOrderList();
                    myOrdersAdapter = new MyOrdersAdapter(context, list, CheckoutPresenter.this::recyclerViewClickListenerIncrement, CheckoutPresenter.this::recyclerViewClickListenerDecrement);
                    recyclerView.setAdapter(myOrdersAdapter);
                    totalTextUpdate();
                    Log.d("myItemList", " " + list.size());
                }
            }
        });
    }

    private void totalTextUpdate(){
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                totalText.setText(value.get("Total").toString());
                total = Double.valueOf(value.get("Total").toString());
                Log.d("basketNumber", basket+ " ");
            }
        });
    }


    @Override
    public void recyclerViewClickListenerDecrement(int position, int number) {
        targetPos = position;
        itemCount = list.get(position).getItemCount();
        String itemID = list.get(position).getItemID();
        addItemCountertReference.collection("item").document(itemID)
                .update("productCount", itemCount - 1);
        documentReference.update("Total", total - list.get(position).getItemPrice());
        documentReference.update("Basket", basket-1);

        getBasketList();
    }

    @Override
    public void recyclerViewClickListenerIncrement(int position, int number) {
        targetPos = position;
        itemCount = list.get(position).getItemCount();
        String itemID = list.get(position).getItemID();
        addItemCountertReference.collection("item").document(itemID)
                .update("productCount", itemCount + 1);
        Log.d("targetPosition", targetPos + " , number " + itemCount + " , " +
                itemID + " , " + (itemCount + 1));

        documentReference.update("Total", total + list.get(position).getItemPrice());
        documentReference.update("Basket", basket+1);
        getBasketList();
    }
}
