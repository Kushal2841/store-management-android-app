package com.project.storemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {

    RecyclerView recyclerCusList;
    FloatingActionButton fab_add;


    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser currentUser;

    ArrayList<Customer> list;
    CustomerAdapter customerAdapter;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);


        recyclerCusList = findViewById(R.id.recycler_cuslist);
        fab_add = findViewById(R.id.fab_add_customers);
        recyclerCusList.setHasFixedSize(true);
        recyclerCusList.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        list = new ArrayList<>();
        customerAdapter = new CustomerAdapter(this, list);
        recyclerCusList.setAdapter(customerAdapter);


        String id = currentUser.getUid();

        databaseReference.child(id).child("Customers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    Customer customers = dataSnapshot1.getValue(Customer.class);
//
                    list.add(customers);
                }
                customerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerActivity.this, AddCustomerActivity.class));
                finish();
            }
        });




    }
}