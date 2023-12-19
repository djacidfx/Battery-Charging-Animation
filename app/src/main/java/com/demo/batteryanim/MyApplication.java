package com.demo.batteryanim;

import android.app.Application;


public class MyApplication extends Application {
    private static MyApplication mInstance;


    public static String privacy_policy_url = "https://www.google.com/";


    @Override 
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static MyApplication getInstance() {
        return mInstance;
    }
}
