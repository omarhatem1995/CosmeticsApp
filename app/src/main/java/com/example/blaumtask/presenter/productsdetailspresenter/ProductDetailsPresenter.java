package com.example.blaumtask.presenter.productsdetailspresenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blaumtask.R;
import com.example.blaumtask.adapter.ReviewsAdapter;
import com.example.blaumtask.models.ProductsModel;
import com.example.blaumtask.models.ReviewsModel;
import com.example.blaumtask.ui.ProductsDetailsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProductDetailsPresenter {
    private Context context;
    private Activity activity;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private DocumentReference documentReference , addToCartReference;

    private ReviewsAdapter reviewsAdapter;
    private List<ReviewsModel> reviewsModelList;

    private TextView basketCounter, details, reviews, productPriceDetails;
    private LinearLayout linearDetails, linearButtons;
    private RecyclerView recyclerView;
    String itemID , image , name , rating , categoryString , serialString , conditionString , price;
    ProductsModel productsModel;
    private long basketNumber , totalNumber;

    public ProductDetailsPresenter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        Intent intent = activity.getIntent();
        Bundle extras = intent.getExtras();
        itemID = extras.getString("id");
        image = extras.getString("image");
        name = extras.getString("name");
        price = extras.getString("price");
        rating = extras.getString("rating");
        conditionString = extras.getString("condition");
        serialString = extras.getString("serial");
        categoryString = extras.getString("category");
        productsModel = new ProductsModel();
        productsModel.setSerial(serialString);
        productsModel.setCondition(conditionString);
        productsModel.setCategory(categoryString);
        productsModel.setRating(rating);
        productsModel.setImage(image);
        productsModel.setId(itemID);
        productsModel.setProductName(name);
        productsModel.setProductPrice(price);
        productsModel.setProductCount(1);

        documentReference = firestore.collection("users").document(mAuth.getUid());
        addToCartReference = firestore.collection("users_cart").document(mAuth.getUid());

        linearDetails = ((Activity) context).findViewById(R.id.linear2);
        linearButtons = ((Activity) context).findViewById(R.id.linearbuttons);
        recyclerView = ((Activity) context).findViewById(R.id.reviews_list);
        details = ((Activity) context).findViewById(R.id.details_details);
        reviews = ((Activity) context).findViewById(R.id.reviews_details);
        basketCounter = ((Activity) context).findViewById(R.id.basket_counter);
        productPriceDetails = ((Activity) context).findViewById(R.id.product_price_details);
    }

    public void getUserData() {
        firestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {

                basketNumber = task.getResult().getLong("Basket");
                totalNumber = task.getResult().getLong("Total");
                basketCounter.setText(String.valueOf(basketNumber));
                //other stuff
            } else {
                //deal with error
            }
        });
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Log.d("valueEventListener", value.get("Basket").toString());
                basketCounter.setText(value.get("Basket").toString());

            }
        });
    }

    public void addToCart() {

        basketNumber = basketNumber + 1;
        documentReference.update("Total", totalNumber + Double.valueOf(productPriceDetails.getText().toString()));
        documentReference.update("Basket", basketNumber);
        addToCartReference.collection("item").document(itemID).set(productsModel);
//        addToCartReference.set(productsModel);
        Toast.makeText(context, R.string.item_is_added_successfully, Toast.LENGTH_SHORT).show();
        activity.finish();
    }

    public void reviewDetailsClick() {
        details.setElevation(0.0F);
        details.setTypeface(null, Typeface.NORMAL);
        reviews.setElevation(20.0F);
        reviews.setTypeface(null, Typeface.BOLD);
        linearDetails.setVisibility(View.GONE);
        linearButtons.setVisibility(View.GONE);
        getReviews();
        recyclerView.setVisibility(View.VISIBLE);
    }

    public void detailsDetailsClick() {
        reviews.setElevation(0.0F);
        reviews.setTypeface(null, Typeface.NORMAL);
        details.setElevation(20.0F);
        details.setTypeface(null, Typeface.BOLD);
        linearDetails.setVisibility(View.VISIBLE);
        linearButtons.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private ReviewsModel firstItem, secondItem, thirdItem, fourthItem, fifthItem, sixthItem;

    void getReviews() {

        reviewsModelList = new ArrayList<>();

        firstItem = new ReviewsModel();
        secondItem = new ReviewsModel();
        thirdItem = new ReviewsModel();
        fourthItem = new ReviewsModel();
        fifthItem = new ReviewsModel();
        sixthItem = new ReviewsModel();

        firstItem.setReviewsName("Mohamed");
        secondItem.setReviewsName("Ahmed Omar");
        thirdItem.setReviewsName("Mohanad Ramadan");
        fourthItem.setReviewsName("Sayed Mohamed");
        fifthItem.setReviewsName("Mohanad Mohamed");
        sixthItem.setReviewsName("Mohanad Mohamed");

        firstItem.setReviewsDate("20-12-2021");
        secondItem.setReviewsDate("20-12-2021");
        thirdItem.setReviewsDate("20-12-2021");
        fourthItem.setReviewsDate("20-12-2021");
        fifthItem.setReviewsDate("30-12-2021");
        sixthItem.setReviewsDate("30-12-2021");

        firstItem.setReviewsImage(R.drawable.curved_background);
        secondItem.setReviewsImage(R.drawable.curved_background);
        thirdItem.setReviewsImage(R.drawable.curved_background);
        fourthItem.setReviewsImage(R.drawable.curved_background);
        fifthItem.setReviewsImage(R.drawable.curved_background);
        sixthItem.setReviewsImage(R.drawable.curved_background);

        firstItem.setReviewsRating(4.0f);
        secondItem.setReviewsRating(3.5f);
        thirdItem.setReviewsRating(2.5f);
        fourthItem.setReviewsRating(0.5f);
        fifthItem.setReviewsRating(2.5f);
        sixthItem.setReviewsRating(2.5f);

        reviewsModelList.add(firstItem);
        reviewsModelList.add(secondItem);
        reviewsModelList.add(thirdItem);
        reviewsModelList.add(fourthItem);
        reviewsModelList.add(fifthItem);
        reviewsModelList.add(sixthItem);


        setUpReviewsList();
        reviewsAdapter = new ReviewsAdapter(context, reviewsModelList);
        recyclerView.setAdapter(reviewsAdapter);
    }

    private void setUpReviewsList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);
    }
}
