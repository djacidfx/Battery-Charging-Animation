package com.demo.batteryanim.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.demo.batteryanim.R;

import com.demo.batteryanim.utils.Anim_SharedPrefsUtils;



public class Anim_Tutorial3Activity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtNext;


    @Override
    
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.anim_activity_tutorial3);
        initView();
        toolbarSetup();

        this.txtNext.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                try {
                    Anim_Tutorial3Activity anim_Tutorial3Activity = Anim_Tutorial3Activity.this;
                    anim_Tutorial3Activity.loadAMBAd(anim_Tutorial3Activity);
                } catch (Exception unused) {
                }
            }
        });
    }

    public void loadAMBAd(final Activity activity) {


        Anim_SharedPrefsUtils.setBooleanPreference(getApplicationContext(), "first", false);
        startActivity(new Intent(getApplicationContext(), Anim_MainActivity.class).addFlags(335577088));

    }

    private void initView() {
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
                Anim_Tutorial3Activity.this.onBackPressed();
            }
        });
    }
}
