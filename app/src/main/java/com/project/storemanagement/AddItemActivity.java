package com.project.storemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.actions.ItemListIntents;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddItemActivity extends AppCompatActivity {

    EditText editTextItemName, editTextPrice, editTextQty;
    String strSelectedCategory;
    String itemId;
    Spinner spinnerCategory;
    Button btnAddItem;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        editTextItemName = findViewById(R.id.edit_item_name);
        editTextPrice = findViewById(R.id.edit_item_price);
        editTextQty = findViewById(R.id.edit_item_quantity);
        btnAddItem = findViewById(R.id.btn_add_item);

        spinnerCategory = findViewById(R.id.spinner_categories);



        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();


        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String it_name = editTextItemName.getText().toString();
                int it_price=Integer.parseInt(editTextPrice.getText().toString());
                int it_qty=Integer.parseInt(editTextQty.getText().toString());

                if(it_name.isEmpty()){
                    Toast.makeText(AddItemActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }if(Integer.toString(it_price).isEmpty()){
                    Toast.makeText(AddItemActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }if(Integer.toString(it_qty).isEmpty()){
                    Toast.makeText(AddItemActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Item item = new Item(it_name, it_price, it_qty);

                String id = currentUser.getUid();
                databaseReference.child("Users").child(id).child("Items").child(it_name).setValue(item);

                startActivity(new Intent(AddItemActivity.this, ItemlistActivity.class));
                finish();
            }
        });



    }
}