package com.ayush.chattingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "user_detail";
    private static final String TABLE_NAME2 = "msg";

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE user_detail (u_name string , psw string, token string)";
        db.execSQL(query);

        String query2="CREATE TABLE msg (id primary key autoincrement , user string, msg string, type int, time  DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(query2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
        Log.d("a", "hereeeeeeeeeeeeee");

    }
}
