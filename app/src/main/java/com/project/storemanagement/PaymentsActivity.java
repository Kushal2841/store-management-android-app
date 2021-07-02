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

public class PaymentsActivity extends AppCompatActivity {

    RecyclerView recyclerEmpList;
    FloatingActionButton fab_addEmp;


    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser currentUser;

    ArrayList<Employee> list;
    EmployeeAdapter employeeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        recyclerEmpList = findViewById(R.id.rec_view_emp);
        fab_addEmp = findViewById(R.id.fab_add_employee);
        recyclerEmpList.setHasFixedSize(true);
        recyclerEmpList.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        list = new ArrayList<>();
        employeeAdapter = new EmployeeAdapter(this, list);
        recyclerEmpList.setAdapter(employeeAdapter);


        String id = currentUser.getUid();

        databaseReference.child(id).child("Employees").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    Employee employees = dataSnapshot1.getValue(Employee.class);
//
                    list.add(employees);
                }
                employeeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fab_addEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentsActivity.this, AddEmployeeActivity.class));
            }
        });

    }
}