package com.ayush.chattingapp;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.datatransport.Encoding;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.internal.http.HttpMethod;

public class Main extends AppCompatActivity {

    Button srch=findViewById(R.id.search);
    EditText t=findViewById(R.id.get);
    public HttpURLConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_view);


        srch.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                t.setVisibility(View.VISIBLE);

            }
        });

        t.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String value = t.getText().toString();
                    performSearch(value);
                    return true;
                }
                return false;
            }
        });


       





    }
    void performSearch(String v){

        Globals.runame=v;
        try {
            getHTML("https://ec9b-112-196-170-84.ngrok.io/get-user-info?username="+Globals.runame);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public Void getHTML(String uri) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(uri);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }

        }
        System.out.println(result.toString());
        String jsonString=result.toString();
        JSONObject obj = new JSONObject(jsonString);
        //String pageName = obj.getJSONObject("pageInfo").getString("pageName");
        JSONArray arr = obj.getJSONArray("data");
        Globals.token=arr.getJSONObject(1).getString("token");
        Globals.fname=arr.getJSONObject(3).getString("first_name");
        Globals.lname=arr.getJSONObject(4).getString("last_name");

        Intent intent=new Intent(this, MainActivity2.class);
        startActivity(intent);




        return null;
    }

}