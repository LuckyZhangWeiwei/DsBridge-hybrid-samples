package com.example.dsbridage_learning;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;

import utils.VideoApi;
import wendu.dsbridge.DWebView;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video);

        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        DWebView webView = findViewById(R.id.webView);
        webView.addJavascriptObject(new VideoApi(this), "");
        webView.loadUrl("file:///android_asset/test2.html");
    }

    @Override
    public void onBackPressed() {
        if (!VideoApi.onBackPressed()) {
            super.onBackPressed();
        }

    }
}