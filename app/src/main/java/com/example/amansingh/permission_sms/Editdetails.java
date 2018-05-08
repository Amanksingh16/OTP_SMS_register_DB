package com.example.amansingh.permission_sms;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amansingh.permission_sms.Database.DBhelper;
import com.example.amansingh.permission_sms.Model.BookData;
import com.example.amansingh.permission_sms.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class Editdetails extends AppCompatActivity {

    EditText username , password , phone;
    Button register;
    DBhelper dBhelper;
    Intent intent;
    String user;
    List<BookData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdetails);

        username = (EditText)findViewById(R.id.useredit);
        password = (EditText)findViewById(R.id.passedit);
        phone = (EditText)findViewById(R.id.phoneedit);
        register=(Button)findViewById(R.id.edit);
        dBhelper = DBhelper.getinstance(this);
        intent = getIntent();
        user = intent.getStringExtra(Constants.USER_NAME);
        list = dBhelper.getfullcontent(" where "+Constants.USER_NAME+" ='"+user+"';");
        username.setText(list.get(0).getUsername());
        password.setText(list.get(0).getPassword());
        phone.setText(list.get(0).getPhone());

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(Constants.USER_NAME,username.getText().toString());
                contentValues.put(Constants.PASSWORD,password.getText().toString());
                contentValues.put(Constants.PHONE,phone.getText().toString());
                dBhelper.updatecontentvalues(Constants.Table_name,contentValues,Constants.USER_NAME+" ='"+user+"';",null);
                Toast.makeText(Editdetails.this, "Details Updated", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Editdetails.this,Home.class);
                i.putExtra(Constants.USER_NAME,username.getText().toString());
                startActivity(i);
                finish();
            }
        });
    }
}
