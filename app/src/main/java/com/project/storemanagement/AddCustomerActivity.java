package com.project.storemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCustomerActivity extends AppCompatActivity {

    EditText editTextCusName, editTextCusPhone, editTextCusDebt;

    Button btnAddCus;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        editTextCusName = findViewById(R.id.edit_cus_name);
        editTextCusPhone = findViewById(R.id.edit_cus_phone);
        editTextCusDebt = findViewById(R.id.edit_cus_debt);
        btnAddCus = findViewById(R.id.btn_add_cus);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        btnAddCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cus_name = editTextCusName.getText().toString();
                String cus_phone=editTextCusPhone.getText().toString();
                int cus_debt=Integer.parseInt(editTextCusDebt.getText().toString());

                if(cus_name.isEmpty()){
                    Toast.makeText(AddCustomerActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }if(cus_phone.isEmpty()){
                    Toast.makeText(AddCustomerActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }if(Integer.toString(cus_debt).isEmpty()){
                    Toast.makeText(AddCustomerActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Customer customer = new Customer(cus_name, cus_phone, cus_debt);
                String id = currentUser.getUid();
                databaseReference.child("Users").child(id).child("Customers").child(cus_phone).setValue(customer);
                startActivity(new Intent(AddCustomerActivity.this, CustomerActivity.class));
                finish();
            }
        });
    }
} 