package com.example.amansingh.permission_sms;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amansingh.permission_sms.Database.DBhelper;
import com.example.amansingh.permission_sms.Model.BookData;
import com.example.amansingh.permission_sms.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Register extends AppCompatActivity {

    EditText username , password , phone , otp;
    Button register,confirm,send;
    DBhelper dBhelper;
    List<BookData> content ;
    int status = 1;
    int otp_value ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        phone = (EditText)findViewById(R.id.phone);
        otp = (EditText)findViewById(R.id.otp);
        register = (Button)findViewById(R.id.Register);
        confirm = (Button)findViewById(R.id.confirm);
        send = (Button)findViewById(R.id.send);
        dBhelper = DBhelper.getinstance(this);

        register.setEnabled(false);
        confirm.setEnabled(false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "Permission is not granted", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},123);
            send.setEnabled(false);
        }
        else
        {
            Toast.makeText(this, "Permission is granted", Toast.LENGTH_SHORT).show();
            send.setEnabled(true);
        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phone.getText().toString().length() == 10) {
                    sendsms();
                    send.setText("SMS Sent");
                    send.setEnabled(false);
                    confirm.setEnabled(true);
                }
                else
                {
                    Toast.makeText(Register.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                }
            }

        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(otp.getText().toString().equals(String.valueOf(otp_value)))
                {
                    confirm.setText("Confirmed");
                    confirm.setEnabled(false);
                    register.setEnabled(true);
                }
                else
                {
                    Toast.makeText(Register.this, "OTP is not Correct", Toast.LENGTH_SHORT).show();
                    send.setEnabled(true);
                    send.setText("Resend");
                    confirm.setEnabled(false);
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().length() >= 4) {
                    if (password.getText().toString().length() >= 8)
                    {
                insert();
            }
            else
                    {
                        Toast.makeText(Register.this, "Password must have atleast 8 charactrers", Toast.LENGTH_SHORT).show();
                    }
        }
                else
                {
                    Toast.makeText(Register.this, "Username must have atleast 4 charactrers", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void insert() {
        content = dBhelper.getfullcontent("");
        List<String> usernames = new ArrayList<String>();
        for (int i = 0; i < content.size(); i++) {
            usernames.add(content.get(i).getUsername());
        }
        for(int i=0;i<content.size();i++)
        {
            status = 1;
            if (usernames.get(i).equals(username.getText().toString()))
            {
                Toast.makeText(Register.this, "Username already taken", Toast.LENGTH_SHORT).show();
                status = 0;
                break;
            }
        }
        if(status == 1)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Constants.USER_NAME, username.getText().toString());
            contentValues.put(Constants.PASSWORD, password.getText().toString());
            contentValues.put(Constants.PHONE, phone.getText().toString());
            dBhelper.insertContentvalues(Constants.Table_name, contentValues);
            Toast.makeText(Register.this, "Thanks for Registration", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Register.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 123)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission is granted here", Toast.LENGTH_SHORT).show();
                send.setEnabled(true);
            }
            else {
                send.setEnabled(false);
            }
        }
    }
    private void sendsms()
    {
        Random r = new Random();
        otp_value = 100000+r.nextInt(900000);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone.getText().toString(),null,"Please Confirm your number to complete the regustration. Enter the OTP : "+otp_value,null,null);
    }
}
