package com.example.lab1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    protected static final int PERMISSION_REQUEST_CODE = 228;
    protected TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_view);
        showInfo();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                showInfo();
            }
        }
    }

    protected void showInfo()
    {
        String deviceIMEI = getDeviceIMEI();
        String text = "VersionName: " + BuildConfig.VERSION_NAME + '\n' +
                "VersionCode: " + BuildConfig.VERSION_CODE + '\n';
        if(!deviceIMEI.isEmpty())
            text += "IMEI: " + deviceIMEI;
        textView.setText(text);
    }

    protected String getDeviceIMEI() {
        if (!isPermissionGranted(Manifest.permission.READ_PHONE_STATE)){
            requestPermission(Manifest.permission.READ_PHONE_STATE, PERMISSION_REQUEST_CODE);
        return "";
        }
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    protected boolean isPermissionGranted(String permission){
        int permissionState = ActivityCompat.checkSelfPermission(this, permission);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    protected void requestPermission(String permission, int requestCode){
         ActivityCompat.requestPermissions(this, new String[]{permission},requestCode);
    }
}
