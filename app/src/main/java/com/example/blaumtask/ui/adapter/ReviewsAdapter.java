package com.example.blaumtask.ui.adapter;

import android.content.Context;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.blaumtask.R;
import com.example.blaumtask.ui.models.ReviewsModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder > {


    private final Context mContext;
    private String TAG = "RecyclerViewAdapterTAG";
    private List<ReviewsModel> reviewsModelList;

    public ReviewsAdapter(Context mContext, List<ReviewsModel> reviewsModelList) {
        this.mContext = mContext;
        this.reviewsModelList = reviewsModelList;
    }

    @NonNull
    @Override
    public ReviewsAdapter.ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.reviews_item, parent, false);
        ReviewsAdapter.ReviewsViewHolder viewHolder = new ReviewsAdapter.ReviewsViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ReviewsViewHolder holder, int position) {
        String recentsName = reviewsModelList.get(position).getReviewsName();
        String recentsPrice = reviewsModelList.get(position).getReviewsMessage();
        float reviewsRating = reviewsModelList.get(position).getReviewsRating();
        String reviewsDate = reviewsModelList.get(position).getReviewsDate();

        holder.reviewsName.setText(recentsName);
        holder.reviewsRating.setRating(reviewsRating);
        holder.reviewsDate.setText(reviewsDate);

        Glide.with(mContext)
                .load(reviewsModelList.get(position).getReviewsImage())
                .into(holder.reviewsImage);
    }

    @Override
    public int getItemCount() {
        return reviewsModelList.size();
    }

    public static class ReviewsViewHolder extends RecyclerView.ViewHolder {
        private TextView reviewsName;
        private TextView reviewsDate;
        private RatingBar reviewsRating;
        private TextView reviewsMessage;
        private ImageView reviewsImage;

        public ReviewsViewHolder(@NonNull View itemView) {
            super(itemView);

            reviewsName = (TextView) itemView.findViewById(R.id.reviewer_name);
            reviewsDate = (TextView) itemView.findViewById(R.id.review_date);
            reviewsRating =  itemView.findViewById(R.id.review_rating);
            reviewsImage = (ImageView) itemView.findViewById(R.id.image_reviewer);
        }
    }
}