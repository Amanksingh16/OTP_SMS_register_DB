package com.example.amansingh.permission_sms.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.amansingh.permission_sms.utils.Constants;

/**
 * Created by Aman Singh on 4/27/2018.
 */

public class TableClass extends SQLiteOpenHelper
{
       Context context;
    public TableClass(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, Constants.Database_name, null, Constants.Database_version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
           String query = "CREATE TABLE IF NOT EXISTS "+Constants.Table_name+"( "
                   +Constants.ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+Constants.USER_NAME+" VARCHAR,"+Constants.PASSWORD+
                   " VARCHAR,"+Constants.PHONE+" VARCHAR);";
           db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
         onCreate(db);
    }
}
