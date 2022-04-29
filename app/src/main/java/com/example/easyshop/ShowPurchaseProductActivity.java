package com.example.easyshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.easyshop.Model.AdminOrders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowPurchaseProductActivity extends AppCompatActivity {
    private RecyclerView ordersList2;
    private DatabaseReference ordersRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_purchase_product);

        ordersRef2= FirebaseDatabase.getInstance().getReference().child("Orders");

        ordersList2=findViewById(R.id.orders_list2);
        ordersList2.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<AdminOrders> options=
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                        .setQuery(ordersRef2,AdminOrders.class)
                        .build();
        FirebaseRecyclerAdapter<AdminOrders, ShowPurchaseProductActivity.AdminOrdersViewHolder> adapter=
                new FirebaseRecyclerAdapter<AdminOrders, ShowPurchaseProductActivity.AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ShowPurchaseProductActivity.AdminOrdersViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull AdminOrders model) {

                        holder.userName.setText("Name: "+model.getName());
                        holder.userPhoneNumber.setText("Phone: "+model.getPhone());
                        holder.userTotalPrice.setText("Total Amount= Rs."+model.getTotalAmount());
                        holder.userDateTime.setText("Order at: "+model.getDate() + " "+model.getTime());
                        holder.userShippingAddress.setText("Shipping Address: "+model.getAddress() + ", "+model.getCity());

                        holder.ShowOrdersBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String uID= getRef(position).getKey();
                                Intent intent= new Intent(ShowPurchaseProductActivity.this,AdminUserProductsActivity.class);
                                intent.putExtra("uid",uID);
                                startActivity(intent);
                            }
                        });
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[] = new CharSequence[]{
                                        "Yes",
                                        "No"
                                };
                                AlertDialog.Builder builder= new AlertDialog.Builder(ShowPurchaseProductActivity.this);
                                builder.setTitle("Wants to cancel your order?");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if(which==0){
                                            String uID= getRef(position).getKey();
                                            RemoveOrder(uID);
                                        }
                                        else{
                                            finish();
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ShowPurchaseProductActivity.AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout,parent,false);
                        return new ShowPurchaseProductActivity.AdminOrdersViewHolder(view);
                    }
                };
        ordersList2.setAdapter(adapter);
        adapter.startListening();
    }



   public static  class AdminOrdersViewHolder extends RecyclerView.ViewHolder{

        public TextView userName, userPhoneNumber, userTotalPrice, userDateTime, userShippingAddress;

        public Button ShowOrdersBtn;
        public AdminOrdersViewHolder(View itemView){
            super(itemView);
            userName=itemView.findViewById(R.id.order_user_name);
            userPhoneNumber=itemView.findViewById(R.id.order_phone_number);
            userTotalPrice=itemView.findViewById(R.id.order_total_price);
            userDateTime=itemView.findViewById(R.id.order_date_time);
            userShippingAddress=itemView.findViewById(R.id.order_address_city);
            ShowOrdersBtn=itemView.findViewById(R.id.show_all_products);

        }
    }

    private void RemoveOrder(String uID) {
        ordersRef2.child(uID).removeValue();
    }
    }
