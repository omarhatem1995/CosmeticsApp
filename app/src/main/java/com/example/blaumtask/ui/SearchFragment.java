package com.example.blaumtask.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.blaumtask.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment {

    private String mText ;
    private static final String TEXT = "text";

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    public SearchFragment(){

    }
    public static SearchFragment newInstance(String text) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(TEXT,text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mText = getArguments().getString(TEXT);
        }
    }
    TextView detailText;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.search_fragment, container, false);
        Bundle getBundle = getActivity().getIntent().getExtras();
//        String details=getBundle.getString("DETAIL");
        detailText= (TextView) v.findViewById(R.id.fragment_add);
        detailText.setText("details");
        return v;
    }
}
