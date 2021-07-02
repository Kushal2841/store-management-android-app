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

public class EditEmployeeActivity extends AppCompatActivity {

    EditText updateEmpName, updateEmpPhone, updateEmpSalary, updateEmpLeaves;
    String strEmpName, strEmpPhone;
    int intEmpSal, intEmpLeav;

    Button btnUpdate, btnDelete, btnSend;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);

        updateEmpName = (EditText) findViewById(R.id.update_emp_name);
        updateEmpPhone = (EditText) findViewById(R.id.update_emp_phone);
        updateEmpSalary= (EditText) findViewById(R.id.update_emp_salary);
        updateEmpLeaves = (EditText) findViewById(R.id.update_emp_leaves);
        btnUpdate = (Button) findViewById(R.id.btn_update_emp);
        btnDelete = (Button) findViewById(R.id.btn_delete_emp);
        btnSend =(Button) findViewById(R.id.btn_send_msg_emp);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        Bundle bundle = this.getIntent().getExtras();
        if (bundle!=null){
            strEmpName = bundle.getString("emp_name");
            strEmpPhone = bundle.getString("emp_phone");
            intEmpSal= bundle.getInt("emp_salary");
            intEmpLeav = bundle.getInt("emp_leaves");

            updateEmpName.setText(strEmpName);
            updateEmpPhone.setText(strEmpPhone);
            updateEmpSalary.setText(Integer.toString(intEmpSal));
            updateEmpLeaves.setText(Integer.toString(intEmpLeav));

        }

        String id = currentUser.getUid();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditEmployeeActivity.this, SendMessageActivity.class);
                intent.putExtra("phone_number", strEmpPhone);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Users").child(id).child("Employees").child(strEmpPhone).removeValue();
                startActivity(new Intent(EditEmployeeActivity.this, PaymentsActivity.class));
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em_name = updateEmpName.getText().toString();
                String em_phone = strEmpPhone;
                int em_salary=Integer.parseInt(updateEmpSalary.getText().toString());
                int em_leaves =Integer.parseInt(updateEmpLeaves.getText().toString());

                if(em_name.isEmpty()){
                    Toast.makeText(EditEmployeeActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }if(em_phone.isEmpty()){
                    Toast.makeText(EditEmployeeActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }if(Integer.toString(em_salary).isEmpty()){
                    Toast.makeText(EditEmployeeActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }if(Integer.toString(em_leaves).isEmpty()){
                    Toast.makeText(EditEmployeeActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Employee employee = new Employee(em_name, em_phone, em_salary, em_leaves);


                databaseReference.child("Users").child(id).child("Employees").child(em_phone).setValue(employee);

                startActivity(new Intent(EditEmployeeActivity.this, PaymentsActivity.class));
                finish();
            }
        });
    }
}