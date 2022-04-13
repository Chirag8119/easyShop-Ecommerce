package com.example.easyshop.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.easyshop.Interface.ItemClickListener;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, txtProductDescription, txtProductPrice;
    public ImageView txtImageView;
    public ItemClickListener listener;


    public ProductViewHolder(View itemView)
    {
        super(itemView);

        txtImageView = (ImageView) itemView.findViewById(com.example.easyshop.R.id.product_image);
       txtProductDescription=(TextView) itemView.findViewById(com.example.easyshop.R.id.product_description);
        txtProductName=(TextView) itemView.findViewById(com.example.easyshop.R.id.product_name);
        txtProductPrice=(TextView) itemView.findViewById(com.example.easyshop.R.id.product_price);



    }
    public void setItemClickListener(ItemClickListener listener){
        this.listener=listener;
    }


    @Override
    public void onClick(View v) {
        listener.onClick(v,getBindingAdapterPosition(),false );

    }
}
