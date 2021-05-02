package com.example.blaumtask.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.blaumtask.R;
import com.example.blaumtask.models.MyOrdersModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyOrdersViewHolder> {


    private final Context mContext;
    private String TAG = "RecyclerViewAdapterTAG";
    private List<MyOrdersModel> myOrdersModels;
    private int number = 1;
    private RecyclerViewClickListenerIncrement recyclerViewClickListenerIncrement;
    private RecyclerViewClickListenerDecrement recyclerViewClickListenerDecrement;

    public MyOrdersAdapter(Context mContext, List<MyOrdersModel> myOrdersModels
            , RecyclerViewClickListenerIncrement recyclerViewClickListenerIncrement,
                           RecyclerViewClickListenerDecrement recyclerViewClickListenerDecrement) {
        this.mContext = mContext;
        this.myOrdersModels = myOrdersModels;
        this.recyclerViewClickListenerIncrement = recyclerViewClickListenerIncrement;
        this.recyclerViewClickListenerDecrement = recyclerViewClickListenerDecrement;
    }

    @NonNull
    @Override
    public MyOrdersAdapter.MyOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.my_orders_item, parent, false);
        MyOrdersAdapter.MyOrdersViewHolder viewHolder = new MyOrdersAdapter.MyOrdersViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersAdapter.MyOrdersViewHolder holder, int position) {
        String itemName = myOrdersModels.get(position).getItemName();
        double itemPrice = myOrdersModels.get(position).getItemPrice();
        String itemCategory = myOrdersModels.get(position).getItemCategory();
        int itemCounter = myOrdersModels.get(position).getItemCount();

        holder.itemName.setText(itemName);
        holder.itemCategory.setText(itemCategory);
        holder.itemPrice.setText(itemPrice + "");
        holder.numberCounter.setText(itemCounter + "");
        holder.minusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(holder.numberCounter.getText().toString()) != 0) {
                    recyclerViewClickListenerDecrement.recyclerViewClickListenerDecrement(position, 0);
                } else {
                    Toast.makeText(mContext, "Can't decrease", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.plusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewClickListenerIncrement.recyclerViewClickListenerIncrement(position, 0);
            }
        });
        Log.d("Msosdlsdksd", " " + myOrdersModels.get(position).getItemImage());
        Glide.with(mContext)
                .load(myOrdersModels.get(position).getItemImage())
                .into(holder.itemOrders);
    }

    @Override
    public int getItemCount() {
        return myOrdersModels.size();
    }

    public static class MyOrdersViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName;
        private TextView itemCategory;
        private TextView itemPrice;
        private TextView numberCounter;
        private ImageView itemOrders, plusImage, minusImage;

        public MyOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.name_my_orders);
            itemCategory = (TextView) itemView.findViewById(R.id.category_my_orders);
            itemPrice = (TextView) itemView.findViewById(R.id.price_my_orders);
            numberCounter = (TextView) itemView.findViewById(R.id.item_counter);
            itemOrders = (ImageView) itemView.findViewById(R.id.image_my_orders);
            plusImage = (ImageView) itemView.findViewById(R.id.plus_image);
            minusImage = (ImageView) itemView.findViewById(R.id.minus_image);
        }
    }
}