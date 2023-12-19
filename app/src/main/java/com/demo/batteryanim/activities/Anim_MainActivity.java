package com.demo.batteryanim.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.demo.batteryanim.AdAdmob;
import com.demo.batteryanim.MyApplication;
import com.demo.batteryanim.R;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import com.demo.batteryanim.notifications.Anim_Notification;
import com.demo.batteryanim.service.Anim_Service;
import com.demo.batteryanim.utils.Anim_AppRater;
import com.demo.batteryanim.utils.Anim_SharedPrefsUtils;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;


public class Anim_MainActivity extends AppCompatActivity {
    public static Anim_Notification myNotification;
    public static BroadcastReceiver receiver;
    TextView battrytext;
    PowerManager f129pm;
    boolean first;
    boolean isLock;
    CardView layoutSelect;
    RelativeLayout layoutSettings;
    CardView layoutTut;
    CardView layoutgallary;
    CardView layoutpolicy;
    String packageName;
    Intent serviceIntent;
    long mLastClickTime = 0;
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() { 
        @Override 
        public void onReceive(Context context, Intent intent) {
            float intExtra = ((float) (intent.getIntExtra("level", -1) * 100)) / ((float) intent.getIntExtra("scale", -1));
            TextView textView = Anim_MainActivity.this.battrytext;
            textView.setText(String.valueOf(intExtra) + "%");
        }
    };
    private boolean doubleBackToExitPressedOnce = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static RippleDrawable getBackgroundDrawable(int i, Drawable drawable) {
        return new RippleDrawable(getPressedState(i), drawable, null);
    }

    public static ColorStateList getPressedState(int i) {
        return new ColorStateList(new int[][]{new int[0]}, new int[]{i});
    }

    public static int getBatteryPercentage(Context context) {
        Intent registerReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        return (int) ((((float) registerReceiver.getIntExtra("level", -1)) / ((float) registerReceiver.getIntExtra("scale", -1))) * 100.0f);
    }

    @Override
    
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.anim_activity_main);


        AdAdmob adAdmob = new AdAdmob( this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (this.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{
                        Manifest.permission.POST_NOTIFICATIONS
                }, 22);
            }
        }



        boolean booleanPreference = Anim_SharedPrefsUtils.getBooleanPreference(this, "first", true);
        this.first = booleanPreference;
        if (booleanPreference) {
            startActivity(new Intent(getApplicationContext(), Anim_Tutorial1Activity.class));
            finish();
        }
        initView();
        this.packageName = getPackageName();
        this.f129pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                overlayPermissionPopup();
            } else if (!this.f129pm.isIgnoringBatteryOptimizations(this.packageName)) {
                batteryOptimizationDialog();
            }
        }
        this.layoutgallary.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                try {
                    Anim_MainActivity anim_MainActivity = Anim_MainActivity.this;
                    anim_MainActivity.gallloadAMBAd(anim_MainActivity);
                } catch (Exception unused) {
                }
            }
        });
        this.layoutpolicy.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                try {
                    Anim_MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(MyApplication.privacy_policy_url)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        initReceiever();
        addBroadcastReceiver();
        if (Build.VERSION.SDK_INT < 23) {
            Anim_Notification anim_Notification = new Anim_Notification(this);
            myNotification = anim_Notification;
            anim_Notification.updateStatus("Charging Animation\"");
            prepareForService();
        } else if (Settings.canDrawOverlays(this) && this.f129pm.isIgnoringBatteryOptimizations(this.packageName)) {
            Anim_Notification anim_Notification2 = new Anim_Notification(this);
            myNotification = anim_Notification2;
            anim_Notification2.updateStatus("Charging Animation");
            prepareForService();
        }
        this.layoutSelect.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent(Anim_MainActivity.this, Anim_ListActivity.class);
                try {
                    Anim_MainActivity anim_MainActivity = Anim_MainActivity.this;
                    anim_MainActivity.loadAMBAd(anim_MainActivity, intent);
                } catch (Exception unused) {
                    Anim_MainActivity.this.startActivity(intent);
                }
            }
        });
        this.layoutTut.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent(Anim_MainActivity.this, Anim_Tutorial1Activity.class);
                intent.putExtra("showBack", "yes");
                try {
                    Anim_MainActivity anim_MainActivity = Anim_MainActivity.this;
                    anim_MainActivity.loadAMBAd(anim_MainActivity, intent);
                } catch (Exception unused) {
                    Anim_MainActivity.this.startActivity(intent);
                }
            }
        });

    }

    public void gallloadAMBAd(final Activity activity) {



        if(Build.VERSION.SDK_INT >= 33){
            Intent intent = new Intent("android.intent.action.PICK", MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            intent.setType("video/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            intent.setAction("android.intent.action.OPEN_DOCUMENT");
            intent.addCategory("android.intent.category.OPENABLE");
            Anim_MainActivity.this.startActivityForResult(intent, 1);
        }else{
            requestPermission(1);
        }

    }


    public void requestPermission(final int i) {
        Permissions.check(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, "Please provide Storage Permission for accessing videos.", new Permissions.Options().setRationaleDialogTitle("Info").setSettingsDialogTitle("Warning"), new PermissionHandler() { 
            @Override 
            public void onDenied(Context context, ArrayList<String> arrayList) {
            }

            @Override 
            public void onGranted() {
                if (i == 1) {
                    Intent intent = new Intent("android.intent.action.PICK", MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("video/*");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                    intent.setAction("android.intent.action.OPEN_DOCUMENT");
                    intent.addCategory("android.intent.category.OPENABLE");
                    Anim_MainActivity.this.startActivityForResult(intent, 1);
                }
                if (i == 2) {
                    Anim_MainActivity.this.pickImage();
                }
            }
        });
    }

    public void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.OPEN_DOCUMENT");
        intent.addCategory("android.intent.category.OPENABLE");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);
    }

    @Override
    
    public void onActivityResult(int i, int i2, Intent intent) {
        Uri data;
        super.onActivityResult(i, i2, intent);
        if (Build.VERSION.SDK_INT >= 23) {
            if (i != 1 || !Settings.canDrawOverlays(this)) {
                if (i != 2 || !Settings.canDrawOverlays(this)) {
                    finish();
                } else if (this.f129pm.isIgnoringBatteryOptimizations(this.packageName)) {
                    Anim_Notification anim_Notification = new Anim_Notification(this);
                    myNotification = anim_Notification;
                    anim_Notification.updateStatus("Charging Animation");
                    prepareForService();
                }
            } else if (!this.f129pm.isIgnoringBatteryOptimizations(this.packageName)) {
                batteryOptimizationDialog();
            }
            if (i == 1 && i2 == -1 && (data = intent.getData()) != null) {
                if (Build.VERSION.SDK_INT >= 19) {
                    try {
                        getContentResolver().takePersistableUriPermission(intent.getData(), intent.getFlags() & 3);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }
                Intent intent2 = new Intent(this, Anim_PreviewActivity.class);
                intent2.putExtra("uri", data.toString());
                startActivity(intent2);
            }
        }
    }


    public void loadAMBAd(final Activity activity, final Intent intent) {


        activity.startActivity(intent);

    }


    private void initView() {
        this.layoutSelect = (CardView) findViewById(R.id.layoutSelectAnimation);
        this.layoutTut = (CardView) findViewById(R.id.layoutTutorial);
        this.layoutgallary = (CardView) findViewById(R.id.layoutgallary);
        this.layoutpolicy = (CardView) findViewById(R.id.layoutpolicy);
        this.battrytext = (TextView) findViewById(R.id.battrytext);

        LottieAnimationView aaa = findViewById(R.id.battryimage);
        aaa.setAnimation(R.raw.splesh);


        registerReceiver(this.mBatInfoReceiver, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
    }

    private void overlayPermissionPopup() {
        new MaterialAlertDialogBuilder(this).setTitle((CharSequence) "Permission Required").setCancelable(false).setMessage((CharSequence) getResources().getString(R.string.overlay)).setPositiveButton((CharSequence) "OK", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() { 
            @Override 
            public final void onClick(DialogInterface dialogInterface, int i) {
                Anim_MainActivity.this.lambda$overlayPermissionPopup$0$Anim_MainActivity(dialogInterface, i);
            }
        }).setNegativeButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() { 
            @Override 
            public final void onClick(DialogInterface dialogInterface, int i) {
                Anim_MainActivity.this.lambda$overlayPermissionPopup$1$Anim_MainActivity(dialogInterface, i);
            }
        }).show();
    }

    public void lambda$overlayPermissionPopup$0$Anim_MainActivity(DialogInterface dialogInterface, int i) {
        startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + getPackageName())), 1);
    }

    public void lambda$overlayPermissionPopup$1$Anim_MainActivity(DialogInterface dialogInterface, int i) {
        finish();
    }

    private void batteryOptimizationDialog() {
        new MaterialAlertDialogBuilder(this).setTitle((CharSequence) "Background Running Permission").setMessage((CharSequence) "Allow permission to run app in background").setCancelable(false).setPositiveButton((CharSequence) "OK", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() { 
            @Override 
            public final void onClick(DialogInterface dialogInterface, int i) {
                Anim_MainActivity.this.lambda$batteryOptimizationDialog$2$Anim_MainActivity(dialogInterface, i);
            }
        }).setNegativeButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() { 
            @Override 
            public final void onClick(DialogInterface dialogInterface, int i) {
                Anim_MainActivity.this.lambda$batteryOptimizationDialog$3$Anim_MainActivity(dialogInterface, i);
            }
        }).show();
    }

    public void lambda$batteryOptimizationDialog$2$Anim_MainActivity(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        Intent intent = new Intent();
        intent.setAction("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 2);
    }

    public void lambda$batteryOptimizationDialog$3$Anim_MainActivity(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        finish();
    }

    public void prepareForService() {
        this.serviceIntent = new Intent(this, Anim_Service.class);
        if (!isMyServiceRunning(Anim_Service.class)) {
            if (Build.VERSION.SDK_INT >= 26) {
                ContextCompat.startForegroundService(this, this.serviceIntent);
            } else {
                startService(this.serviceIntent);
            }
        }
    }

    private boolean isMyServiceRunning(Class<?> cls) {
        for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE)) {
            if (cls.getName().equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void initReceiever() {
        receiver = new BroadcastReceiver() { 
            @Override 
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (intent.getAction().equals("android.intent.action.ACTION_POWER_CONNECTED")) {
                    boolean booleanPreference = Anim_SharedPrefsUtils.getBooleanPreference(context, "show", true);
                    if (SystemClock.elapsedRealtime() - Anim_MainActivity.this.mLastClickTime >= 2000 && booleanPreference) {
                        Anim_MainActivity.this.mLastClickTime = SystemClock.elapsedRealtime();
                        Anim_MainActivity.this.isLock = Anim_SharedPrefsUtils.getBooleanPreference(context, "lock", false);
                        Anim_Service.removeScreenFilter();
                        if (!Anim_MainActivity.this.isLock) {
                            Anim_MainActivity.this.startActivity(new Intent(Anim_MainActivity.this.getApplicationContext(), Anim_LockScreenAnimActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        } else if (((KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE)).inKeyguardRestrictedInputMode()) {
                            Anim_MainActivity.this.startActivity(new Intent(Anim_MainActivity.this.getApplicationContext(), Anim_LockScreenAnimActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }
                    }
                } else if (intent.getAction().equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
                    Anim_Service.removeScreenFilter();
                } else if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                    float intExtra = ((float) (intent.getIntExtra("level", -1) * 100)) / ((float) intent.getIntExtra("scale", -1));
                    if (Anim_LockScreenAnimActivity.txtPercentage != null) {
                        TextView textView = Anim_LockScreenAnimActivity.txtPercentage;
                        textView.setText(((int) intExtra) + "%");
                    }
                }
            }
        };
    }

    private void addBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("android.intent.action.BATTERY_LOW");
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        registerReceiver(receiver, intentFilter);
    }

    @Override 
    public void onBackPressed() {
        rateAppDialog(this);
    }

    public void rateAppDialog(Context context) {
       if (this.doubleBackToExitPressedOnce) {
            super.onBackPressed();
        } else {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press Back Again To Exit", Toast.LENGTH_SHORT).show();
        }
    }
}
