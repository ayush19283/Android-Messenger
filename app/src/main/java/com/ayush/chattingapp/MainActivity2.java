package com.ayush.chattingapp;

//import android.widget.TextView;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//public class Main {
//    public static TextView tt;
//    public static TextView t1;
//
//    HttpEntity entity = MultipartEntityBuilder.create()
//            .addPart("file", new FileBody(file))
//            .build();
//
//    HttpPost request = new HttpPost(url);
//    request.setEntity(entity);
//
//    HttpClient client = HttpClientBuilder.create().build();
//    HttpResponse response = client.execute(request);
//}

//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpVersion;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.mime.MultipartEntity;
//import org.apache.http.entity.mime.content.ContentBody;
//import org.apache.http.entity.mime.content.FileBody;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.params.CoreProtocolPNames;
//import org.apache.http.util.EntityUtils;
//
//import java.io.File;
//import java.io.IOException;
//
//
//public class Main {
//    public static void a() {
//        try{
//        HttpClient httpclient = new DefaultHttpClient();
//        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
//
//        HttpPost httppost = new HttpPost("https://dc1a-112-196-170-84.ngrok.io/upload");
//        File file = new File("C:\\Users\\ayush\\Pictures\\Screenshots\\Screenshot (19).png");
//
//        MultipartEntity mpEntity = new MultipartEntity();
//        FileBody cbFile = new FileBody(file, "image/jpeg");
//        mpEntity.addPart("userfile", cbFile);
//
//
//        httppost.setEntity(mpEntity);
//        System.out.println("executing request " + httppost.getRequestLine());
//        HttpResponse response = httpclient.execute(httppost);
//        HttpEntity resEntity = response.getEntity();
//
//        System.out.println(response.getStatusLine());
//        if (resEntity != null) {
//            System.out.println(EntityUtils.toString(resEntity));
//        }
//        if (resEntity != null) {
//
//                resEntity.consumeContent();
//
//        }
//
//        httpclient.getConnectionManager().shutdown();
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//}
//}

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity2 extends AppCompatActivity {

    public HttpURLConnection conn;
    Button bt;

    EditText editText;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_view);
        editText =findViewById(R.id.et);
        Database db=new Database(this);
        Globals.ll =findViewById(R.id.chats);

        TextView t;
        t= findViewById(R.id.username);
        t.setText(Globals.runame);

        bt =findViewById(R.id.btn_send);

        bt.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v)
                    {
                        String name
                                = editText.getText()
                                .toString();

                        db.message(Globals.uname,Globals.runame,name,1);
                        RelativeLayout child_ll = (RelativeLayout) getLayoutInflater().inflate(R.layout.message_outgoing, null);
//                        Globals.rl= (RelativeLayout) getLayoutInflater().inflate(R.layout.message_incoming,null);
                        TextView chile_txt_view = child_ll.findViewById(R.id.out);
                        chile_txt_view.setText(name);
                        Globals.ll.addView(child_ll);

                        try {
                            HttpURLConnection httpcon = (HttpURLConnection) ((new URL("https://fcm.googleapis.com/fcm/send").openConnection()));

                            httpcon.setDoOutput(false);
                            httpcon.setRequestProperty("Content-Type", "application/json");
                            httpcon.setRequestProperty("Authorization", "key=AAAAuPFssR8:APA91bHshseC3GkODd-OYw92L4ZrN7oZHKxYe9mnMkOoeD8oRp_gGDeu6cR0eV-3ilR3yRNIe1aebNviAc74OX2kGNb0-ja0iUX88EFgG5X1Xqv06YtzllP-2dHeAEfmmtv9E_LBnkec");
                            httpcon.setRequestMethod("GET");
                            httpcon.connect();


                            byte[] outputBytes = ("{\"to\":\""+Globals.token+"\"," +
                                    "\"data\":{\"text\":\""+name+"\",\"sender\":\""+Globals.uname+"\"}," +

                                    "\"priority\":\"high\"," +
                                    "\"content_available\":true}").getBytes("UTF-8");


                            OutputStream os = httpcon.getOutputStream();
                            os.write(outputBytes);
                            os.close();

                            InputStream input = httpcon.getInputStream();
                            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
                                for (String line; (line = reader.readLine()) != null;) {
                                    System.out.println(line);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }


                });

    }


    }







