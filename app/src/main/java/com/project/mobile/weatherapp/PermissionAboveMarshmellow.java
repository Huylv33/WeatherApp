package com.project.mobile.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class PermissionAboveMarshmellow extends AppCompatActivity {
    private static int LOCATION_PERMISSIONS_REQUEST = 1;
    private static final String SHARED_PREFERENCES_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        askPermissions();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("IS_FIRST_LAUNCHER",true).apply();
    }

    protected void askPermissions() {

        if(!checkWriteExternalPermission()){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSIONS_REQUEST);

            Log.i("askPermissions:", "askPermissions");
        }
    }

    private boolean checkWriteExternalPermission() {

        String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {

        if (requestCode == LOCATION_PERMISSIONS_REQUEST) {

            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Log.i("RequestPermissions:", "RequestPermissionsInside");
                finish();

            } else {
                Toast.makeText(this, "Access Location permission denied, PLEASE ACCEPT IT.", Toast.LENGTH_SHORT).show();
                askPermissions();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }
    }
}
