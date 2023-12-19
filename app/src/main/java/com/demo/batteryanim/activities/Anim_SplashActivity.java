package com.demo.batteryanim.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.demo.batteryanim.R;


public class Anim_SplashActivity extends AppCompatActivity {
    @Override
    
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.anim_activity_splash);
        LottieAnimationView aaa = findViewById(R.id.battryimage);
        aaa.setAnimation(R.raw.splesh);
        getNetworkInfo();
    }

    public void getNetworkInfo() {
        new Handler().postDelayed(new Runnable() { 
            @Override 
            public final void run() {

                nextsxreen();


            }
        }, 2000);

    }


    public void nextsxreen() {
        if (getIntent().getStringExtra("isLock") != null) {
            startActivity(new Intent(getApplicationContext(), Anim_LockScreenAnimActivity.class));
            finish();
            return;
        }
        startActivity(new Intent(getApplicationContext(), Anim_MainActivity.class));
        finish();
    }
}
