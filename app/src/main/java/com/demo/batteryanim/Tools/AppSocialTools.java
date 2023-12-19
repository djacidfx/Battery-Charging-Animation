package com.demo.batteryanim.Tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.demo.batteryanim.MyApplication;


public class AppSocialTools {
    private static AppSocialTools appSocialTools;

    public static AppSocialTools getInstance() {
        AppSocialTools appSocialTools2 = appSocialTools;
        if (appSocialTools2 != null) {
            return appSocialTools2;
        }
        AppSocialTools appSocialTools3 = new AppSocialTools();
        appSocialTools = appSocialTools3;
        return appSocialTools3;
    }

    public static boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public String getYdkf() {
        return MyApplication.getInstance().getPackageName();
    }
}
