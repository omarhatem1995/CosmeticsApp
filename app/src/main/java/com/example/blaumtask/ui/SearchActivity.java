package com.example.blaumtask.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.blaumtask.R;
import com.example.blaumtask.adapter.RecentAdapter;
import com.example.blaumtask.models.RecentsModel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    EditText searchText;
    RecentAdapter recentAdapter;
    RecyclerView recyclerView;
    List<RecentsModel> recentsModelList;

    private RecentsModel firstItem,secondItem,thirdItem,fourthItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchText = findViewById(R.id.search_search_edit_text);
        searchText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchText, InputMethodManager.SHOW_IMPLICIT);
        recyclerView = findViewById(R.id.recently_viewed_recyclerview);

        recentsModelList = new ArrayList<>();

        firstItem = new RecentsModel();
        secondItem = new RecentsModel();
        thirdItem = new RecentsModel();
        fourthItem = new RecentsModel();

        firstItem.setProductName("Ebafix");
        secondItem.setProductName("Ron");
        thirdItem.setProductName("Viola");
        fourthItem.setProductName("Cream");

        firstItem.setProductPrice("80");
        secondItem.setProductPrice("260");
        thirdItem.setProductPrice("378");
        fourthItem.setProductPrice("200");

        firstItem.setImage(R.drawable.ebafix);
        secondItem.setImage(R.drawable.ron);
        thirdItem.setImage(R.drawable.viola);
        fourthItem.setImage(R.drawable.cream);

        firstItem.setRating("4");
        secondItem.setRating("5");
        thirdItem.setRating("5");
        fourthItem.setRating("5");

        recentsModelList.add(firstItem);
        recentsModelList.add(secondItem);
        recentsModelList.add(thirdItem);
        recentsModelList.add(fourthItem);


        setUpRecentsList();
        recentAdapter = new RecentAdapter(this, recentsModelList);
        recyclerView.setAdapter(recentAdapter);
    }

    private void setUpRecentsList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);
    }
}