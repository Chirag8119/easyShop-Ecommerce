package com.example.easyshop.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.easyshop.Interface.ItemClickListener;
import com.example.easyshop.R;

public class itemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, txtProductDescription, txtProductPrice, txtProductStatus;
    public ImageView txtImageView;
    public ItemClickListener listener;


    public itemViewHolder(View itemView)
    {
        super(itemView);

        txtImageView = (ImageView) itemView.findViewById(com.example.easyshop.R.id.product_seller_image);
        txtProductDescription=(TextView) itemView.findViewById(com.example.easyshop.R.id.product_seller_description);
        txtProductName=(TextView) itemView.findViewById(com.example.easyshop.R.id.seller_product_name);
        txtProductPrice=(TextView) itemView.findViewById(com.example.easyshop.R.id.product_seller_price);
        txtProductStatus=(TextView) itemView.findViewById(R.id.product_state);



    }
    public void setItemClickListener(ItemClickListener listener){
        this.listener=listener;
    }


    @Override
    public void onClick(View v) {
        listener.onClick(v,getBindingAdapterPosition(),false );

    }
}