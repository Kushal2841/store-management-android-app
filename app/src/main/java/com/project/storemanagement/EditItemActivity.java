package com.project.storemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditItemActivity extends AppCompatActivity {

    EditText updateItemName, updateItemStock, updateItemPrice;
    String strItemName;
    int inteItemStock;
    int intItemPrice;

    Button btnUpdate, btnDelete;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);


        updateItemName = (EditText) findViewById(R.id.update_item_name);
        updateItemPrice = (EditText) findViewById(R.id.update_item_price);
        updateItemStock = (EditText) findViewById(R.id.update_item_stock);
        btnUpdate = (Button) findViewById(R.id.btn_update_item);
        btnDelete = (Button) findViewById(R.id.item_delete_btn);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        Bundle bundle = this.getIntent().getExtras();
        if (bundle!=null){
            strItemName = bundle.getString("item_name");
            intItemPrice = bundle.getInt("item_price");
            inteItemStock = bundle.getInt("item_stock");

            updateItemName.setText(strItemName);
            updateItemPrice.setText(Integer.toString(intItemPrice));
            updateItemStock.setText(Integer.toString(inteItemStock));


        }

        String id = currentUser.getUid();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Users").child(id).child("Items").child(strItemName).removeValue();
                startActivity(new Intent(EditItemActivity.this, ItemlistActivity.class));
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String it_name = strItemName;
                int it_price=Integer.parseInt(updateItemPrice.getText().toString());
                int it_qty=Integer.parseInt(updateItemStock.getText().toString());

                if(it_name.isEmpty()){
                    Toast.makeText(EditItemActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }if(Integer.toString(it_price).isEmpty()){
                    Toast.makeText(EditItemActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }if(Integer.toString(it_qty).isEmpty()){
                    Toast.makeText(EditItemActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Item item = new Item(it_name, it_price, it_qty);


                databaseReference.child("Users").child(id).child("Items").child(it_name).setValue(item);

                startActivity(new Intent(EditItemActivity.this, ItemlistActivity.class));
                finish();
            }
        });

    }
}