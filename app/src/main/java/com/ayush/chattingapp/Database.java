package com.ayush.chattingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "user_detail";
    private static final String TABLE_NAME2 = "msg";
    Connection con;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE user_detail (u_name string , psw string, token string)";
        db.execSQL(query);

        String query2="CREATE TABLE msg (id integer primary key autoincrement  , sender string, receiver stirng, msg string, type int, time  DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(query2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
        Log.d("a", "hereeeeeeeeeeeeee");

    }
    public String user_info(){
        String value = null;
        Statement stmt = null;

      //  SQLiteDatabase db = this.getWritableDatabase();

        try {
            stmt = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String query = String.format("SELECT u_name from user_detail");
        try {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
                value = rs.getString("u_name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //db.execSQL(query);
        return value;

    }


    public void add_user(String u, String pwd, String token){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("INSERT INTO user_detail (U_name, psw, token) values ('%1$s', '%2$s' , '%3$s')", u,pwd,token);
        db.execSQL(query);



    }

    public void message(String from,String to, String msg,int type){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("INSERT INTO msg (sender, reciever, msg, type, time) values ('%1$s', '%2$s' , '%3$s','1','null')", from,to,msg);
        db.execSQL(query);


    }

}
