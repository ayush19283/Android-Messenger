package com.ayush.chatdemo3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Database extends SQLiteOpenHelper {
    int prior;
    private static final String DB_NAME = "storage";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "user";
    private static final String TABLE_NAME2="friends";
    private static final String SEN = "sender";
    private static final String REC = "receiver";
    private static final String MSG="msg";

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query2="CREATE TABLE "+TABLE_NAME+" (id integer primary key autoincrement , sender string, receiver string, msg string, time  DATETIME DEFAULT CURRENT_TIMESTAMP, read integer)";
        String query1="CREATE TABLE "+TABLE_NAME2+"(uname string , priority integer )";
        db.execSQL(query1);
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS msgg" );
        onCreate(db);
    }
    public boolean deleteTitle(String name)
    {   SQLiteDatabase db= this.getWritableDatabase();
        System.out.println("deleted    "+name);
        return db.delete(TABLE_NAME2, "uname" + "=" +"'"+ name+"'", null) > 0;
    }

    public int get_priority(String name){

        SQLiteDatabase db =this.getWritableDatabase();
        System.out.println("get_priority    "+name);
        Cursor a = db.rawQuery("SELECT priority FROM friends where uname="+"'"+name+"'",null);
        if (a.moveToFirst()) {
            do {
                prior=a.getInt(0);
                System.out.println(a.getString(0));
            } while (a.moveToNext());
        }
        a.close();
        return prior;

    }
    public void rearrange(int number){
        SQLiteDatabase db = this.getWritableDatabase();

        if(number==1){
            System.out.println("already at first position");
        }else{
            String strSQL = String.format("UPDATE friends SET priority = %1$d WHERE priority = %2$d", 2000,number);
            db.execSQL(strSQL);
            for(int i=1;i<number;i++){
                String strSQL1 = String.format("UPDATE friends SET priority = %1$d WHERE priority = %2$d", i+1,i);
                db.execSQL(strSQL1);
            }
            String strSQL3 = String.format("UPDATE friends SET priority = %1$d WHERE priority = %2$d", 1,2000);
            db.execSQL(strSQL3);
        }
        Cursor a = db.rawQuery("SELECT * FROM friends ORDER BY priority",null);
        if (a.moveToFirst()) {
            do {
                System.out.println("show contact "+a.getString(0)+"     "+a.getString(1));

                Globals.contacts.add(a.getString(0));


            } while (a.moveToNext());
        }
        a.close();
    }
    public void add_contact(String name){
        System.out.println("add_contact   "+name);
        SQLiteDatabase db = this.getWritableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME2);
        System.out.println(count);
        ContentValues values = new ContentValues();
        values.put("uname", name);
        values.put("priority",1);
        db.insert(TABLE_NAME2, null, values);

        db.close();
    }

    public void show_contact(){
        Globals.contacts.clear();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor a = db.rawQuery("SELECT uname FROM friends",null);
        if (a.moveToFirst()) {
            do {
                System.out.println("show contact "+a.getString(0));
                Globals.contacts.add(a.getString(0));
            } while (a.moveToNext());
        }
        a.close();
    }

    public void insert(String sen, String rec, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SEN, sen);
        values.put(REC, rec);
        values.put(MSG, message);
        values.put("read",0);
        SimpleDateFormat gmtDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        gmtDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        System.out.println("Current Date and Time in UTC time zone: " + gmtDateFormat.format(new Date()));
        values.put("time",gmtDateFormat.format(new Date()));
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void show_msg(String sender,String receiver){
        SQLiteDatabase db = this.getWritableDatabase();
        String s= String.format("SELECT * from user where (receiver='%1$s' and sender='%2$s')or(sender='%1s' and receiver='%2$s')",receiver,sender);
        Cursor cursorCourses = db.rawQuery(s, null);
        if (cursorCourses.moveToFirst()) {
            do {

                System.out.println(cursorCourses.getString(0)+"      "+cursorCourses.getString(1)+"       "+cursorCourses.getString(2)+"          "+cursorCourses.getString(3)+"    "+cursorCourses.getString(4));
            } while (cursorCourses.moveToNext());
        }
        cursorCourses.close();
    }
}
