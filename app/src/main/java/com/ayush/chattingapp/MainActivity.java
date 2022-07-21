package com.ayush.chattingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    EditText e;
    EditText e2;
    Button b;
    Database db=new Database(this);
    String a="user_detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isEmpty(a)) {
            setContentView(R.layout.home);
            e = findViewById(R.id.e1);
            e2 = findViewById(R.id.e2);
            b = findViewById(R.id.log);
            TextView t;
         //   t = findViewById(R.id.fd);
            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> {
                String fcm_token = instanceIdResult.getToken();
                Globals.rl= (RelativeLayout) getLayoutInflater().inflate(R.layout.message_incoming,null);


            b.setOnClickListener(
                    new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            String name
                                    = e.getText()
                                    .toString();
                            String name2 = e2.getText().toString();

                            System.out.println(name+"            ggg           "+name2);

                            db.add_user(name,name2,fcm_token);



                        }
                    });





                    System.out.println(fcm_token);
//                    Log.d("MYTAG", "This is your Firebase token" + fcm_token);
           //     t.setText(fcm_token);

            });
            //  ();


        }
        else{
          //  setContentView(R.layout.chat_container);
            Globals.uname=db.user_info();
            Intent intent=new Intent(this, Main.class);
            startActivity(intent);

        }



//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> {
//            String fcm_token = instanceIdResult.getToken();
//
//            System.out.println(fcm_token);
//            Log.d("MYTAG", "This is your Firebase token" + fcm_token);
//
//
//
//            // send it to server
//        });
//        senPushdNotification("fdddddddddd","dffffffffff","dKY1u1ePRKCHZxyjtGXQcc:APA91bF1gZt9rZRLr69EHDTlkEe_DpFZYXSEg6bnsZssNje25-xJgrtS9CqUYlHXWjSbgrI9TIM9i0OKacSJr6t7u7KuKkFirugHxCSMjMQBsgiBQQIHQoLFCRKizOzhQn8IQKJIuL_e");
//
    }

    public boolean isEmpty(String TableName){

        SQLiteDatabase database = db.getReadableDatabase();
        long NoOfRows = DatabaseUtils.queryNumEntries(database,TableName);
        System.out.println(NoOfRows);

        if (NoOfRows == 0){
            return true;
        } else {
            return false;
        }
    }


}