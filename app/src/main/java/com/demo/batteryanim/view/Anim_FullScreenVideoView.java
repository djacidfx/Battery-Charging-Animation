package com.demo.batteryanim.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;


public class Anim_FullScreenVideoView extends VideoView {
    public Anim_FullScreenVideoView(Context context) {
        super(context);
    }

    public Anim_FullScreenVideoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public Anim_FullScreenVideoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override 
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(i, i2);
    }
}
