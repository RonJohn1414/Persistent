package com.example.persistdatatest.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String name = "database" ;
    private static final int version = 1;

    public DatabaseHelper(Context context) {
        super (context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
// we are going to create the tables that are going to be used
        String userQuery = "CREATE TABLE "+
                UserTable.TABLE_NAME+
                " ("+
                UserTable._ID+
                " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                UserTable.COLUMN_NAME+
                " VARCHAR (255), "+
                UserTable.COLUMN_ADDRESS+
                " VARCHAR(255), "+
                UserTable.COLUMN_AGE+
                " INT, "+
                UserTable.COLUMN_DOB+
                " VARCHAR (255));";
        db.execSQL (userQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
