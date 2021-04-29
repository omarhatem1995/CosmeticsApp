package com.example.blaumtask.ui;

import androidx.annotation.NonNull;
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
import com.example.blaumtask.adapter.ProductsAdapter;
import com.example.blaumtask.adapter.RecyclerViewClickListener;
import com.example.blaumtask.models.ProductsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickListener
//        implements PopupMenu.OnMenuItemClickListener {
{

    private FirebaseAuth mAuth;

    ProductsAdapter productsAdapter;
    RecyclerView recyclerView;
    List<ProductsModel> productsModelArray;

    ImageView menuImageView , cartImage;
    TextView userNameText;
    ConstraintLayout constraintLayout;

    private EditText searchEditText;

    private LinearLayout bottomSheetLayout ;
    BottomSheetBehavior bottomSheetBehavior;
    String flagIntent = "1" ,TAG = "MainActivity";
    PopupMenu popup ;
    LinearLayout background;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateUser();

        initViews();

        setUpProductsList();
        getProductsList();


    }
    private void initViews(){
        productsModelArray = new ArrayList<>();

        userNameText = findViewById(R.id.user_name);
        menuImageView = findViewById(R.id.menu_imageview);
        constraintLayout = findViewById(R.id.constraint_layout);

        bottomSheetLayout = findViewById(R.id.products_layout);
        searchEditText = findViewById(R.id.search_edit_text);
        background = findViewById(R.id.main_background);
        cartImage = findViewById(R.id.basket);


        recyclerView = findViewById(R.id.recyclerview_products);

//        userNameText.setText();

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

    }

    private void updateUser(){
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        firestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnCompleteListener(task -> {
            if(task.isSuccessful() && task.getResult() != null){
                String fullName = task.getResult().getString("FullName");
                String email = task.getResult().getString("Email");
                String phone = task.getResult().getString("userID");
                Log.d(TAG,"firstName " + fullName + " " + email + " " + phone  );
                userNameText.setText(fullName);
                //other stuff
            }else{
                //deal with error
            }
        });
    }

    private void setUpProductsList() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setHasFixedSize(true);
    }
    private void getProductsList() {
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

                                productsModelArray.add(productsModel);
                            }
                            setAdapter();
                        } else {
                            Log.w("LogTAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void setAdapter(){
        productsAdapter = new ProductsAdapter(getApplicationContext(),
                productsModelArray,this);
        recyclerView.setAdapter(productsAdapter);
//        recyclerView.setVisibility(View.VISIBLE);

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
                }else if(item.getTitle().equals("Logout")) {
                    mAuth.signOut();
                    Intent intentLogout = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intentLogout);
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
//        extras.putInt("image", productsModelArray.get(targetPos).getImage());
        extras.putString("name", productsModelArray.get(targetPos).getProductName());
        extras.putString("price", productsModelArray.get(targetPos).getProductPrice());
        extras.putString("rating", productsModelArray.get(targetPos).getRating());

        intent.putExtras(extras);
        startActivity(intent);

    }
}