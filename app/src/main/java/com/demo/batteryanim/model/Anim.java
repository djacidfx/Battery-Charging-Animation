package com.demo.batteryanim.model;

import android.net.Uri;


public class Anim {
    public int audioRes;
    public int custom;
    public int jsonRes;
    Uri uri;

    public Anim(int i, Uri uri) {
        this.custom = -1;
        this.custom = i;
        this.uri = uri;
    }

    public Anim( int i2, int i3) {
        this.custom = -1;
        this.jsonRes = i2;
        this.audioRes = i3;
    }



    public int getJsonRes() {
        return this.jsonRes;
    }


    public void setJsonRes(int i) {
        this.jsonRes = i;
    }

    public int getAudioRes() {
        return this.audioRes;
    }

    public int getCustom() {
        return this.custom;
    }

    public Uri getUri() {
        return this.uri;
    }
}
