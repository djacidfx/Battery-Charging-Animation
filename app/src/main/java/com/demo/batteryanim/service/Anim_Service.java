package com.demo.batteryanim.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.demo.batteryanim.R;
import com.demo.batteryanim.activities.Anim_MainActivity;
import com.demo.batteryanim.interfaces.Anim_Callback;
import com.demo.batteryanim.interfaces.Anim_DoubleTapCallback;
import com.demo.batteryanim.interfaces.Anim_DoubleTapListener;
import com.demo.batteryanim.utils.Anim_SharedPrefsUtils;
import com.demo.batteryanim.view.Anim_FullScreenVideoView;



public class Anim_Service extends Service implements Anim_DoubleTapCallback {
    public static int anim;
    public static int audio;
    public static Anim_Callback callback;
    public static int closing;
    public static int custom;
    public static int duration;
    public static boolean isLock;
    public static boolean isPer;
    public static boolean isSound;
    static WindowManager.LayoutParams layoutParams;
    public static MediaPlayer mediaPlayer;
    static RelativeLayout relativeLayout;
    public static TextView txtPercentage;
    static WindowManager windowManager;
    ImageView imgPreview;
    LottieAnimationView lottieAnimationView;
    String uriCustom;
    Anim_FullScreenVideoView videoView;

    @Override 
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override 
    public void onSingleClick(View view) {
    }

    @Override 
    public void onCreate() {
        super.onCreate();
        createScreenFilter();
        addBroadcastReceiver();
        initData();
        callback = new Anim_Callback() { 
            @Override 
            public void callback() {
                Anim_Service.this.createScreenFilter();
            }
        };
    }

    private void addBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("android.intent.action.BATTERY_LOW");
        intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        registerReceiver(Anim_MainActivity.receiver, intentFilter);
    }

    @Override 
    public int onStartCommand(Intent intent, int i, int i2) {
        addBroadcastReceiver();
        initData();
        if (Anim_MainActivity.myNotification != null) {
            startForeground(1, Anim_MainActivity.myNotification.builder.build());
        }
        return START_STICKY;
    }

    private void initData() {
        anim = Anim_SharedPrefsUtils.getIntegerPreference(this, "anim_id", R.raw.anim3);
        audio = Anim_SharedPrefsUtils.getIntegerPreference(this, "audio_id", R.raw.music3_new);
        isSound = Anim_SharedPrefsUtils.getBooleanPreference(this, "sound", true);
        isLock = Anim_SharedPrefsUtils.getBooleanPreference(this, "lock", false);
        duration = Anim_SharedPrefsUtils.getIntegerPreference(this, "duration", 5);
        closing = Anim_SharedPrefsUtils.getIntegerPreference(this, "closing", 0);
        custom = Anim_SharedPrefsUtils.getIntegerPreference(this, "custom", -1);
        this.uriCustom = Anim_SharedPrefsUtils.getStringPreference(this, "custom_uri");
        isPer = Anim_SharedPrefsUtils.getBooleanPreference(this, "per", true);
    }

    @Override 
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(Anim_MainActivity.receiver);
    }

    public void createScreenFilter() {
        initData();
        WindowManager windowManager2 = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        windowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        }
        layoutParams.format = -3;
        if (isLock) {
            if (duration == -1) {
                layoutParams.flags = 6817152;
            } else {
                layoutParams.flags = 6817152;
            }
        } else if (duration == -1) {
            layoutParams.flags = 1408;
        } else {
            layoutParams.flags = 1280;
        }
        getNavigationBarHeight(getApplicationContext());
        windowManager2.getDefaultDisplay().getRealMetrics(new DisplayMetrics());
        layoutParams.width = -1;
        layoutParams.height = -1;
        layoutParams.format = -3;
        RelativeLayout relativeLayout2 = (RelativeLayout) LayoutInflater.from(getApplication()).inflate(R.layout.anim_screen_filter, (ViewGroup) null);
        relativeLayout = relativeLayout2;
        LinearLayout linearLayout = (LinearLayout) relativeLayout2.findViewById(R.id.layoutBattery);
        this.videoView = (Anim_FullScreenVideoView) relativeLayout.findViewById(R.id.videoView);
        this.imgPreview = (ImageView) relativeLayout.findViewById(R.id.imgAnim);
        this.lottieAnimationView = (LottieAnimationView) relativeLayout.findViewById(R.id.animationView);
        TextView textView = (TextView) relativeLayout.findViewById(R.id.txtPercentage);
        txtPercentage = textView;
        textView.setText(getBatteryPercentage(this) + "%");
        if (isPer) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.GONE);
        }
        int i = custom;
        if (i == -1) {
            this.lottieAnimationView.setAnimation(anim);
            this.videoView.setVisibility(View.GONE);
            this.imgPreview.setVisibility(View.GONE);
        } else if (i == 0) {
            this.videoView.setVisibility(View.VISIBLE);
            this.videoView.setVideoURI(Uri.parse(this.uriCustom));
            this.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { 
                @Override 
                public void onPrepared(MediaPlayer mediaPlayer2) {
                    Anim_Service.this.videoView.start();
                    if (!Anim_Service.isSound) {
                        mediaPlayer2.setVolume(0.0f, 0.0f);
                    }
                    if (Anim_Service.duration == -1) {
                        mediaPlayer2.setLooping(true);
                    }
                }
            });
        } else {
            this.imgPreview.setImageURI(Uri.parse(this.uriCustom));
        }
        if (duration != -1) {
            new Handler().postDelayed(new Runnable() { 
                @Override 
                public void run() {
                    Anim_Service.removeScreenFilter();
                }
            }, (long) (duration * 1000));
        }
        if (closing == 1) {
            this.lottieAnimationView.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view) {
                    Anim_Service.removeScreenFilter();
                }
            });
        } else {
            this.lottieAnimationView.setOnClickListener(new Anim_DoubleTapListener(this));
        }
    }

    public static int getBatteryPercentage(Context context) {
        Intent registerReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        return (int) ((((float) registerReceiver.getIntExtra("level", -1)) / ((float) registerReceiver.getIntExtra("scale", -1))) * 100.0f);
    }

    public static void setMediaPlayer(Context context) {
        MediaPlayer create = MediaPlayer.create(context, audio);
        mediaPlayer = create;
        create.setLooping(true);
        mediaPlayer.start();
    }

    public static int getNavigationBarHeight(Context context) {
        if (context.getResources().getIdentifier("config_showNavigationBar", "bool", "android") == 0) {
            return 0;
        }
        return context.getResources().getDimensionPixelSize(context.getResources().getIdentifier("navigation_bar_height", "dimen", "android"));
    }

    public static void activateScreenFilter(Context context) {
        callback.callback();
        windowManager.addView(relativeLayout, layoutParams);
        if (isSound && custom == -1) {
            setMediaPlayer(context);
        }
        Log.d("filterActivated", "yus");
    }

    public static void removeScreenFilter() {
        RelativeLayout relativeLayout2 = relativeLayout;
        if (relativeLayout2 != null && relativeLayout2.getParent() != null) {
            MediaPlayer mediaPlayer2 = mediaPlayer;
            if (mediaPlayer2 != null) {
                mediaPlayer2.stop();
            }
            windowManager.removeViewImmediate(relativeLayout);
        }
    }

    @Override 
    public void onDoubleClick(View view) {
        removeScreenFilter();
    }
}
