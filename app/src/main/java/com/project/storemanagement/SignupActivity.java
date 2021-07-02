package com.project.storemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity  {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    EditText userLname, userfname, userUsername, userPassw;
    Button btnGetStarted;
    TextView txtSignUp;

    String storeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userLname = findViewById(R.id.edit_user_lastname);
        userfname = findViewById(R.id.edit_user_firstname);
        userUsername = findViewById(R.id.edit_user_username);
        userPassw = findViewById(R.id.edit_user_password);
        btnGetStarted = findViewById(R.id.btn_user_get_started);

        txtSignUp = findViewById(R.id.text_sign_in);




        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are creating your account");

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String txtlname = userLname.getText().toString();
                String txtfname = userfname.getText().toString();
                String txtusername = userUsername.getText().toString();
                String txtpassword = userPassw.getText().toString();
                if (txtlname.isEmpty()){
                    Toast.makeText(SignupActivity.this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (txtfname.isEmpty()){

                    Toast.makeText(SignupActivity.this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (txtusername.isEmpty()){

                    Toast.makeText(SignupActivity.this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (txtpassword.isEmpty()){

                    Toast.makeText(SignupActivity.this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(txtusername, txtpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            Users user = new Users(txtlname, txtfname, txtusername, txtpassword);
                            String id = task.getResult().getUser().getUid();
                            firebaseDatabase.getReference().child("Users").child(id).child("details").setValue(user);

                            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

    }





}