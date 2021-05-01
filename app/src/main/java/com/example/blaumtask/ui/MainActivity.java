package com.example.blaumtask.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
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

public class MainActivity extends AppCompatActivity implements MainActivityPresenterListener, View.OnClickListener,
        View.OnTouchListener {

    private ImageView menuImageView, cartImage;

    private EditText searchEditText;

    private LinearLayout bottomSheetLayout;
    private BottomSheetBehavior bottomSheetBehavior;
    private String flagIntent = "1", TAG = "MainActivity";
    private LinearLayout background;
    private FrameLayout fragmentLayout;

    private MainActivityPresenter mainActivityPresenter;

    private SpinnerDialog spinnerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

    }

    private void initViews() {
        spinnerDialog = new SpinnerDialog(this);

        menuImageView = findViewById(R.id.menu_imageview);

        fragmentLayout = findViewById(R.id.fragment_layout);

        bottomSheetLayout = findViewById(R.id.products_layout);
        searchEditText = findViewById(R.id.search_edit_text);
        background = findViewById(R.id.main_background);
        cartImage = findViewById(R.id.basket);

        background.setAlpha(0.4F);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);

        mainActivityPresenter = new MainActivityPresenter(this,this);
        mainActivityPresenter.updateUser();
        mainActivityPresenter.updateUsersBasket();
        mainActivityPresenter.getProductsList();
        mainActivityPresenter.initPopMenu(menuImageView);

        menuImageView.setOnClickListener(this);
        cartImage.setOnClickListener(this);

        searchEditText.setOnTouchListener(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        flagIntent = "1";
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.menu_imageview:
                mainActivityPresenter.showPopup();
                break;
            case R.id.basket:
                mainActivityPresenter.openBasket();
                break;
        }
    }

    private void openFragment(String text){
        SearchFragment fragment = SearchFragment.newInstance(text);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.exit_to_right, R.anim.exit_to_right,
                R.anim.exit_to_right,R.anim.exit_to_right);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.fragmentsearch_layout,fragment,"SEARCH_FRAGMENT").commit();

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
            mainActivityPresenter.switchToSecondFragment();
//            openFragment("TEXT");
            return true;
        }
        return false;
    }
}