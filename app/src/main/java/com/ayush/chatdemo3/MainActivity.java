package com.ayush.chatdemo3;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String search_data;
    Button btn_search;
    EditText searched;
    TextView title;
    RelativeLayout tool_bar;


    Database db= new Database(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.green));
        }

        setContentView(R.layout.activity_main);

        db.show_contact();
        Globals.chat=findViewById(R.id.chats_list);
        Globals.chat_child=(RelativeLayout) getLayoutInflater().inflate(R.layout.contacts, null);
        Globals.text_child=Globals.chat_child.findViewById(R.id.contact);
//      thread to continuously updating the ui when new message is recieved or contact is added
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while(true) {
                        sleep(1000);
                        Globals.mm.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Globals.chat.removeAllViews();
                                System.out.println("here ");
                                for (int i = 0; i < Globals.contacts.size(); i++) {

                                    System.out.println(Globals.contacts.get(i));
                                    //clickable dynamic text views
                                View.OnClickListener btnClickListener = new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            System.out.println("id id id "+Globals.contacts.get(v.getId()));
                                            Globals.username= Globals.contacts.get(v.getId());
                                            new RequestTask2().execute(Globals.url+"/setSelected/"+Globals.user+"/"+Globals.username);
                                            new RequestTask2().execute(Globals.url+"/show/"+Globals.username);
                                            startActivity(new Intent(MainActivity.this, chats.class));

                                        }
                                    };

                                    Globals.chat_child=(RelativeLayout) getLayoutInflater().inflate(R.layout.contacts, null);
                                    Globals.text_child=Globals.chat_child.findViewById(R.id.contact);
                                    Globals.text_child.setText(Globals.contacts.get(i));
                                    Globals.text_child.setId(i);
                                    Globals.text_child.setOnClickListener(btnClickListener);
                                    Globals.chat.addView(Globals.chat_child);

                                }

                            }
                        });

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

        tool_bar=findViewById(R.id.toolbar);
        title=findViewById(R.id.title);
        btn_search=findViewById(R.id.search);
        searched=findViewById(R.id.get);

        // to give options for settings and add contact
        btn_search.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(MainActivity.this, btn_search);
                popup.getMenuInflater()
                        .inflate(R.drawable.popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("Add contact")){
                            System.out.println(item.getTitle());
                            searched.setVisibility(View.VISIBLE);
                            searched.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(searched, InputMethodManager.SHOW_IMPLICIT);
                            btn_search.setVisibility(View.GONE);
                            title.setVisibility(View.GONE);
                            tool_bar.setBackgroundColor(Color.WHITE);
                        }
                        Toast.makeText(
                                MainActivity.this,
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();
                        return true;
                    }
                });
                popup.show();

            }
        });

        searched.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                search_data=searched.getText().toString();
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    System.out.print(search_data);
                    if(Globals.contacts.contains(search_data)){
                        System.out.println("old user");
                    }else{
                    Globals.contacts.add(search_data);
                    db.add_contact(search_data);}
//                    Globals.params="/show/"+search_data;
                    Globals.username=search_data;
                    new RequestTask2().execute(Globals.url+"/show/"+search_data);
                    startActivity(new Intent(MainActivity.this, chats.class));

                    return true;
                }
                return false;
            }

        });


    }

    }
}