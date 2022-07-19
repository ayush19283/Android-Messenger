package com.ayush.chattingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView t;
        t=findViewById(R.id.fd);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> {
                    String fcm_token = instanceIdResult.getToken();

//                    System.out.println(fcm_token);
//                    Log.d("MYTAG", "This is your Firebase token" + fcm_token);
                    t.setText(fcm_token);

        });
      //  ();






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
//    public static void d(){
//        Main m=new Main();
//
//        m.a();
//    }

//    public static void senPushdNotification(final String body, final String title, final String fcmToken) {
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... params) {
//                try {
//                    OkHttpClient client = new OkHttpClient();
//                    JSONObject json = new JSONObject();
//                    JSONObject notificationJson = new JSONObject();
//                    JSONObject dataJson = new JSONObject();
//                    notificationJson.put("text", body);
//                    notificationJson.put("title", title);
//                    notificationJson.put("priority", "high");
//                    dataJson.put("customId", "02");
//                    dataJson.put("badge", 1);
//                    dataJson.put("alert", "Alert");
//                    json.put("notification", notificationJson);
//                    json.put("data", dataJson);
//                    json.put("to", fcmToken);
//                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());
//                    Request request = new Request.Builder()
//                            .header("Authorization", "key=AAAAuPFssR8:APA91bHshseC3GkODd-OYw92L4ZrN7oZHKxYe9mnMkOoeD8oRp_gGDeu6cR0eV-3ilR3yRNIe1aebNviAc74OX2kGNb0-ja0iUX88EFgG5X1Xqv06YtzllP-2dHeAEfmmtv9E_LBnkec")
//                            .url("https://fcm.googleapis.com/fcm/send")
//                            .post(body)
//                            .build();
//                    Response response = client.newCall(request).execute();
//                    String finalResponse = response.body().string();
//                    Log.i("TAG", finalResponse);
//                } catch (Exception e) {
//
//                    Log.i("TAG", e.getMessage());
//                }
//                return null;
//            }
//        }.execute();
//    }
}