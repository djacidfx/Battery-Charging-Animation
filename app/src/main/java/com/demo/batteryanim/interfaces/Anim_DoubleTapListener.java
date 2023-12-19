package com.demo.batteryanim.interfaces;

import android.content.Context;
import android.view.View;


public class Anim_DoubleTapListener implements View.OnClickListener {
    private Anim_DoubleTapCallback listener;
    public int counter = 0;
    public boolean isRunning = false;
    public int resetInTime = 500;

    public Anim_DoubleTapListener(Context context) {
        this.listener = (Anim_DoubleTapCallback) context;
    }

    @Override 
    public void onClick(View view) {
        this.listener.onSingleClick(view);
        if (this.isRunning && this.counter == 1) {
            this.listener.onDoubleClick(view);
        }
        this.counter++;
        if (!this.isRunning) {
            this.isRunning = true;
            new Thread(new Runnable() { 
                @Override 
                public void run() {
                    try {
                        Thread.sleep((long) Anim_DoubleTapListener.this.resetInTime);
                        Anim_DoubleTapListener.this.isRunning = false;
                        Anim_DoubleTapListener.this.counter = 0;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
