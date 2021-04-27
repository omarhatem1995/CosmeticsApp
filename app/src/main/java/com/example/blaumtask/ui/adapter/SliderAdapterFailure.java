package com.example.blaumtask.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.blaumtask.R;
import com.example.blaumtask.ui.SliderItem;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class SliderAdapterFailure extends SliderViewAdapter<SliderAdapterFailure.SliderAdapterVH> {

    private Context context;
    private ArrayList<SliderItem> mSliderItems = new ArrayList<>();
    public SliderAdapterFailure(Context context) {
        this.context = context;
    }

    public void renewItems(ArrayList<SliderItem> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_slider, parent, false);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        SliderItem sliderItem = mSliderItems.get(position);
        Glide.with(viewHolder.itemView)
                .load(context.getDrawable(sliderItem.getImage()))
                .into(viewHolder.image);
        viewHolder.title.setText(sliderItem.getTitle());
        viewHolder.message.setText(sliderItem.getMessage());
    }

    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    static class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
        View itemView;
        ImageView image;
        TextView title;
        TextView message;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            image = itemView.findViewById(R.id.itemSliderImage);
            title = itemView.findViewById(R.id.itemSliderTitle);
            message = itemView.findViewById(R.id.itemSliderMessage);
        }
    }
}