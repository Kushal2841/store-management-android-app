package com.project.storemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CardView cardViewEmplist, cardViewItemlist, cardViewPurchList, cardViewPayments, cardViewLogout;
//    TextView txtFirstname;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardViewEmplist = findViewById(R.id.card_emplist);
        cardViewItemlist = findViewById(R.id.card_items);
        cardViewPurchList = findViewById(R.id.card_purchases);
        cardViewPayments = findViewById(R.id.card_payments);
        cardViewLogout = findViewById(R.id.card_logout);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

//        txtFirstname = findViewById(R.id.txt_firstname);

        cardViewEmplist.setOnClickListener(this);
        cardViewItemlist.setOnClickListener(this);
        cardViewPurchList.setOnClickListener(this);
        cardViewPayments.setOnClickListener(this);
        cardViewLogout.setOnClickListener(this);

        //
        String id = currentUser.getUid();
        databaseReference.child(id).child("details");
//        txtFirstname.setText();



    }



    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.card_emplist:
                Intent intent = new Intent(MainActivity.this, CustomerActivity.class);
                startActivity(intent);
                break;
            case R.id.card_items:
                Intent intent1 = new Intent(MainActivity.this, ItemlistActivity.class);
                startActivity(intent1);
                break;
            case R.id.card_purchases:
                Intent intent2 = new Intent(MainActivity.this, AllPurchasesActivity.class);
                startActivity(intent2);
                break;
            case R.id.card_payments:
                Intent intent3 = new Intent(MainActivity.this, PaymentsActivity.class);
                startActivity(intent3);
                break;
            case R.id.card_logout:
                user_logout();
                break;

        }

    }

    private void user_logout() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to logout?");

        builder.setPositiveButton("LOGOUT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                SharedPreferences userPref = getApplicationContext().getSharedPreferences("UserPref", MODE_PRIVATE);
//                final String username = (userPref.getString("user_username",""));

                SharedPreferences userPref = getApplicationContext().getSharedPreferences("UserPref", MODE_PRIVATE);
                final SharedPreferences.Editor editor = userPref.edit();

                editor.clear();
                editor.commit();
                Intent intent = new Intent(MainActivity.this, SigninActivity.class);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }
}