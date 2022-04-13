package com.example.easyshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchProductActivity extends AppCompatActivity {
    private Button SearchBtn;
    private EditText inputText;
    private RecyclerView searchList;
    private String searchInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        inputText = findViewById(R.id.search_product_name);
        SearchBtn = findViewById(R.id.search_btn);

        searchList=findViewById(R.id.search_list);
        searchList.setLayoutManager(new LinearLayoutManager(SearchProductActivity.this));

        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}