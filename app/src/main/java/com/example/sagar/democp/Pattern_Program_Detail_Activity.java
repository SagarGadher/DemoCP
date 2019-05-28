package com.example.sagar.democp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Pattern_Program_Detail_Activity extends AppCompatActivity {
    private Toolbar toolbar;
    WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_pattern__program__detail);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
setTitle("Pattern Program");
        Intent intent = getIntent();
        int Page_Position = intent.getIntExtra("List_Item_Position",0);

        wv = findViewById(R.id.wv_1);
        //LOAD WEBVIEW URL AND MAINTAIN THE LAYOUT OF WEBVIEW
        wv.loadUrl("file:///android_asset/PatternProgram/" + Page_Position + ".html");

        //when page not found it will redirect to introduction page
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                wv.loadUrl("file:///android_asset/PatternProgram/0.html");
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
