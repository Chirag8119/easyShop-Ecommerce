package com.example.easyshop.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyshop.Interface.ItemClickListener;
import com.example.easyshop.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProductName, txtProductPrice, txtProductQuantity;
    private ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txtProductName=itemView.findViewById(R.id.cart_product_name);
        txtProductPrice=itemView.findViewById(R.id.cart_product_price);
        txtProductQuantity=itemView.findViewById(R.id.cart_product_quantity);
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v, getBindingAdapterPosition(),false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
