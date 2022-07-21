package com.ayush.chattingapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Map;

public class FirebaseMessagingReceiver extends FirebaseMessagingService {

//    @Override
//    public void onNewToken(String token) {
//        Log.d("TAG", "New token: " + token);
//        Main.tt.setText(token);
//
//    }

    // Override onMessageReceived() method to extract the
    // title and
    // body from the message passed in FCM

//    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void
    onMessageReceived(RemoteMessage remoteMessage) {
        Database db=new Database(this);
//        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        System.out.println("fffffffffffffffffffffffffffffffffffffffffffffffffffff"+remoteMessage);


        Map<String, String> data = remoteMessage.getData();

        //you can get your text message here.
        String text= data.get("text");
        String text1= data.get("sender");
        System.out.println(text);
        db.message(text1,Globals.uname,text,1);
//        RelativeLayout child_ll = (RelativeLayout) getLayoutInflater().inflate(R.layout.message_outgoing, null);
        if(text1.matches(Globals.uname)){
        TextView chile_txt_view = Globals.rl.findViewById(R.id.out);
        chile_txt_view.setText(text);
        Globals.ll.addView(Globals.rl);}
//        show_Notification();





    }
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public void show_Notification(){
//
//        Intent intent=new Intent(getApplicationContext(),FirebaseMessagingReceiver.class);
//        String CHANNEL_ID="MYCHANNEL";
//        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"name",NotificationManager.IMPORTANCE_LOW);
//        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),1,intent,0);
//        Notification notification=new Notification.Builder(getApplicationContext(),CHANNEL_ID)
//                .setContentText("Heading")
//                .setContentTitle("subheading")
//                .setContentIntent(pendingIntent)
//                .addAction(android.R.drawable.sym_action_chat,"Title",pendingIntent)
//                .setChannelId(CHANNEL_ID)
//                .setSmallIcon(android.R.drawable.sym_action_chat)
//                .build();
//
//        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.createNotificationChannel(notificationChannel);
//        notificationManager.notify(1,notification);
//
//
//    }



}
