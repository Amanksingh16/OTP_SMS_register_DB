package com.example.amansingh.permission_sms.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.amansingh.permission_sms.Model.BookData;
import com.example.amansingh.permission_sms.utils.Constants;

import java.util.LinkedList;
import java.util.List;

public class DBhelper {
    private SQLiteDatabase db;
    private Context context;
    private final TableClass dbhelper_table;
    private static DBhelper dBhelper = null;

    public static DBhelper getinstance(Context context) {
        try {
            if (dBhelper == null) {
                dBhelper = new DBhelper(context);
                dBhelper.open();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dBhelper;
    }

    public DBhelper(Context context) {
        this.context = context;
        dbhelper_table = new TableClass(context, Constants.Database_name, null, Constants.Database_version);
    }

    public void open() {
        try {
            db = dbhelper_table.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
            db = dbhelper_table.getReadableDatabase();
        }
    }

    public void close() {
        if (db.isOpen()) {
            db.close();
        }
    }

    public long insertContentvalues(String tabname, ContentValues cv) {
        long count = 0;
        try {
            db.beginTransaction();
            db.insert(tabname, null, cv);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return count;
    }

    public int updatecontentvalues(String tabname, ContentValues cv, String where, String[] whereargs) {
        int count = 0;
        try {
            db.beginTransaction();
            db.update(tabname, cv, where, whereargs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return count;
    }

    public void deleterecords(String tabname, String where, String[] whereargs) {
        try {
            db.beginTransaction();
            db.delete(tabname, where, whereargs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public int getcontentcount(String tabname, String where) {
        int count = 0;
        Cursor c = db.query(false,tabname,null,where,null,null,null,null,null);
        if(c!=null)
        {
            c.moveToFirst();
            count = c.getCount();
            Toast.makeText(context, "Count of the records"+count, Toast.LENGTH_SHORT).show();
            c.close();
        }
        return count;
    }

    public List<BookData> getfullcontent(String where)
    {
        List<BookData> list = new LinkedList<>();
        String query = "Select * from "+Constants.Table_name+where;
        BookData book = null;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            do {
                  book = new BookData();
                  book.setId(cursor.getInt(0));
                  book.setUsername(cursor.getString(1));
                  book.setPassword(cursor.getString(2));
                  book.setPhone(cursor.getString(3));
                  list.add(book);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
