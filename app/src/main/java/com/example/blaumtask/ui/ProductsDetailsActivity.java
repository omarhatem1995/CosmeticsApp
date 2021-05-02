package com.example.blaumtask.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.blaumtask.R;
import com.example.blaumtask.adapter.SliderAdapterFailure;
import com.example.blaumtask.presenter.productsdetailspresenter.ProductDetailsPresenter;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class ProductsDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private String name, price, rating, image, categoryString, serialString, conditionString , id;
    private ImageView backButton;
    private LinearLayout linearBackground;
    private TextView details, reviews, nameProduct, productPriceDetails, ratingDetails, serial, condition, category;
    private Button addToCart , cancelButton;

    private ProductDetailsPresenter productDetailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_details);

        initViews();

    }

    private void initViews() {
        productDetailsPresenter = new ProductDetailsPresenter(this, this);

        productDetailsPresenter.getCurrentBasket();
        backButton = findViewById(R.id.back_arrow);
        linearBackground = findViewById(R.id.background_details);
        details = findViewById(R.id.details_details);
        reviews = findViewById(R.id.reviews_details);
        nameProduct = findViewById(R.id.name_product);
        productPriceDetails = findViewById(R.id.product_price_details);
        ratingDetails = findViewById(R.id.product_rating_details);
        category = findViewById(R.id.category_product);
        serial = findViewById(R.id.serial_product);
        condition = findViewById(R.id.condition_product);
        addToCart = findViewById(R.id.addtocart);
        cancelButton = findViewById(R.id.cancel);

        getExtras();

        reviews.setElevation(0.0F);
        reviews.setTypeface(null, Typeface.NORMAL);
        details.setElevation(20.0F);
        details.setTypeface(null, Typeface.BOLD);

        reviews.setOnClickListener(this);
        details.setOnClickListener(this);
        addToCart.setOnClickListener(this);
        backButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        getDefaultSlider();

        backButton.setBackgroundResource(R.drawable.ic_backarrow);
        backButton.setRotation(180);

        linearBackground.setAlpha(0.1F);
    }

    private void getExtras() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        id = extras.getString("id");
        image = extras.getString("image");
        name = extras.getString("name");
        price = extras.getString("price");
        rating = extras.getString("rating");
        conditionString = extras.getString("condition");
        serialString = extras.getString("serial");
        categoryString = extras.getString("category");
        nameProduct.setText(name);
        productPriceDetails.setText(price);
        ratingDetails.setText(rating);
        category.setText(categoryString);
        serial.setText(serialString);
        condition.setText(conditionString);
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

        if (name.equals("Ebafix")) {
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
        } else if (name.equals("Ron")) {
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
        }
        if (name.equals("Viola")) {
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
        }
        if (name.equals("Cream")) {
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
        }
        if (name.equals("Salikata")) {
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reviews_details:
                productDetailsPresenter.reviewDetailsClick();
                break;
            case R.id.details_details:
                productDetailsPresenter.detailsDetailsClick();
                break;
            case R.id.addtocart:
                productDetailsPresenter.addToCart();
                break;
            case R.id.back_arrow:
            case R.id.cancel:
                finish();
                break;
        }
    }

}