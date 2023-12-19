package com.demo.batteryanim.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.demo.batteryanim.AdAdmob;
import com.demo.batteryanim.R;


public class Anim_OtherPermissionActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtNext;
    TextView txtSet1;
    TextView txtSet2;


    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.anim_activity_other_permission);

        AdAdmob adAdmob = new AdAdmob( this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);


        initView();
        toolbarSetup();
        this.txtNext.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Anim_OtherPermissionActivity.this.startActivity(new Intent(Anim_OtherPermissionActivity.this.getApplicationContext(), Anim_Tutorial2Activity.class));
            }
        });
        this.txtSet1.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.fromParts("package", Anim_OtherPermissionActivity.this.getPackageName(), null));
                Anim_OtherPermissionActivity.this.startActivityForResult(intent, 1000);
            }
        });
        this.txtSet2.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.fromParts("package", Anim_OtherPermissionActivity.this.getPackageName(), null));
                Anim_OtherPermissionActivity.this.startActivityForResult(intent, 1000);
            }
        });
    }

    private void initView() {
        this.txtSet1 = (TextView) findViewById(R.id.txtSet);
        this.txtSet2 = (TextView) findViewById(R.id.txtSet2);
        this.txtNext = (TextView) findViewById(R.id.txtNext);
        this.toolbar = (Toolbar) findViewById(R.id.layoutActionBar);
    }

    private void toolbarSetup() {
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Anim_OtherPermissionActivity.this.onBackPressed();
            }
        });
    }
}
