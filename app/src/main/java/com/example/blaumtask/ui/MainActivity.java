package com.example.blaumtask.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.blaumtask.presenter.mainpresenter.MainActivityPresenter;
import com.example.blaumtask.presenter.mainpresenter.MainActivityPresenterListener;
import com.example.blaumtask.utils.SpinnerDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
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

public class MainActivity extends AppCompatActivity implements MainActivityPresenterListener {

    private FirebaseAuth mAuth;

    private ImageView menuImageView, cartImage;
    private ConstraintLayout constraintLayout;

    private EditText searchEditText;

    private LinearLayout bottomSheetLayout;
    BottomSheetBehavior bottomSheetBehavior;
    String flagIntent = "1", TAG = "MainActivity";
    PopupMenu popup;
    LinearLayout background;
    private MainActivityPresenter mainActivityPresenter;

    private SpinnerDialog spinnerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

//        setUpProductsList();

//        getProductsList();


    }

    private void initViews() {
        mAuth = FirebaseAuth.getInstance();
        spinnerDialog = new SpinnerDialog(this);

        menuImageView = findViewById(R.id.menu_imageview);
        constraintLayout = findViewById(R.id.constraint_layout);

        bottomSheetLayout = findViewById(R.id.products_layout);
        searchEditText = findViewById(R.id.search_edit_text);
        background = findViewById(R.id.main_background);
        cartImage = findViewById(R.id.basket);

        background.setAlpha(0.4F);
        initPopMenu(menuImageView);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);

        mainActivityPresenter = new MainActivityPresenter(this,this);
        mainActivityPresenter.updateUser();
        mainActivityPresenter.updateUsersBasket();
        mainActivityPresenter.getProductsList();

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
                Intent intentMyOrders = new Intent(MainActivity.this, My_Orders.class);
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
                if (flagIntent.equals("1")) {
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


    @Override
    protected void onResume() {
        super.onResume();
        flagIntent = "1";
    }

    public void initPopMenu(View view) {
        popup = new PopupMenu(MainActivity.this, menuImageView);

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
                    Intent intent = new Intent(MainActivity.this, CartSettingsActivity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getTitle().equals("Logout")) {
                    mAuth.signOut();
                    Intent intentLogout = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intentLogout);
                    return true;
                } else {
                    Toast.makeText(getApplicationContext(), "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        });
    }

    public void switchToSecondFragment() {

        Intent intentSearch = new Intent(MainActivity.this, SearchActivity.class);
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



    @Override
    public void showProgress() {
        spinnerDialog.show();
    }

    @Override
    public void hideProgress() {
        spinnerDialog.dismiss();
    }
}