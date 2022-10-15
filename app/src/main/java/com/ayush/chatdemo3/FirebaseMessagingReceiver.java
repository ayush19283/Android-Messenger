package com.ayush.chatdemo3;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class FirebaseMessagingReceiver extends FirebaseMessagingService {
    String text,sender;
    TextView Time;
    Database db = new Database(this);
    @Override
    public void onNewToken(String token) {
        Log.d("TAG", "New token: " + token);
        System.out.println(token);
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        text= data.get("text"); //data received from fcm body i.e message
        sender=data.get("sender"); // the name of sender
        System.out.println(sender);

        SpannableString ss1=  new SpannableString(text);
        ss1.setSpan(new RelativeSizeSpan(1f), 0,text.length(), 0); // set size
        ss1.setSpan(new ForegroundColorSpan(Color.BLACK), 0, text.length(), 0);

        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm");
        String currentDateTimeString = sdf.format(d);
        System.out.println("dsdffdf  adsfdf "+currentDateTimeString);
        SpannableString ss2=  new SpannableString(currentDateTimeString);
        ss2.setSpan(new RelativeSizeSpan(0.8f), 0,currentDateTimeString.length(), 0); // set size
        ss2.setSpan(new ForegroundColorSpan(Color.GRAY), 0, currentDateTimeString.length(), 0);// set color


        if (Globals.contacts.contains(sender)){

            if(Globals.username.equals(sender)){
                //starting ui thread to update ui inside a service
            Globals.mm.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Globals.incoming=(RelativeLayout) Globals.lf.inflate(R.layout.message_incoming,null);
                    Globals.incoming_child=Globals.incoming.findViewById(R.id.in);
//                    Time=Globals.incoming.findViewById(R.id.incoming_time);
                    Globals.incoming_child.setText(ss1+" ");
                    Globals.incoming_child.append(ss2);
                    Globals.ll.addView(Globals.incoming);
                    //to make scroll downwards automatically
                    Globals.scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            Globals.scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });

                }
            });}

            db.deleteTitle(sender);
            db.add_contact(sender);
            db.show_contact();
            Collections.reverse(Globals.contacts);
        }else{
            db.add_contact(sender);
            Globals.contacts.add(sender);
            Collections.reverse(Globals.contacts);

        }

    }
}
