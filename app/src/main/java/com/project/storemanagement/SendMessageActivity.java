package com.project.storemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendMessageActivity extends AppCompatActivity {

    EditText txt_message;
    String phone_number;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        txt_message = (EditText) findViewById(R.id.messge_edit);
        button = (Button) findViewById(R.id.btn_send_msg);
        phone_number = getIntent().getStringExtra("phone_number");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_send(view);
            }
        });
    }

    public void btn_send(View view){
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
            MyMessage();
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
        }
    }

    public void MyMessage() {
        String Message = txt_message.getText().toString().trim();
        if(!txt_message.getText().toString().equals("")){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone_number, null, Message, null, null);

            Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Please enter the Message", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 0:
                if(grantResults.length>=0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    MyMessage();
                }
                else {
                    Toast.makeText(this, "You don't have required Permission to make the Action", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}