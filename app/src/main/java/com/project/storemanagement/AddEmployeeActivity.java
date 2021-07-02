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

public class AddEmployeeActivity extends AppCompatActivity {

    EditText editTextEmpName, editTextEmpPhone, editTextEmpSalary, editTextEmpLeaves;

    Button btnAddEmp;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        editTextEmpName = findViewById(R.id.edit_emp_name);
        editTextEmpPhone = findViewById(R.id.edit_emp_phone);
        editTextEmpSalary = findViewById(R.id.edit_emp_salary);
        editTextEmpLeaves= findViewById(R.id.edit_emp_leaves);
        btnAddEmp = findViewById(R.id.btn_add_emp);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();


        btnAddEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emp_name = editTextEmpName.getText().toString();
                String emp_phone=editTextEmpPhone.getText().toString();
                int emp_salary=Integer.parseInt(editTextEmpSalary.getText().toString());
                int emp_leaves=Integer.parseInt(editTextEmpLeaves.getText().toString());

                if(emp_name.isEmpty()){
                    Toast.makeText(AddEmployeeActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }if(emp_phone.isEmpty()){
                    Toast.makeText(AddEmployeeActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }if(Integer.toString(emp_salary).isEmpty()){
                    Toast.makeText(AddEmployeeActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }if(Integer.toString(emp_leaves).isEmpty()){
                    Toast.makeText(AddEmployeeActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Employee employee = new Employee(emp_name, emp_phone, emp_salary, emp_leaves);
                String id = currentUser.getUid();
                databaseReference.child("Users").child(id).child("Employees").child(emp_phone).setValue(employee);
                startActivity(new Intent(AddEmployeeActivity.this, PaymentsActivity.class));
                finish();
            }
        });
    }
}