package com.example.android_wei22;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Set;

import static android.Manifest.permission.BLUETOOTH;

//裡面觸發外面
//外面觸發立面
public class MainActivity extends AppCompatActivity {
    private WebView webView;
    String[ ] PERMISIONS={
        android.Manifest.permission.CAMERA,
        BLUETOOTH
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!hasPermissions(this,PERMISIONS)) {
            ActivityCompat.requestPermissions(this,
                    PERMISIONS, 321);
        } else{
            // Permission has already been granted
            initWebview();
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        initWebview();
    }

    private void initWebview(){
        webView=findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        WebSettings Setting=webView.getSettings();
        Setting.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new MyJavascript2(),"brad");
        webView.loadUrl("file:///android_asset/brad.html");


    }
    public class MyJavascript2{
        //寫這行代表這個方法是"可以"給 Javascript 用的方法
        //主要透過 addJavascriptInterface 方法
        @JavascriptInterface
        public void fromJS(){
            gotoScan();
        }
    }

    private void gotoScan() {
        Log.v("wei","gotoScan()");
        Intent intent=new Intent(this,ScanActivity.class);
        startActivityForResult(intent,321);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK&&requestCode==321){
            webView.loadUrl(String.format("javascript:showCode('%s')",data.getStringExtra("code")));
        }
    }
}
