package com.demo.batteryanim.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.demo.batteryanim.R;
import com.demo.batteryanim.interfaces.Anim_DoubleTapCallback;
import com.demo.batteryanim.interfaces.Anim_DoubleTapListener;
import com.demo.batteryanim.utils.Anim_SharedPrefsUtils;
import java.io.IOException;


public class Anim_LockScreenAnimActivity extends AppCompatActivity implements Anim_DoubleTapCallback {
    public static TextView txtPercentage;
    public int anim;
    public int audio;
    BroadcastReceiver broadcastReceiver;
    public int closing;
    public int custom;
    public int duration;
    ImageView imgClick;
    ImageView imgPreview;
    public boolean isLock;
    private boolean isPer;
    public boolean isSound;
    LinearLayout layoutBattery;
    RelativeLayout layoutVideo;
    LottieAnimationView lottieAnimationView;
    public MediaPlayer mediaPlayer;
    String uriCustom;
    VideoView videoView;

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.color_toolbar));
        }
        getWindow().getDecorView().setSystemUiVisibility(5890);
        getWindow().addFlags(2621440);
        setContentView(R.layout.anim_activity_lock_screen_anim);
        initView();
        initData();
        showAnim();
        this.broadcastReceiver = new BroadcastReceiver() { 
            @Override 
            public void onReceive(Context context, Intent intent) {
                if (Anim_LockScreenAnimActivity.this.mediaPlayer != null) {
                    Anim_LockScreenAnimActivity.this.mediaPlayer.release();
                }
                Anim_LockScreenAnimActivity.this.finish();
            }
        };
        addBroadcastReceiver();
    }

    private void addBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        registerReceiver(this.broadcastReceiver, intentFilter);
    }

    public void setMediaPlayer(Context context) {
        MediaPlayer create = MediaPlayer.create(context, this.audio);
        this.mediaPlayer = create;
        create.setLooping(true);
        this.mediaPlayer.start();
    }

    private void showAnim() {
        if (this.anim == R.raw.anim23) {
            this.lottieAnimationView.setPadding(0, 0, 0, 0);
            this.lottieAnimationView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        if (this.isPer) {
            this.layoutBattery.setVisibility(View.VISIBLE);
        } else {
            this.layoutBattery.setVisibility(View.GONE);
        }
        TextView textView = txtPercentage;
        textView.setText(Anim_MainActivity.getBatteryPercentage(this) + "%");
        if (this.isSound && this.custom == -1) {
            setMediaPlayer(this);
        }
        int i = this.custom;
        if (i == -1) {
            this.lottieAnimationView.setAnimation(this.anim);
            this.layoutVideo.setVisibility(View.GONE);
            this.imgPreview.setVisibility(View.GONE);
        } else if (i == 0) {
            this.layoutVideo.setVisibility(View.VISIBLE);
            Log.d("uriCustom", this.uriCustom + "abc");
            this.videoView.setVideoURI(Uri.parse(this.uriCustom));
            this.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { 
                @Override 
                public void onPrepared(MediaPlayer mediaPlayer) {
                    Anim_LockScreenAnimActivity.this.videoView.start();
                    if (!Anim_LockScreenAnimActivity.this.isSound) {
                        mediaPlayer.setVolume(0.0f, 0.0f);
                    }
                    if (Anim_LockScreenAnimActivity.this.duration == -1) {
                        mediaPlayer.setLooping(true);
                    }
                }
            });
        } else {
            Bitmap bitmap = null;
            try {
                bitmap = Anim_PreviewActivity.rotateImageIfRequired(this, Anim_PreviewActivity.uriToBitmap(this, Uri.parse(this.uriCustom)), Uri.parse(this.uriCustom));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.imgPreview.setImageBitmap(bitmap);
        }
        if (this.duration != -1) {
            new Handler().postDelayed(new Runnable() { 
                @Override 
                public void run() {
                    if (Anim_LockScreenAnimActivity.this.mediaPlayer != null) {
                        Anim_LockScreenAnimActivity.this.mediaPlayer.release();
                    }
                    Anim_LockScreenAnimActivity.this.finish();
                }
            }, (long) (this.duration * 1000));
        }
        if (this.closing == 1) {
            this.lottieAnimationView.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view) {
                    Log.d("clickAnim", "single");
                    if (Anim_LockScreenAnimActivity.this.mediaPlayer != null) {
                        Anim_LockScreenAnimActivity.this.mediaPlayer.release();
                    }
                    Anim_LockScreenAnimActivity.this.finish();
                }
            });
        } else {
            this.lottieAnimationView.setOnClickListener(new Anim_DoubleTapListener(this));
        }
    }

    private void initView() {
        this.videoView = (VideoView) findViewById(R.id.videoView);
        this.imgPreview = (ImageView) findViewById(R.id.imgAnim);
        this.lottieAnimationView = (LottieAnimationView) findViewById(R.id.animationView);
        txtPercentage = (TextView) findViewById(R.id.txtPercentage);
        this.layoutBattery = (LinearLayout) findViewById(R.id.layoutBattery);
        this.imgClick = (ImageView) findViewById(R.id.imgClick);
        this.layoutVideo = (RelativeLayout) findViewById(R.id.layoutVideo);
    }

    private void initData() {
        this.anim = Anim_SharedPrefsUtils.getIntegerPreference(this, "anim_id", R.raw.anim3);
        this.audio = Anim_SharedPrefsUtils.getIntegerPreference(this, "audio_id", R.raw.music3_new);
        this.isSound = Anim_SharedPrefsUtils.getBooleanPreference(this, "sound", true);
        this.isLock = Anim_SharedPrefsUtils.getBooleanPreference(this, "lock", false);
        this.isPer = Anim_SharedPrefsUtils.getBooleanPreference(this, "per", true);
        this.duration = Anim_SharedPrefsUtils.getIntegerPreference(this, "duration", 5);
        this.closing = Anim_SharedPrefsUtils.getIntegerPreference(this, "closing", 0);
        this.custom = Anim_SharedPrefsUtils.getIntegerPreference(this, "custom", -1);
        this.uriCustom = Anim_SharedPrefsUtils.getStringPreference(this, "custom_uri");
    }

    @Override 
    public void onDoubleClick(View view) {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        finish();
        Log.d("clickAnim", "single");
    }

    @Override 
    public void onSingleClick(View view) {
        this.imgClick.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() { 
            @Override 
            public void run() {
                Anim_LockScreenAnimActivity.this.imgClick.setVisibility(View.GONE);
            }
        }, 1000);
    }

    @Override 
    public void onDestroy() {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}
