package com.demo.batteryanim.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.demo.batteryanim.R;


public class Anim_Tutorial2Activity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtNext;


    @Override
    
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.anim_activity_tutorial2);
        initView();
        toolbarSetup();
        this.txtNext.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent(Anim_Tutorial2Activity.this, Anim_Tutorial3Activity.class);
                try {
                    Anim_Tutorial2Activity anim_Tutorial2Activity = Anim_Tutorial2Activity.this;
                    anim_Tutorial2Activity.loadAMBAd(anim_Tutorial2Activity, intent);
                } catch (Exception unused) {
                    Anim_Tutorial2Activity.this.startActivity(intent);
                }
            }
        });
    }

    public void loadAMBAd(final Activity activity, final Intent intent) {

        activity.startActivity(intent);

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
                Anim_Tutorial2Activity.this.onBackPressed();
            }
        });
    }
}
