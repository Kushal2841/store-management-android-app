package com.project.storemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SigninActivity extends AppCompatActivity{


        TextView txtSignUp;
        Button btnSignin;
        EditText editUsername;
        EditText editPassw;
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
        FirebaseAuth mAuth;
        ProgressDialog progressDialog;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signin);

            txtSignUp = findViewById(R.id.text_sign_up);
            btnSignin = findViewById(R.id.btn_sign_in);
            editUsername = findViewById(R.id.edit_username);
            editPassw = findViewById(R.id.edit_password);


            //
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();
            mAuth = FirebaseAuth.getInstance();

            progressDialog = new ProgressDialog(SigninActivity.this);
            progressDialog.setTitle("Login");
            progressDialog.setMessage("Login to your account");




            btnSignin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String username = editUsername.getText().toString();
                    final String password = editPassw.getText().toString();
                    if(username.isEmpty()){
                        editUsername.setError("Enter your mail");
                        return;
                    }if(password.isEmpty()){
                        editPassw.setError("Enter your password");
                        return;
                    }
                    progressDialog.show();


                    mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()){
                                Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(SigninActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });

            txtSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
                    startActivity(intent);
                }
            });
        }




    }
