package com.example.blaumtask.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.blaumtask.R;
import com.example.blaumtask.ui.models.RecentsModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.RecentViewHolder >{


    private final Context mContext;
    private String TAG = "RecyclerViewAdapterTAG";
    private List<RecentsModel> recentModelArray;
    public RecentAdapter(Context mContext,List<RecentsModel> recentModelArray) {
        this.mContext = mContext;
        this.recentModelArray = recentModelArray;
    }

    @NonNull
    @Override
    public RecentAdapter.RecentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.recently_viewed_item,parent,false);
        RecentAdapter.RecentViewHolder viewHolder = new RecentAdapter.RecentViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecentAdapter.RecentViewHolder holder, int position) {
        String recentsName = recentModelArray.get(position).getProductName();
        String recentsPrice = recentModelArray.get(position).getProductPrice();
        String recentsRate = recentModelArray.get(position).getRating();

        holder.recentsName.setText(recentsName);
        holder.recentsPrice.setText(recentsPrice);

        Glide.with(mContext)
                .load(recentModelArray.get(position).getImage())
                .into(holder.recentsImage);
    }

    @Override
    public int getItemCount() {
        return recentModelArray.size();
    }

    public static class RecentViewHolder extends RecyclerView.ViewHolder{

        private TextView recentsName ;
        private TextView recentsPrice;
        private ImageView recentsImage;

        public RecentViewHolder(@NonNull View itemView) {
            super(itemView);

            recentsName = (TextView) itemView.findViewById(R.id.text_item);
            recentsPrice = (TextView) itemView.findViewById(R.id.item_price);
            recentsImage = (ImageView) itemView.findViewById(R.id.image_item);
        }
    }

}
