package com.example.blaumtask.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blaumtask.R;
import com.example.blaumtask.ui.adapter.ProductsAdapter;
import com.example.blaumtask.ui.adapter.RecyclerViewClickListener;
import com.example.blaumtask.ui.models.ProductsModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickListener
//        implements PopupMenu.OnMenuItemClickListener {
{
    ProductsAdapter productsAdapter;
    RecyclerView recyclerView;
    List<ProductsModel> productsModelArray;
    ProductsModel productsModel, firstItem, secondItem, thirdItem, fourthItem, fifthItem;

    ImageView menuImageView , cartImage;
    TextView hiSheraaText;
    ConstraintLayout constraintLayout;

    private EditText searchEditText;

    private LinearLayout bottomSheetLayout ;
    BottomSheetBehavior bottomSheetBehavior;
    String flagIntent = "1";
    PopupMenu popup ;
    LinearLayout background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuImageView = findViewById(R.id.menu_imageview);
        constraintLayout = findViewById(R.id.constraint_layout);
        bottomSheetLayout = findViewById(R.id.products_layout);
        searchEditText = findViewById(R.id.search_edit_text);
        background = findViewById(R.id.main_background);
        cartImage = findViewById(R.id.basket);

        background.setAlpha(0.4F);
        initPopMenu(menuImageView);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);

        menuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                constraintLayout.setAlpha(0.5F);
                popup.show();

            }
        });
        cartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMyOrders = new Intent(MainActivity.this,My_Orders.class);
                startActivity(intentMyOrders);
            }
        });
        searchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        searchEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(flagIntent.equals("1")) {
                    switchToSecondFragment();
                    flagIntent = "0";
                }
                return false;
            }
        });
        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                constraintLayout.setAlpha(1);
            }
        });

        productsModelArray = new ArrayList<>();

        firstItem = new ProductsModel();
        secondItem = new ProductsModel();
        thirdItem = new ProductsModel();
        fourthItem = new ProductsModel();
        fifthItem = new ProductsModel();

        firstItem.setId(0);
        secondItem.setId(1);
        thirdItem.setId(2);
        fourthItem.setId(3);
        fifthItem.setId(4);

        firstItem.setProductName("Ebafix");
        secondItem.setProductName("Ron");
        thirdItem.setProductName("Viola");
        fourthItem.setProductName("Cream");
        fifthItem.setProductName("Salikata");

        firstItem.setProductPrice("80");
        secondItem.setProductPrice("260");
        thirdItem.setProductPrice("378");
        fourthItem.setProductPrice("200");
        fifthItem.setProductPrice("45");

        firstItem.setImage(R.drawable.ebafix);
        secondItem.setImage(R.drawable.ron);
        thirdItem.setImage(R.drawable.viola);
        fourthItem.setImage(R.drawable.cream);
        fifthItem.setImage(R.drawable.salikata);

        firstItem.setRating("4");
        secondItem.setRating("5");
        thirdItem.setRating("5");
        fourthItem.setRating("5");
        fifthItem.setRating("4.5");

        productsModelArray.add(firstItem);
        productsModelArray.add(secondItem);
        productsModelArray.add(thirdItem);
        productsModelArray.add(fourthItem);
        productsModelArray.add(fifthItem);

        recyclerView = findViewById(R.id.recyclerview_products);
        setUpProductsList();
        productsAdapter = new ProductsAdapter(this, productsModelArray,this::recyclerViewClickListener);
        recyclerView.setAdapter(productsAdapter);
    }

    private void setUpProductsList() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setHasFixedSize(true);
    }

  /*  public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        popup.setOnMenuItemClickListener(this);
        inflater.inflate(R.menu.main, popup.getMenu());
        popup.show();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        flagIntent = "1";
    }

    public void initPopMenu(View view){
        popup =  new PopupMenu(MainActivity.this, menuImageView);

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
                if(item.getTitle().equals("Settings")){
                    Intent intent = new Intent(MainActivity.this, CartSettingsActivity.class);
                    startActivity(intent);
                    return true;
                }else {
                    Toast.makeText(getApplicationContext(), "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        });
    }

    public void switchToSecondFragment(){

        Intent intentSearch = new Intent(MainActivity.this,SearchActivity.class);
        startActivity(intentSearch);
        /*PagerAdapter adapter = new PagerAdapter(this);
        TabLayout pagerTL = findViewById(R.id.pagerTL);
        mViewPager = findViewById(R.id.pager);

        adapter.addFragment("adapter", new SearchFragment(this));
        mViewPager.setUserInputEnabled(true);
        mViewPager.setAdapter(adapter);
        new TabLayoutMediator(pagerTL, mViewPager,
                (tab, position) -> tab.setText(adapter.getFragmentTitle(position)))
                .attach();
        pagerTL.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/
        /*Fragment fragment = new SearchFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.search_layout, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
        Log.d("switch","is called");*/
    }
    private int targetPos = 0;

    @Override
    public void recyclerViewClickListener(int position) {
        Log.d("clickListener","clicked");
        targetPos = position;
        Intent intent = new Intent(getApplicationContext(), ProductsDetailsActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("id", productsModelArray.get(targetPos).getId());
        extras.putInt("image", productsModelArray.get(targetPos).getImage());
        extras.putString("name", productsModelArray.get(targetPos).getProductName());
        extras.putString("price", productsModelArray.get(targetPos).getProductPrice());
        extras.putString("rating", productsModelArray.get(targetPos).getRating());

        intent.putExtras(extras);
        startActivity(intent);

    }
}