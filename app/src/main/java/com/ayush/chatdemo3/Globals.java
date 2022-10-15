package com.ayush.chatdemo3;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class Globals {
    public static String message;
    public static String token;
    public static String url="server url";
    public static String params;
    public static String username="";
    public static LinearLayout ll;
    public static RelativeLayout incoming;
    public static TextView incoming_child;
    public static Activity mm;
    public static LayoutInflater lf;
    public static String user;
    public static LinearLayout chat;
    public static RelativeLayout chat_child;
    public static TextView text_child;
    public static ArrayList<String> reverse = new ArrayList<>();
    public static ArrayList<String> contacts = new ArrayList<String>();
    public static TextView incoming_time;
    public static int new_user=0;
    public static ScrollView scrollView;
}
