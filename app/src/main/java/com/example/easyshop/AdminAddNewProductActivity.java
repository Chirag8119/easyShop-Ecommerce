package com.example.easyshop;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.easyshop.Seller.SellerHomeActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProductActivity extends AppCompatActivity {


    private String CategoryName,Description,price,pName,saveCurrentDate,saveCurrentTime;
    private Button AddNewProductButton;
    private EditText InputProductName, InputProductDescription, InputProductPrice;
    private ImageView InputProductImage;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String productRandomKey, downloadImageUrl;
    private StorageReference ProductImageRef;
    private DatabaseReference ProductRef, sellersRef;
    private ProgressDialog loadingBar;

    private String sName, sAddress, sPhone, sEmail, sID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        CategoryName = getIntent().getExtras().get("category").toString();
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");
       sellersRef=FirebaseDatabase.getInstance().getReference().child("Sellers");
        loadingBar = new ProgressDialog(this);


        AddNewProductButton = (Button) findViewById(R.id.add_new_product);
        InputProductImage = (ImageView) findViewById(R.id.select_product_image);
        InputProductName = (EditText) findViewById(R.id.product_name);
        InputProductDescription = (EditText) findViewById(R.id.product_description);
        InputProductPrice = (EditText) findViewById(R.id.product_price);


        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });

        sellersRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    sName = snapshot.child("name").getValue().toString();
                    sAddress = snapshot.child("address").getValue().toString();
                    sPhone = snapshot.child("phone").getValue().toString();
                    sID = snapshot.child("sid").getValue().toString();

                    sEmail=snapshot.child("email").getValue().toString();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
    @Override
    public void onActivityResult(ActivityResult result) {
   if(result.getResultCode()== Activity.RESULT_OK){
       Intent data = result.getData();
       if(data!=null){
           ImageUri=data.getData();
           InputProductImage.setImageURI(ImageUri);
       }
   }
    }
});
   private void OpenGallery(){
        Intent galleryIntent = new Intent();
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        someActivityResultLauncher.launch(galleryIntent);
    }

  

            /*private void OpenGallery() {

                Intent galleryIntent=new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GalleryPick);

            }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GalleryPick && requestCode==RESULT_OK && data!=null){
            ImageUri=data.getData();
            InputProductImage.setImageURI(ImageUri);
        }

    }
    private void ValidateProductData(){
        Description=InputProductDescription.getText().toString();
        price = InputProductPrice.getText().toString();
        pName=InputProductName.getText().toString();
        if(ImageUri==null){
            Toast.makeText(this, "Product image is mandatory ...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Description)){
            Toast.makeText(this, "Please write product description ...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(price)){
            Toast.makeText(this, "Please write product price ...", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(pName)){
            Toast.makeText(this, "Please write product Name ...", Toast.LENGTH_SHORT).show();
        }
        else{
            StoreProductInformation();
        }


    }

    private void StoreProductInformation() {

        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Dear Seller, Please wait, while we are adding a product");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar =Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MM dd,yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey=saveCurrentDate+saveCurrentTime;
        StorageReference filePath= ProductImageRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask=filePath.putFile(ImageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
           String message=e.toString();
                Toast.makeText(AdminAddNewProductActivity.this, "Error: "+message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddNewProductActivity.this, "Image uploaded Successfully ..", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask= uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();

                        }
                        downloadImageUrl=filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                           downloadImageUrl=task.getResult().toString();
                            Toast.makeText(AdminAddNewProductActivity.this, "Got the image url successfully ...", Toast.LENGTH_SHORT).show();
                         SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });


    }

    private void SaveProductInfoToDatabase() {
        HashMap<String,Object> productMap = new HashMap<>();
        productMap.put("pid",productRandomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("description",Description);
        productMap.put("image",downloadImageUrl);
        productMap.put("category",CategoryName);
        productMap.put("price",price);
        productMap.put("pname",pName);

        productMap.put("sellerName",sName);
        productMap.put("sellerAddress",sAddress);
        productMap.put("sellerPhone",sPhone);
        productMap.put("SellerEmail",sEmail);
        productMap.put("sid",sID);
        productMap.put("productsState","Not Approved");

        ProductRef.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(AdminAddNewProductActivity.this, SellerHomeActivity.class);
                    startActivity(intent);
                    loadingBar.dismiss();
                    Toast.makeText(AdminAddNewProductActivity.this, "Product is added Successfully ...", Toast.LENGTH_SHORT).show();
                }
                else{
                    loadingBar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(AdminAddNewProductActivity.this, "Error : "+message, Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}