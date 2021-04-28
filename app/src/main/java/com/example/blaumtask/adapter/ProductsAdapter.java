package com.example.blaumtask.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.blaumtask.R;
import com.example.blaumtask.models.ProductsModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {

    private final Context mContext;
    private String TAG = "RecyclerViewAdapterTAG";
    private List<ProductsModel> productsModelArray;
    RecyclerViewClickListener itemClickListener;
    private int row_index = -1;

    public ProductsAdapter(Context mContext, List<ProductsModel> productsModelArray, RecyclerViewClickListener itemClickListener) {
        this.mContext = mContext;
        this.productsModelArray = productsModelArray;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_products_item, parent, false);
        ProductsViewHolder viewHolder = new ProductsViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        String productName = productsModelArray.get(position).getProductName();
        String productPrice = productsModelArray.get(position).getProductPrice();
        String productRate = productsModelArray.get(position).getRating();

        holder.productName.setText(productName);
        holder.productPrice.setText(productPrice);
        holder.productRate.setText(productRate);

        Glide.with(mContext)
                .load(productsModelArray.get(position).getImage())
                .into(holder.productImage);

        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                Log.d("clickListener","clicked adapter" + row_index);
                itemClickListener.recyclerViewClickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsModelArray.size();
    }

    public static class ProductsViewHolder extends RecyclerView.ViewHolder {

        private TextView productName;
        private TextView productPrice;
        private TextView productRate;
        private ImageView productImage;
        private ConstraintLayout foregroundLayout;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = (TextView) itemView.findViewById(R.id.product_name);
            productPrice = (TextView) itemView.findViewById(R.id.product_price);
            productRate = (TextView) itemView.findViewById(R.id.rating_text);
            productImage = (ImageView) itemView.findViewById(R.id.product_image);
            foregroundLayout = itemView.findViewById(R.id.foreground_layout);
            

        }
    }

}
