package com.example.easyshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyshop.Model.Users;
import com.example.easyshop.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import org.w3c.dom.Text;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    private EditText InputPhoneNumber, InputPasswords;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    private String parentDbName="Users";
    private CheckBox checkBox;
    private TextView AdminLink, NotAdminLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = (Button) findViewById(R.id.login_btn);
        InputPasswords = (EditText) findViewById(R.id.login_password_input);
        InputPhoneNumber = (EditText) findViewById(R.id.login_phone_number_input);
        loadingBar = new ProgressDialog(this);
        AdminLink = (TextView) findViewById(R.id.admin_panel_link);
        NotAdminLink = (TextView) findViewById(R.id.not_admin_panel_link);

        checkBox = (CheckBox) findViewById(R.id.remember_me_chkb);
        Paper.init(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }

        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDbName="Admins";

            }
        });
        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDbName="Users";
            }
        });
    }

            private void LoginUser() {
                String phone=InputPhoneNumber.getText().toString();
                String password=InputPasswords.getText().toString();
                 if(TextUtils.isEmpty((phone))){
                    Toast.makeText(LoginActivity.this,"Please write your Phone Number...",Toast.LENGTH_LONG).show();

                }
                else if(TextUtils.isEmpty((password))){
                    Toast.makeText(LoginActivity.this,"Please write your Password...",Toast.LENGTH_LONG).show();

                }
                else{
                     loadingBar.setTitle("Login Account");
                     loadingBar.setMessage("Please wait, while we are checking credentials");
                     loadingBar.setCanceledOnTouchOutside(false);
                     loadingBar.show();
                     
                     AllowAccessToAccount(phone,password);
                 }
            }

            private void AllowAccessToAccount(String phone, String password) {

                if(checkBox.isChecked()){
                    Paper.book().write(Prevalent.UserPhoneKey,phone);
                    Paper.book().write(Prevalent.UserPasswordKey,password);

                }

                final DatabaseReference RootRef;
                RootRef = FirebaseDatabase.getInstance().getReference();

                RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(parentDbName).child(phone).exists()){
                            Users usersData = snapshot.child(parentDbName).child(phone).getValue(Users.class);
                            if(usersData.getPhone().equals(phone)){
                                if(usersData.getPassword().equals(password)) {
                                     if (parentDbName.equals("Users")) {
                                        Toast.makeText(LoginActivity.this, "Logged in Successfully ...", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        Prevalent.currentOnlineUsers=usersData;
                                        startActivity(intent);
                                    }
                                    else if (parentDbName.equals("Admins")) {
                                        Toast.makeText(LoginActivity.this, "Welcome Admin, You are Logged in Successfully ...", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                        startActivity(intent);
                                    }
                                }
                                else {
                                        loadingBar.dismiss();
                                        Toast.makeText(LoginActivity.this, "Password is Incorrect , Try Again!", Toast.LENGTH_SHORT).show();
                                    }

                            }

                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Account with this "+phone+" number do not exist ...", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "you need to create a new Account", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

}