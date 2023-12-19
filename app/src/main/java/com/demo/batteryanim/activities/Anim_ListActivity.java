package com.demo.batteryanim.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.batteryanim.AdAdmob;
import com.demo.batteryanim.R;

import com.demo.batteryanim.adapters.Anim_ListAdapter;
import com.demo.batteryanim.model.Anim;
import com.demo.batteryanim.utils.Anim_SharedPrefsUtils;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;


public class Anim_ListActivity extends AppCompatActivity {
    public int anim;
    Anim_ListAdapter animListAdapter;
    ArrayList<Anim> anims = new ArrayList<>();
    public int audio;
    public int closing;
    public int custom;
    public int duration;
    ImageView imgVideo;
    public boolean isLock;
    public boolean isSound;
    RecyclerView rcAnims;
    Toolbar toolbar;
    String uriCustom;

    @Override
    
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.anim_activity_animation_list_activtiy);

        AdAdmob adAdmob = new AdAdmob( this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        adAdmob.FullscreenAd(this);


        initView();
        toolbarSetup();
        loadData();
        loadData();
        this.imgVideo.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Anim_ListActivity anim_ListActivity = Anim_ListActivity.this;
                anim_ListActivity.loadAMBAd(anim_ListActivity);
            }
        });
    }

    public void loadAMBAd(final Activity activity) {

        if(Build.VERSION.SDK_INT >= 33){
            Intent intent = new Intent("android.intent.action.PICK", MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            intent.setType("video/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            intent.setAction("android.intent.action.OPEN_DOCUMENT");
            intent.addCategory("android.intent.category.OPENABLE");
            Anim_ListActivity.this.startActivityForResult(intent, 1);
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
                    Anim_ListActivity.this.startActivityForResult(intent, 1);
                }
                if (i == 2) {
                    Anim_ListActivity.this.pickImage();
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

    private void initView() {
        this.rcAnims = (RecyclerView) findViewById(R.id.rcAnimations);
        this.imgVideo = (ImageView) findViewById(R.id.imgVideo);
        this.toolbar = (Toolbar) findViewById(R.id.layoutActionBar);
    }

    private void loadData() {
        initData();
        this.anims.clear();
        int i = this.custom;
        if (i != -1) {
            this.anims.add(new Anim(i, Uri.parse(this.uriCustom)));
        }
        this.anims.add(new Anim(R.raw.anim22, R.raw.music3_new));
        this.anims.add(new Anim(R.raw.anim3, R.raw.music3_new));
        this.anims.add(new Anim(R.raw.anim10, R.raw.music1));
        this.anims.add(new Anim(R.raw.anim14, R.raw.music2));
        this.anims.add(new Anim(R.raw.anim11, R.raw.music4));
        this.anims.add(new Anim(R.raw.anim23, R.raw.music5_new));
        this.anims.add(new Anim(R.raw.anim_17_new, R.raw.music6));
        this.anims.add(new Anim(R.raw.anim18, R.raw.music5_new));
        this.anims.add(new Anim(R.raw.anim_16_new, R.raw.music2));
        this.anims.add(new Anim(R.raw.anim19, R.raw.music6));
        this.anims.add(new Anim(R.raw.anim12, R.raw.music1));
        this.anims.add(new Anim(R.raw.anim20, R.raw.music5_new));
        this.anims.add(new Anim(R.raw.anim15, R.raw.music6));
        this.anims.add(new Anim(R.raw.anim21, R.raw.music2));
        this.anims.add(new Anim(R.raw.splesh, R.raw.music2));



        this.animListAdapter = new Anim_ListAdapter(this, this.anims);
        this.rcAnims.setLayoutManager(new GridLayoutManager(this, 2));
        this.rcAnims.setAdapter(this.animListAdapter);
    }

    private void toolbarSetup() {
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Anim_ListActivity.this.onBackPressed();
            }
        });
    }

    @Override 
    public void onResume() {
        super.onResume();
        loadData();
        Anim_ListAdapter anim_ListAdapter = this.animListAdapter;
        if (anim_ListAdapter != null) {
            anim_ListAdapter.notifyDataSetChanged();
        }
    }

    private void initData() {
        this.anim = Anim_SharedPrefsUtils.getIntegerPreference(this, "anim_id", R.raw.anim3);
        this.audio = Anim_SharedPrefsUtils.getIntegerPreference(this, "audio_id", R.raw.music3_new);
        this.isSound = Anim_SharedPrefsUtils.getBooleanPreference(this, "sound", true);
        this.isLock = Anim_SharedPrefsUtils.getBooleanPreference(this, "lock", false);
        this.duration = Anim_SharedPrefsUtils.getIntegerPreference(this, "duration", 5);
        this.closing = Anim_SharedPrefsUtils.getIntegerPreference(this, "closing", 0);
        this.custom = Anim_SharedPrefsUtils.getIntegerPreference(this, "custom", -1);
        this.uriCustom = Anim_SharedPrefsUtils.getStringPreference(this, "custom_uri");
    }
}
