package com.project.mobile.weatherapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;


import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class LocationUtil {
    private Activity activity;
    private Context context;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private boolean isContinue = false;
    private boolean checkGPS = false;
    private LocationCallback locationCallback;
    private  double lat;
    private  double lon;
    public LocationUtil() {

    }
    public LocationUtil(Activity activity){
        this.context = activity.getApplicationContext();
        this.activity = activity;
        mFusedLocationClient = getFusedLocationProviderClient(context);
        statusCheck();
        if (!checkGPS) {
            showSettingsAlert();
        }
        getLocation();
    }
    private void setLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000); // 10 seconds
        locationRequest.setFastestInterval(5 * 1000); // 5 seconds
    }

    private void setLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {

                        if (!isContinue && mFusedLocationClient != null) {
                            mFusedLocationClient.removeLocationUpdates(locationCallback);
                        }
                    }
                }
            }
        };
    }

    public  double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLat(double lat){
        this.lat = lat;
    }
    public void setLon(double lon){
        this.lon = lon;
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setTitle("GPS is not Enabled!");

        alertDialog.setMessage("Do you want to turn on GPS?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });


        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    public void statusCheck() {
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            checkGPS = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized  void getLocation() {
        final LocationUtil locationUtil = new LocationUtil();
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    Constants.LOCATION_REQUEST);

        } else {


            if (isContinue) {
                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
            } else {
                mFusedLocationClient.getLastLocation().addOnSuccessListener(activity,
                        location -> {

                            if (location != null) {
                                Log.d("debug1","1");
                                onLocationChanged(location);
                            } else {
                                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                            }
                        });

            }
        }
    }
    public void onLocationChanged(Location location) {
        // New location has now been determined
        setLon(location.getLongitude());
        setLat(location.getLatitude());
        Log.d("debug3","3");
        WeatherAsyncTask weatherAsyncTask = new WeatherAsyncTask(getLat(),getLon(),activity);
        weatherAsyncTask.execute();
    }

}
