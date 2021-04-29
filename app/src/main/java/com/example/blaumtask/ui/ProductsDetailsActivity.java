package com.example.blaumtask.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.blaumtask.R;
import com.example.blaumtask.adapter.ReviewsAdapter;
import com.example.blaumtask.adapter.SliderAdapterFailure;
import com.example.blaumtask.models.ReviewsModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class ProductsDetailsActivity extends AppCompatActivity {

    private String name,price,rating;
    private int image,id;
    ImageView backButton;
    private SliderView sliderView;
    private LinearLayout linearBackground , linearDetails, linearButtons;
    private TextView details,reviews , nameProduct ,productPriceDetails , ratingDetails;
    ReviewsAdapter reviewsAdapter;
    RecyclerView recyclerView;
    List<ReviewsModel> reviewsModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_details);

        backButton = findViewById(R.id.back_arrow);
        sliderView = findViewById(R.id.imageSlider);
        linearBackground = findViewById(R.id.background_details);
        linearDetails = findViewById(R.id.linear2);
        linearButtons = findViewById(R.id.linearbuttons);
        details = findViewById(R.id.details_details);
        reviews = findViewById(R.id.reviews_details);
        recyclerView = findViewById(R.id.reviews_list);
        nameProduct = findViewById(R.id.name_product);
        productPriceDetails = findViewById(R.id.product_price_details);
        ratingDetails = findViewById(R.id.product_rating_details);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        id = extras.getInt("id");
//        image = extras.getInt("image");
        name = extras.getString("name");
        price = extras.getString("price");
        rating = extras.getString("rating");
        nameProduct.setText(name);
        productPriceDetails.setText(price);
        ratingDetails.setText(rating);

        reviews.setElevation(0.0F);
        reviews.setTypeface(null, Typeface.NORMAL);
        details.setElevation(20.0F);
        details.setTypeface(null, Typeface.BOLD);

        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                details.setElevation(0.0F);
                details.setTypeface(null, Typeface.NORMAL);
                reviews.setElevation(20.0F);
                reviews.setTypeface(null, Typeface.BOLD);
                linearDetails.setVisibility(View.GONE);
                linearButtons.setVisibility(View.GONE);
                getReviews();
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviews.setElevation(0.0F);
                reviews.setTypeface(null, Typeface.NORMAL);
                details.setElevation(20.0F);
                details.setTypeface(null, Typeface.BOLD);
                linearDetails.setVisibility(View.VISIBLE);
                linearButtons.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);

            }
        });

        getDefaultSlider();
        backButton.setBackgroundResource(R.drawable.ic_backarrow);
        backButton.setRotation(180);

        linearBackground.setAlpha(0.1F);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }

        public void getDefaultSlider() {
            SliderView sliderView = findViewById(R.id.imageSlider);
            SliderAdapterFailure sliderAdapterFailure = new SliderAdapterFailure(this);

            sliderView.setSliderAdapter(sliderAdapterFailure);
            sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
            sliderView.setScrollTimeInSec(3); //set scroll delay in seconds :
            sliderView.startAutoCycle();
            ArrayList<SliderItem> list = new ArrayList<>();

            if(name.equals("Ebafix")) {
                list.add(new SliderItem(
                        R.drawable.ebafix,
                        name,
                        price));
                list.add(new SliderItem(
                        R.drawable.ebafix,
                        name,
                        price));
                list.add(new SliderItem(
                        R.drawable.ebafix,
                        name,
                        price));
            }else if(name.equals("Ron")) {
                list.add(new SliderItem(
                        R.drawable.ron,
                        name,
                        price));
                list.add(new SliderItem(
                        R.drawable.ron,
                        name,
                        price));
                list.add(new SliderItem(
                        R.drawable.ron,
                        name,
                        price));
            }if(name.equals("Viola")) {
                list.add(new SliderItem(
                        R.drawable.viola,
                        name,
                        price));
                list.add(new SliderItem(
                        R.drawable.viola,
                        name,
                        price));
                list.add(new SliderItem(
                        R.drawable.viola,
                        name,
                        price));
            }if(name.equals("Cream")) {
                list.add(new SliderItem(
                        R.drawable.cream,
                        name,
                        price));
                list.add(new SliderItem(
                        R.drawable.cream,
                        name,
                        price));
                list.add(new SliderItem(
                        R.drawable.cream,
                        name,
                        price));
            }if(name.equals("Salikata")) {
                list.add(new SliderItem(
                        R.drawable.salikata,
                        name,
                        price));
                list.add(new SliderItem(
                        R.drawable.salikata,
                        name,
                        price));
                list.add(new SliderItem(
                        R.drawable.salikata,
                        name,
                        price));
            }

            sliderAdapterFailure.renewItems(list);
    }
    private ReviewsModel firstItem,secondItem,thirdItem,fourthItem,fifthItem,sixthItem;

    void getReviews()   {

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
        reviewsAdapter = new ReviewsAdapter(this, reviewsModelList);
        recyclerView.setAdapter(reviewsAdapter);
    }

    private void setUpReviewsList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);
    }
}