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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;


public class Main {
    public static void a() {
        try{
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

        HttpPost httppost = new HttpPost("https://dc1a-112-196-170-84.ngrok.io/upload");
        File file = new File("C:\\Users\\ayush\\Pictures\\Screenshots\\Screenshot (19).png");

        MultipartEntity mpEntity = new MultipartEntity();
        FileBody cbFile = new FileBody(file, "image/jpeg");
        mpEntity.addPart("userfile", cbFile);


        httppost.setEntity(mpEntity);
        System.out.println("executing request " + httppost.getRequestLine());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();

        System.out.println(response.getStatusLine());
        if (resEntity != null) {
            System.out.println(EntityUtils.toString(resEntity));
        }
        if (resEntity != null) {

                resEntity.consumeContent();

        }

        httpclient.getConnectionManager().shutdown();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}