package com.demo.batteryanim.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.demo.batteryanim.AdAdmob;
import com.demo.batteryanim.R;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Locale;


public class Anim_Tutorial1Activity extends AppCompatActivity {
    PowerManager f130pm;
    String packageName;
    String showBack;
    ToggleButton toggleBattery;
    ToggleButton toggleOverlay;
    Toolbar toolbar;
    TextView txtNext;

    @Override
    
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.anim_activity_tutorial1);


        AdAdmob adAdmob = new AdAdmob( this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);


        this.packageName = getPackageName();
        this.f130pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.showBack = getIntent().getStringExtra("showBack");
        initView();
        if (this.showBack != null) {
            toolbarSetup();
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(this)) {
                this.toggleOverlay.setChecked(true);
                this.toggleOverlay.setEnabled(false);
            }
            if (this.f130pm.isIgnoringBatteryOptimizations(this.packageName)) {
                this.toggleBattery.setChecked(true);
                this.toggleBattery.setEnabled(false);
            }
            this.toggleOverlay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { 
                @Override 
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    if (z && !Settings.canDrawOverlays(Anim_Tutorial1Activity.this)) {
                        Anim_Tutorial1Activity.this.overlayPermissionPopup();
                    }
                }
            });
            this.toggleBattery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { 
                @Override 
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    if (z && !Anim_Tutorial1Activity.this.f130pm.isIgnoringBatteryOptimizations(Anim_Tutorial1Activity.this.packageName)) {
                        Anim_Tutorial1Activity.this.batteryOptimizationDialog();
                    }
                }
            });
        } else {
            this.toggleBattery.setChecked(true);
            this.toggleOverlay.setChecked(true);
            this.toggleOverlay.setEnabled(false);
            this.toggleBattery.setEnabled(false);
        }
        this.txtNext.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (!"xiaomi".equals(Build.MANUFACTURER.toLowerCase(Locale.ROOT))) {
                    Intent intent = new Intent(Anim_Tutorial1Activity.this, Anim_Tutorial2Activity.class);
                    try {
                        Anim_Tutorial1Activity anim_Tutorial1Activity = Anim_Tutorial1Activity.this;
                        anim_Tutorial1Activity.loadAMBAd(anim_Tutorial1Activity, intent);
                    } catch (Exception unused) {
                        Anim_Tutorial1Activity.this.startActivity(intent);
                    }
                } else if (Build.VERSION.SDK_INT <= 28) {
                    Intent intent2 = new Intent(Anim_Tutorial1Activity.this, Anim_OtherPermissionActivity.class);
                    try {
                        Anim_Tutorial1Activity anim_Tutorial1Activity2 = Anim_Tutorial1Activity.this;
                        anim_Tutorial1Activity2.loadAMBAd(anim_Tutorial1Activity2, intent2);
                    } catch (Exception unused2) {
                        Anim_Tutorial1Activity.this.startActivity(intent2);
                    }
                } else {
                    Intent intent3 = new Intent(Anim_Tutorial1Activity.this, Anim_Tutorial2Activity.class);
                    try {
                        Anim_Tutorial1Activity anim_Tutorial1Activity3 = Anim_Tutorial1Activity.this;
                        anim_Tutorial1Activity3.loadAMBAd(anim_Tutorial1Activity3, intent3);
                    } catch (Exception unused3) {
                        Anim_Tutorial1Activity.this.startActivity(intent3);
                    }
                }
            }
        });
    }

    public void loadAMBAd(final Activity activity, final Intent intent) {

        activity.startActivity(intent);

    }





    private void toolbarSetup() {
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Anim_Tutorial1Activity.this.onBackPressed();
            }
        });
    }

    public void overlayPermissionPopup() {
        new MaterialAlertDialogBuilder(this).setTitle((CharSequence) "Permission Required").setCancelable(false).setMessage((CharSequence) getResources().getString(R.string.overlay)).setPositiveButton((CharSequence) "OK", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() { 
            @Override 
            public final void onClick(DialogInterface dialogInterface, int i) {
                Anim_Tutorial1Activity.this.lambda$overlayPermissionPopup$0$Anim_Tutorial1Activity(dialogInterface, i);
            }
        }).setNegativeButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() { 
            @Override 
            public final void onClick(DialogInterface dialogInterface, int i) {
                Anim_Tutorial1Activity.this.lambda$overlayPermissionPopup$1$Anim_Tutorial1Activity(dialogInterface, i);
            }
        }).show();
    }

    public void lambda$overlayPermissionPopup$0$Anim_Tutorial1Activity(DialogInterface dialogInterface, int i) {
        startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + getPackageName())), 1);
    }

    public void lambda$overlayPermissionPopup$1$Anim_Tutorial1Activity(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        this.toggleOverlay.setChecked(false);
    }

    public void batteryOptimizationDialog() {
        new MaterialAlertDialogBuilder(this).setTitle((CharSequence) "Battery Optimization").setMessage((CharSequence) "Allow permission to optimize battery").setCancelable(false).setPositiveButton((CharSequence) "OK", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() { 
            @Override 
            public final void onClick(DialogInterface dialogInterface, int i) {
                Anim_Tutorial1Activity.this.lambda$batteryOptimizationDialog$2$Anim_Tutorial1Activity(dialogInterface, i);
            }
        }).setNegativeButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() { 
            @Override 
            public final void onClick(DialogInterface dialogInterface, int i) {
                Anim_Tutorial1Activity.this.lambda$batteryOptimizationDialog$3$Anim_Tutorial1Activity(dialogInterface, i);
            }
        }).show();
    }

    public void lambda$batteryOptimizationDialog$2$Anim_Tutorial1Activity(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        Intent intent = new Intent();
        intent.setAction("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 2);
    }

    public void lambda$batteryOptimizationDialog$3$Anim_Tutorial1Activity(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        this.toggleBattery.setChecked(false);
    }

    private void initView() {
        this.toggleOverlay = (ToggleButton) findViewById(R.id.toggleDrawing);
        this.toggleBattery = (ToggleButton) findViewById(R.id.toggleBattery);
        this.txtNext = (TextView) findViewById(R.id.txtNext);
        this.toolbar = (Toolbar) findViewById(R.id.layoutActionBar);
    }

    @Override
    
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (Build.VERSION.SDK_INT >= 23) {
            if (i == 1) {
                if (Settings.canDrawOverlays(this)) {
                    this.toggleOverlay.setEnabled(false);
                } else {
                    this.toggleOverlay.setChecked(false);
                }
            }
            if (i == 2) {
                if (this.f130pm.isIgnoringBatteryOptimizations(this.packageName)) {
                    this.toggleBattery.setEnabled(false);
                } else {
                    this.toggleBattery.setChecked(false);
                }
            }
        }
    }
}
