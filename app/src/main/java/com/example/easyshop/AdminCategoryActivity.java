package com.example.easyshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {
    private ImageView tShirts, sportsTShirts, femaleDresses, sweathers;
    private ImageView glasses, hatsCaps, walletsBagsPurses, shoes;
    private ImageView headPhone, Laptops, watches, mobilePhone;

    private Button LogoutBtn, CheckOrdersBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        LogoutBtn=(Button) findViewById(R.id.admin_logout_button);
        CheckOrdersBtn=(Button) findViewById(R.id.check_orders_btn);

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminCategoryActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        CheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AdminCategoryActivity.this,AdminNewOrderActivity.class);

                startActivity(intent);
            }
        });

        tShirts =(ImageView) findViewById(R.id.t_shirts);
        sportsTShirts=(ImageView) findViewById(R.id.sports_t_shirts);
        femaleDresses=(ImageView) findViewById(R.id.female_t_shirts);
        sweathers=(ImageView) findViewById(R.id.sweather);
        glasses=(ImageView) findViewById(R.id.glasses);
        hatsCaps=(ImageView) findViewById(R.id.hats);
        shoes=(ImageView)findViewById(R.id.shoes);
        walletsBagsPurses=(ImageView) findViewById(R.id.purses_bags);
        headPhone=(ImageView)findViewById(R.id.headphone);
        Laptops=(ImageView)findViewById(R.id.laptops);
        watches=(ImageView)findViewById(R.id.watches);
        mobilePhone=(ImageView)findViewById(R.id.mobile);

tShirts.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
        intent.putExtra("category","tShirts");
        startActivity(intent);
    }
});
sportsTShirts.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
        intent.putExtra("category","Sports TShirts");
        startActivity(intent);
    }
});
        femaleDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Female Dresses");
                startActivity(intent);
            }
        });
        sweathers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Sweathers");
                startActivity(intent);
            }
        });

        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Glasses");
                startActivity(intent);
            }
        });
        hatsCaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Hats Caps");
                startActivity(intent);
            }
        });
        walletsBagsPurses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Wallets Bags Purses");
                startActivity(intent);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Shoes");
                startActivity(intent);
            }
        });
        headPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","HeadPhones");
                startActivity(intent);
            }
        });
        Laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Laptops");
                startActivity(intent);
            }
        });
        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","Watches");
                startActivity(intent);
            }
        });
        mobilePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","MobilePhones");
                startActivity(intent);
            }
        });

    }
}