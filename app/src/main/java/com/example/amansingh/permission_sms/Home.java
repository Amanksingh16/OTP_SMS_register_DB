package com.example.amansingh.permission_sms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.amansingh.permission_sms.Database.DBhelper;
import com.example.amansingh.permission_sms.Model.BookData;
import com.example.amansingh.permission_sms.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
    List<BookData> list;
    Button edit;
    DBhelper dBhelper;
    Intent intent;
    String user;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        listView = (ListView)findViewById(R.id.list_students);
        edit = (Button)findViewById(R.id.editdetails);
        dBhelper = DBhelper.getinstance(this);
        intent = getIntent();
        user = intent.getStringExtra(Constants.USER_NAME);
        list = dBhelper.getfullcontent(" where "+Constants.USER_NAME+" ='"+user+"';");
        List<String> details = new ArrayList<String>();
        details.add(list.get(0).getUsername());
        details.add(list.get(0).getPassword());
        details.add(list.get(0).getPhone());
        adapter = new ArrayAdapter<String>(Home.this,android.R.layout.simple_list_item_1,details);
        listView.setAdapter(adapter);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, Editdetails.class);
                i.putExtra(Constants.USER_NAME,user);
                startActivity(i);
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu)
    {
        if(menu.getItemId() == R.id.logout)
        {
            Intent i = new Intent(Home.this,MainActivity.class);
            startActivity(i);
            Toast.makeText(this, "Logged out Successfully", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(menu);
    }
}
