package com.husen.sqlite.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.husen.sqlite.model.User;

/**
 * Created by Administrator on 5/5/2016.
 */
public class Session {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;
    public static String EMAIL = "Email";
    public static String NAME = "Name";


    public Session(Context ctx){
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("sqlite", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoggedin(boolean logggedin){
        editor.putBoolean("loggedInmode",logggedin);
        editor.commit();
    }

    public boolean loggedin(){
        return prefs.getBoolean("loggedInmode", false);
    }


    public void setUserData(User user) {
        editor.putString(EMAIL,user.getUserEmail());
        editor.putString(NAME,user.getUserName());
        editor.apply();
    }
}
