package com.example.jarvis.loginapplication;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by jarvis on 1/10/2017.
 */

public class SessionMerchant {
        SharedPreferences prefs;
        SharedPreferences.Editor editor;
        Context ctx;
        public static final String KEY_NAME="name";
        public static final String KEY_MOBILE="mobile";
        public static final String KEY_ADDRESS="address";
        public static final String KEY_PASSWORD="password";
        public static final String KEY_ROOMS="rooms";

        public SessionMerchant (Context ctx){
            this.ctx=ctx;
            prefs=ctx.getSharedPreferences("jarvis102", Context.MODE_PRIVATE);
            editor=prefs.edit();
        }
        public void setLoggedin(boolean logggedin, String name, String mobile,String address, int room){
            editor.putBoolean("loggedInmode",logggedin);
            editor.putString(KEY_NAME, name);
            editor.putString(KEY_MOBILE, mobile);
            editor.putString(KEY_ADDRESS, address);
            editor.putInt(KEY_ROOMS, room);
            editor.commit();
        }

        public boolean loggedin(){
            return prefs.getBoolean("loggedInmode", false);
        }

//        public HashMap<String, String> getUserDetails() {
//            HashMap<String, String> user = new HashMap<String, String>();
//            user.put(KEY_NAME, prefs.getString(KEY_NAME, null));
//            user.put(KEY_MOBILE, prefs.getString(KEY_MOBILE, null));
//            user.put(KEY_ADDRESS, prefs.getString(KEY_ADDRESS, null));
//            user.put(KEY_ROOMS, prefs.getInt(KEY_ROOMS,null));
//            return user;
//        }

}
