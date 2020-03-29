package com.example.android_wei22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mScannerView = new ZXingScannerView(this);
        // Programmatically initialize the scanner view
        //setContentView(mScannerView);
        // Set the scanner view as the content view
        setContentView(R.layout.activity_scan);
        mScannerView=findViewById(R.id.Scan);


    }
    @Override
    public void onResume () {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause () {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
//        Log.v("wei", rawResult.getText()); // Prints scan results
//
//        Log.v("wei", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        setResult(RESULT_OK,new Intent().putExtra("code",rawResult.toString()));
        finish();
    }
}
