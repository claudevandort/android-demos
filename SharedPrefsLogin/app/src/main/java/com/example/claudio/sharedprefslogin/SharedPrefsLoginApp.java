package com.example.claudio.sharedprefslogin;

import android.app.Application;
import android.os.SystemClock;

public class SharedPrefsLoginApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(1000);
    }
}
