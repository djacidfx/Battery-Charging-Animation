package com.demo.batteryanim.interfaces;

import android.os.SystemClock;
import android.view.View;


public abstract class Anim_OnSingleClickListener implements View.OnClickListener {
    private static final long MIN_CLICK_INTERVAL = 1000;
    private long mLastClickTime;

    public abstract void onSingleClick(View view);

    @Override 
    public final void onClick(View view) {
        long uptimeMillis = SystemClock.uptimeMillis();
        this.mLastClickTime = uptimeMillis;
        if (uptimeMillis - uptimeMillis > 1000) {
            onSingleClick(view);
        }
    }
}
