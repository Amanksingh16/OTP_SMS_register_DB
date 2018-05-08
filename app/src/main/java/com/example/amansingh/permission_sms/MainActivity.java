package com.example.amansingh.permission_sms;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amansingh.permission_sms.Database.DBhelper;
import com.example.amansingh.permission_sms.Model.BookData;
import com.example.amansingh.permission_sms.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText user , pass;
    Button login;
    TextView register;
    DBhelper dBhelper;
    List<BookData> dataList;
    int count;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (EditText)findViewById(R.id.user);
        pass = (EditText)findViewById(R.id.pass);
        login = (Button)findViewById(R.id.login);
        register = (TextView)findViewById(R.id.register);
        dBhelper = DBhelper.getinstance(this);

        dataList = dBhelper.getfullcontent("");
        final List<String> username = new ArrayList<>();
        final List<String> password = new ArrayList<>();
        for (int i=0;i<dataList.size();i++)
        {
            username.add(dataList.get(i).getUsername());
            password.add(dataList.get(i).getPassword());
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                  Intent i = new Intent(MainActivity.this,Register.class);
                  startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < username.size(); i++)
                {
                    count = 0;
                    if (user.getText().toString().equals(username.get(i)))
                    {
                        if(pass.getText().toString().equals(password.get(i)))
                        {
                            Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            count = 1;
                            Intent intent = new Intent(MainActivity.this,Home.class);
                            intent.putExtra(Constants.USER_NAME,user.getText().toString());
                            startActivity(intent);
                            break;
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Password incorrect", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
                if(count != 1)
                {
                    Toast.makeText(MainActivity.this, "User does not exist , register here", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
