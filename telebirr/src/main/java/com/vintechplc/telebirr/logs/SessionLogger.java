package com.vintechplc.telebirr.logs;

import android.util.Log;

public class SessionLogger {
    public static void log(String tag, String message){
        Log.d("tele-ses-"+tag,message);
    }
}
