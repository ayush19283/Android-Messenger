package com.ayush.chatdemo3;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

import com.google.firebase.messaging.FirebaseMessaging;

public class Register extends AppCompatActivity {
    EditText uname;
    Button register;
    String data;
    String fcm_token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.blue));
        }

        setContentView(R.layout.activity_register);
        uname=findViewById(R.id.username);
        register=findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data=uname.getText().toString();
                FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
                    if (!TextUtils.isEmpty(token)) {
//                        Globals.token=token;
                        fcm_token=token;
                        new RequestTask2().execute(Globals.url+"/register/"+data+"/"+fcm_token);

//                        Globals.params="/register/"+data+"/"+token;
                        Log.d(TAG, "retrieve token successful : " +fcm_token);
                    } else{
                        Log.w(TAG, "token should not be null...");
                    }
                }).addOnFailureListener(e -> {
                    //handle e
                }).addOnCanceledListener(() -> {
                    //handle cancel
                }).addOnCompleteListener(task -> Log.v(TAG, "This is the token : " + task.getResult()));

//                Globals.params="/register/"+data+"/"+fcm_token;
//                System.out.println(Globals.params);

                startActivity(new Intent(Register.this, Login.class));

            }
        });

    }
}