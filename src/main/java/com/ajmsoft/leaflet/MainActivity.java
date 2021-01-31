package com.ajmsoft.leaflet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    WebView webview;
    static TextView textView;
    public String fileName="map.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webview = (WebView) findViewById(R.id.webpage);
        textView = (TextView) findViewById(R.id.city);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setGeolocationEnabled(true);

        webview.addJavascriptInterface(new WebAppInterface(this),"Android");

        webview.loadUrl("file:///android_asset/"+fileName);

    }



}