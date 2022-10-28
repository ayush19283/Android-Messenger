package com.ayush.chatdemo3;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Iterator;
import java.util.List;

public class Login extends AppCompatActivity {
    EditText login_username;
    Button login,register,btn_options;
    String data,fcm_token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // for hiding task bar and changing color of status bar
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.blue));
        }

        setContentView(R.layout.activity_login);
        Intent intent = new Intent(getApplicationContext(),appActivity.class);
        startService(intent);
        Globals.chat=findViewById(R.id.chats_list);
        Globals.incoming=(RelativeLayout) getLayoutInflater().inflate(R.layout.message_incoming, null);
        Globals.incoming_child=Globals.incoming.findViewById(R.id.in);
        Globals.mm=Login.this;
        Globals.lf=getLayoutInflater();
        btn_options=findViewById(R.id.options);
        register=findViewById(R.id.ask_reg);
        login=findViewById(R.id.login);
        login_username=findViewById(R.id.log_username);
        btn_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data=login_username.getText().toString();
                Globals.user=data;
                System.out.println(data);
                new RequestTask2().execute(Globals.url+"/status/"+Globals.user+"/1");
                FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
                    if (!TextUtils.isEmpty(token)) {
                        fcm_token=token;
                        Log.d(TAG, "retrieve token successful : " + token);
                        System.out.println(Globals.user);
                        new RequestTask2().execute(Globals.url+"/update/"+Globals.user+"/"+token);
                    } else{
                        Log.w(TAG, "token should not be null...");
                    }
                }).addOnFailureListener(e -> {
                }).addOnCanceledListener(() -> {
                }).addOnCompleteListener(task -> Log.v(TAG, "This is the token : " + task.getResult()));

                startActivity(new Intent(Login.this, MainActivity.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });


    }
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
     //   builder.setTitle("Safaricom");
        builder.setMessage("Enter forwarding URL");
        View view = this.getLayoutInflater().inflate(R.layout.dialog_layout, null);
        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton("YES", (dialog, which) -> {
            EditText editTextPIN = view.findViewById(R.id.editTextInput);
            String pin = editTextPIN.getText().toString();
            Globals.url=pin;
            new RequestTask2().execute(Globals.url+"/update/"+Globals.user+"/"+fcm_token);
            Toast.makeText(this, pin, Toast.LENGTH_SHORT).show();

        }).setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
