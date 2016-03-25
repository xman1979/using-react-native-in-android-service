package com.reactnativeandroidservice;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    public static Context context;
    public static Application application;

    @Override public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        application = this;
    }
}