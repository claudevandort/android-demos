package com.example.claudio.roomfragmentnotes.application;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class RoomFragmentNotesApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}