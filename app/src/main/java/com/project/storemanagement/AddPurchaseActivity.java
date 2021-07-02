package com.project.storemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddPurchaseActivity extends AppCompatActivity {

    EditText editTextPurchaseCus, editTextPurchasePro, editTextPurchaseQty, editTextPurCusName, editTextPurPrice;

    Button btnaddPurchase;
    Spinner purchaseType;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, dbref2;
    FirebaseUser currentUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purchase);


        editTextPurchaseCus = findViewById(R.id.edit_purchase_customer);
        editTextPurchasePro = findViewById(R.id.edit_purchase_product);
        editTextPurchaseQty = findViewById(R.id.edit_purchase_quantity);
        editTextPurCusName = findViewById(R.id.edit_purchase_customer_name);
        editTextPurPrice = findViewById(R.id.edit_purchase_price);
        btnaddPurchase = findViewById(R.id.btn_add_purchase);
        purchaseType = findViewById(R.id.spinner_purchase);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        dbref2 = firebaseDatabase.getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.payment_status, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        purchaseType.setAdapter(adapter);

        String id = currentUser.getUid();





        btnaddPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String customerPhone = editTextPurchaseCus.getText().toString();
                String product = editTextPurchasePro.getText().toString();
                int quantity = Integer.parseInt(editTextPurchaseQty.getText().toString());
                String status = purchaseType.getSelectedItem().toString();
                int price = Integer.parseInt(editTextPurPrice.getText().toString());
                String customerName = editTextPurCusName.getText().toString();




                if(customerPhone.isEmpty()){
                    Toast.makeText(AddPurchaseActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }if(customerName.isEmpty()){
                    Toast.makeText(AddPurchaseActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }if(product.isEmpty()){
                    Toast.makeText(AddPurchaseActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }if(Integer.toString(quantity).isEmpty()){
                    Toast.makeText(AddPurchaseActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }if(Integer.toString(price).isEmpty()){
                    Toast.makeText(AddPurchaseActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                int amount = price * quantity;




                Purchase purchase = new Purchase(customerName, customerPhone, product, status, quantity, amount);

                databaseReference.child("Users").child(id).child("Purchases").push().setValue(purchase);


                databaseReference.child("Users").child(id).child("Items").child(product).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Item item = snapshot.getValue(Item.class);

                        int qty = item.getQuantity();

                        int new_qty = qty - quantity;
                        dbref2.child("Users").child(id).child("Items").child(product).child("quantity").setValue(new_qty);
//                        dbref2.child("Users").child(id).child("Purchases").child(customerPhone+product).child("amountPur").setValue(amt);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                if (status.equals("Pending")) {
//                    Toast.makeText(AddPurchaseActivity.this, "customers", Toast.LENGTH_SHORT).show();

                    databaseReference.child("Users").child(id).child("Customers").child(customerPhone).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Customer customer = snapshot.getValue(Customer.class);
                                int deb = customer.getDebt();
                                int new_debt = deb + amount;
                                dbref2.child("Users").child(id).child("Customers").child(customerPhone).child("debt").setValue(new_debt);
                            }
                            else{
                                Customer customer = new Customer(customerName, customerPhone, amount);
                                dbref2.child("Users").child(id).child("Customers").child(customerPhone).setValue(customer);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

                startActivity(new Intent(AddPurchaseActivity.this, AllPurchasesActivity.class));
                finish();
            }
        });





    }
}