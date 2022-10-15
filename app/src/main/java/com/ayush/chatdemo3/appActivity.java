// this class is to check whether the app is closed or not

package com.ayush.chatdemo3;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import java.util.List;

public class appActivity extends Service {

    @Override
    public void onTaskRemoved(Intent rootIntent) {

        super.onTaskRemoved(rootIntent);
        new RequestTask2().execute(Globals.url+"/status/"+Globals.user+"/0");
        System.out.println("closed ddddddddddddddddddd");
        
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}