package com.example.dsbridage_learning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import utils.JsInterfaceUtil;
import wendu.dsbridge.DWebView;
import wendu.dsbridge.OnReturnValue;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        DWebView webView = findViewById(R.id.webView);
        webView.addJavascriptObject(new JsInterfaceUtil(this), "");
        webView.loadUrl("file:///android_asset/test.html");

        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.callHandler("androidMethodSync",
                        new Object[]{"android sync"},
                        new OnReturnValue<String>() {
                            @Override
                            public void onValue(String retValue) {
                                Toast.makeText(MainActivity.this, retValue, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
        });

        Button btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.callHandler("androidMethodAsync",
                        new OnReturnValue<String>() {
                            @Override
                            public void onValue(String retValue) {
                                Toast.makeText(MainActivity.this, retValue, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        Button btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent I = new Intent(MainActivity.this, VideoActivity.class);
                startActivity(I);
            }
        });
    }
}