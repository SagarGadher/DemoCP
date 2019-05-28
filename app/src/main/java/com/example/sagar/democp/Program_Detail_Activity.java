package com.example.sagar.democp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class Program_Detail_Activity extends AppCompatActivity {
    private Toolbar toolbar;
    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_program_detail_activity);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
setTitle("Program");
        Intent intent = getIntent();
        String Page_Name = intent.getStringExtra("List_Item_Name");

        wv = findViewById(R.id.wv_1);
        //LOAD WEBVIEW URL AND MAINTAIN THE LAYOUT OF WEBVIEW
        wv.loadUrl("file:///android_asset/Program/" + Page_Name + ".html");

        //when page not found it will redirect to introduction page
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                wv.loadUrl("file:///android_asset/Program/World Program in C.html");
                super.onReceivedError(view, request, error);
            }
        });

        WebSettings wvs = wv.getSettings();
        wvs.setDomStorageEnabled(true);
        wvs.setBuiltInZoomControls(true);
        wvs.setDisplayZoomControls(false);
        wvs.setSupportZoom(true);
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_no_animation, R.anim.slide_out);
    }

}
