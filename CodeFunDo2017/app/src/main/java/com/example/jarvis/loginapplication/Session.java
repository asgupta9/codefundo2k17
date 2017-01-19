package com.example.jarvis.loginapplication;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by jarvis on 1/7/2017.
 */

public class Session {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;
    public static final String KEY_NAME="name";
    public static final String KEY_MOBILE="mobile";
    public static final String KEY_CITY="city";
    public static final String KEY_DOB="dob";

    public Session (Context ctx){
        this.ctx=ctx;
        prefs=ctx.getSharedPreferences("jarvis102", Context.MODE_PRIVATE);
        editor=prefs.edit();
    }
    public void setLoggedin(boolean logggedin, String name, String mobile, String dob,String city){
        editor.putBoolean("loggedInmode",logggedin);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_MOBILE, mobile);
        editor.putString(KEY_DOB, dob);
        editor.putString(KEY_CITY, city);
        editor.commit();
    }

    public boolean loggedin(){
        return prefs.getBoolean("loggedInmode", false);
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_NAME, prefs.getString(KEY_NAME, null));
        user.put(KEY_MOBILE, prefs.getString(KEY_MOBILE, null));
        user.put(KEY_DOB, prefs.getString(KEY_DOB, null));
        user.put(KEY_CITY, prefs.getString(KEY_CITY, null));
        return user;
    }

}
