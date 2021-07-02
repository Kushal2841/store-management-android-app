package com.project.storemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllPurchasesActivity extends AppCompatActivity {

    RecyclerView recyclerPurList;
    FloatingActionButton fab_add;


    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser currentUser;

    ArrayList<Purchase> list;
    PurchaseAdapter purchaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_purchases);


        recyclerPurList = findViewById(R.id.rec_view_purchase);
        fab_add = findViewById(R.id.fab_add_purchase);
        recyclerPurList.setHasFixedSize(true);
        recyclerPurList.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        list = new ArrayList<>();
        purchaseAdapter = new PurchaseAdapter(this, list);
        recyclerPurList.setAdapter(purchaseAdapter);


        String id = currentUser.getUid();

        databaseReference.child(id).child("Purchases").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    Purchase purchase = dataSnapshot1.getValue(Purchase.class);
//
                    list.add(purchase);
                }
                purchaseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllPurchasesActivity.this, AddPurchaseActivity.class));
            }
        });
    }
}