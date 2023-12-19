package com.demo.batteryanim.notifications;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.demo.batteryanim.R;
import com.demo.batteryanim.activities.Anim_MainActivity;


public class Anim_Notification extends Notification {
    String CHANNEL_ID = "channel_name_battery";
    public NotificationCompat.Builder builder;
    Context context;
    NotificationManager notificationManager;

    @SuppressLint("WrongConstant")
    public Anim_Notification(Context context) {
        this.context = context;
        Intent putExtra = new Intent(context, Anim_MainActivity.class).putExtra("optimize", "yes");
        putExtra.setFlags(268468224);
        this.builder = new NotificationCompat.Builder(context, this.CHANNEL_ID).setSmallIcon(R.drawable.icon200).setContentTitle("Charging Animation").setContentText("App is running").setStyle(new NotificationCompat.BigTextStyle()).setAutoCancel(true).setPriority(1).setContentIntent(PendingIntent.getActivity(context, 0, putExtra, PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT));
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            this.notificationManager.createNotificationChannel(new NotificationChannel(this.CHANNEL_ID, "Channel Name", NotificationManager.IMPORTANCE_HIGH));
        }
    }

    public void updateStatus(String str) {
        this.builder.setContentTitle(str);
        Notification build = this.builder.build();
        build.flags |= 32;
        this.notificationManager.notify(1, build);
    }
}
