package com.ayush.chatdemo3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

public class chats extends AppCompatActivity {
    TextView header;
    EditText msg;
    Button send;
    Database db = new Database(this);

    String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.green));
        }

        setContentView(R.layout.activity_chats);
        Globals.ll =findViewById(R.id.chats);
        header=findViewById(R.id.username);
        msg=findViewById(R.id.et);
        send=findViewById(R.id.btn_send);
        Globals.scrollView=findViewById(R.id.message_area);
        header.setText(Globals.username);


       // send message button
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text=msg.getText().toString();
                if(text.matches("")){
                    System.out.println("nothing");
                }else{
                db.deleteTitle(Globals.username);
                db.add_contact(Globals.username);
                db.show_contact();
                Collections.reverse(Globals.contacts);

                Date d=new Date();
                SimpleDateFormat sdf=new SimpleDateFormat("hh:mm");
                String currentDateTimeString = sdf.format(d);
                System.out.println("dsdffdf  adsfdf "+currentDateTimeString);

                Globals.message=text;
                msg.getText().clear();
                RelativeLayout child_ll = (RelativeLayout) getLayoutInflater().inflate(R.layout.outgoing_message, null);
                TextView chile_txt_view = child_ll.findViewById(R.id.out);

                SpannableString ss1=  new SpannableString(text);
                ss1.setSpan(new RelativeSizeSpan(1.1f), 0,text.length(), 0); // set size
                ss1.setSpan(new ForegroundColorSpan(Color.BLACK), 0, text.length(), 0);// set color

                chile_txt_view.setText(ss1+" ");

                SpannableString ss2=  new SpannableString(currentDateTimeString);
                ss2.setSpan(new RelativeSizeSpan(0.8f), 0,currentDateTimeString.length(), 0); // set size
                ss2.setSpan(new ForegroundColorSpan(Color.GRAY), 0, currentDateTimeString.length(), 0);// set color
                chile_txt_view.append(ss2);

                Globals.ll.addView(child_ll);
                // to make scroll downwards
                Globals.scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        Globals.scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
                new RequestTask().execute("https://fcm.googleapis.com/fcm/send"); //fcm api to send message 
                }

            }
        });

    }
}