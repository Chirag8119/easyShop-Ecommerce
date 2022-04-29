package com.example.easyshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHomeActivity extends AppCompatActivity {
     private Button LogoutBtn, CheckOrdersBtn, maintainProductsBtn, checkApproveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        LogoutBtn=(Button) findViewById(R.id.admin_logout_button);
        CheckOrdersBtn=(Button) findViewById(R.id.check_orders_btn);
        maintainProductsBtn=(Button) findViewById(R.id.maintain_btn);
        checkApproveBtn=findViewById(R.id.check_approve_btn);

        maintainProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this,HomeActivity.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);
            }
        });

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminHomeActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        CheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminHomeActivity.this,AdminNewOrderActivity.class);

                startActivity(intent);
            }
        });
        checkApproveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this,CheckNewProductsActivity.class);
                startActivity(intent);

            }
        });

    }
}