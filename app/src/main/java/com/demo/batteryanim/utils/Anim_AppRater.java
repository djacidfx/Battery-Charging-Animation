package com.demo.batteryanim.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.BaseAnimation;


public class Anim_AppRater {
    private static int DAYS_UNTIL_PROMPT = 1;
    public static final int MAX_NEVER_PROMPT = 1;
    public static final int MAX_RATE_PROMPT = 2;
    public static final int MAX_REMIND_PROMPT = 5;
    public static Context context;
    private static Long first_launch_date_time;
    private static Long launch_date_time;
    private static int never_count;
    private static int rate_count;
    private static int total_launch_count;

    public static void app_launched(Context context2, int i, int i2, int i3, int i4) {
        context = context2;
        SharedPreferences sharedPreferences = context2.getSharedPreferences("app_rater", 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        Log.d("Apprater", "Called");
        total_launch_count = sharedPreferences.getInt("total_launch_count", 1);
        never_count = sharedPreferences.getInt("never_count", 1);
        rate_count = sharedPreferences.getInt("rate_count", 1);
        if (!sharedPreferences.getBoolean("do_not_show_again", false)) {
            Long valueOf = Long.valueOf(sharedPreferences.getLong("first_launch_date_time", 0));
            first_launch_date_time = valueOf;
            if (valueOf.longValue() == 0) {
                Long valueOf2 = Long.valueOf(System.currentTimeMillis());
                first_launch_date_time = valueOf2;
                edit.putLong("first_launch_date_time", valueOf2.longValue());
            }
            launch_date_time = Long.valueOf(sharedPreferences.getLong("launch_date_time", 0));
            if (System.currentTimeMillis() >= launch_date_time.longValue() + 86400000 && DAYS_UNTIL_PROMPT <= 5) {
                edit.putLong("launch_date_time", System.currentTimeMillis());
                DAYS_UNTIL_PROMPT++;
            }
            int i5 = total_launch_count;
            if (i5 <= 5) {
                if (edit != null) {
                    edit.putInt("total_launch_count", i5 + 1);
                    edit.apply();
                }
                if (total_launch_count == 1) {
                    showRateDialogExclusive(context, i, i3, i4);
                } else if (System.currentTimeMillis() >= launch_date_time.longValue() + 86400000) {
                    showRateDialogExclusive(context, i, i3, i4);
                }
            }
            if (edit != null) {
                edit.commit();
            }
        }
    }

    public static int dpToPx(int i) {
        return (int) (((float) i) * Resources.getSystem().getDisplayMetrics().density);
    }

    public static void showRateDialogExclusive(final Context context2, int i, int i2, int i3) {
        View inflate = ((Activity) context2).getLayoutInflater().inflate(i, (ViewGroup) null);
        final Dialog dialog = new Dialog(context2);
        dialog.getWindow().setBackgroundDrawableResource(17170445);
        dialog.setCancelable(false);
        ((TextView) inflate.findViewById(i3)).setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + context2.getPackageName()));
                intent.addFlags(1208483840);
                try {
                    context2.startActivity(intent);
                } catch (ActivityNotFoundException unused) {
                    Context context3 = context2;
                    context3.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + context2.getPackageName())));
                }
                dialog.dismiss();
            }
        });
        ((TextView) inflate.findViewById(i2)).setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(inflate, new LinearLayout.LayoutParams(dpToPx(BaseAnimation.DEFAULT_ANIMATION_TIME), dpToPx(250)));
        dialog.show();
    }
}
