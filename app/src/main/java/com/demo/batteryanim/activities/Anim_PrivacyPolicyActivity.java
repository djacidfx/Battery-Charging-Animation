package com.demo.batteryanim.activities;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.demo.batteryanim.AdAdmob;
import com.demo.batteryanim.R;


public class Anim_PrivacyPolicyActivity extends AppCompatActivity {
    Toolbar toolbar;
    private WebSettings webSettings;
    WebView webview;

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.anim_activity_privacy_policy);

        AdAdmob adAdmob = new AdAdmob( this);
        adAdmob.FullscreenAd(this);

        this.toolbar = (Toolbar) findViewById(R.id.layoutActionBar);
        WebView webView = (WebView) findViewById(R.id.web_view);
        this.webview = webView;
        this.webSettings = webView.getSettings();
        toolbarSetup();
        WebSettings settings = this.webview.getSettings();
        this.webSettings = settings;
        settings.setJavaScriptEnabled(true);
        this.webview.loadUrl("");
    }

    private void toolbarSetup() {
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                Anim_PrivacyPolicyActivity.this.lambda$toolbarSetup$0$Anim_PrivacyPolicyActivity(view);
            }
        });
    }

    public void lambda$toolbarSetup$0$Anim_PrivacyPolicyActivity(View view) {
        onBackPressed();
    }
}
