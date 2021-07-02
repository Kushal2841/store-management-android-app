package com.project.storemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
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

public class ItemlistActivity extends AppCompatActivity {

    FloatingActionButton fab_additem;
    RecyclerView recyclerViewItemList;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser currentUser;

    ArrayList<Item> list;
    ItemAdapter itemAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemlist);

        fab_additem = findViewById(R.id.fab_add_items);
        recyclerViewItemList = findViewById(R.id.recycler_itemlist);
        recyclerViewItemList.setHasFixedSize(true);
        recyclerViewItemList.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        list = new ArrayList<>();
        itemAdapter = new ItemAdapter(this, list);
        recyclerViewItemList.setAdapter(itemAdapter);


        String id = currentUser.getUid();

        databaseReference.child(id).child("Items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    Item items = dataSnapshot1.getValue(Item.class);
//                    Item itemlistdata = new Item();
//                    String itemName = items.getName();
//                    int itemQty = items.getQuantity();
//                    int itemPrice = items.getPrice();
//                    itemlistdata.setName(itemName);
//                    itemlistdata.setQuantity(itemQty);
//                    itemlistdata.setPrice(itemPrice);
                    list.add(items);
                }
                itemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fab_additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemlistActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });

    }




}