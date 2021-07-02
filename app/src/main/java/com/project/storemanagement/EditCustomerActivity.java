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

public class EditCustomerActivity extends AppCompatActivity {

    EditText updateCusName, updateCusPhone, updateCusDebt;
    String strCusName, strCusPhone;
    int intCusDebt;

    Button btnUpdate, btnDelete, btnSend;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);

        updateCusName = (EditText) findViewById(R.id.update_cus_name);
        updateCusPhone = (EditText) findViewById(R.id.update_cus_phone);
        updateCusDebt= (EditText) findViewById(R.id.update_cus_debt);
        btnUpdate = (Button) findViewById(R.id.btn_update_cus);
        btnDelete = (Button) findViewById(R.id.btn_delete_cus);
        btnSend = (Button) findViewById(R.id.btn_send_msg_cus);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        Bundle bundle = this.getIntent().getExtras();
        if (bundle!=null){
            strCusName = bundle.getString("cus_name");
            strCusPhone = bundle.getString("cus_phone");
            intCusDebt= bundle.getInt("cus_debt");

            updateCusName.setText(strCusName);
            updateCusPhone.setText(strCusPhone);
            updateCusDebt.setText(Integer.toString(intCusDebt));

        }

        String id = currentUser.getUid();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditCustomerActivity.this, SendMessageActivity.class);
                intent.putExtra("phone_number", strCusPhone);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Users").child(id).child("Customers").child(strCusPhone).removeValue();
                startActivity(new Intent(EditCustomerActivity.this, CustomerActivity.class));
                finish();
            }
        });



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cu_name = updateCusName.getText().toString();
                String cu_phone = strCusPhone;
                int cu_debt=Integer.parseInt(updateCusDebt.getText().toString());

                if(cu_name.isEmpty()){
                    Toast.makeText(EditCustomerActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }if(cu_phone.isEmpty()){
                    Toast.makeText(EditCustomerActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }if(Integer.toString(cu_debt).isEmpty()){
                    Toast.makeText(EditCustomerActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Customer customer = new Customer(cu_name, cu_phone, cu_debt);


                databaseReference.child("Users").child(id).child("Customers").child(cu_phone).setValue(customer);

                startActivity(new Intent(EditCustomerActivity.this, CustomerActivity.class));
                finish();
            }
        });

    }
}