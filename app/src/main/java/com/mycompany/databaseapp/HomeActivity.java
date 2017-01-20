package com.mycompany.databaseapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity implements LocationListener{
    TextView latitudeField;
    TextView longitudeField;
    LocationManager locationManager;
    final String[] LOCATION_PERMS = {android.Manifest.permission.ACCESS_FINE_LOCATION};
    final int LOCATION_REQUEST = 1340;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, this);
        } else {
            requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);
        }
    }

    public static double userLat = 0 ;
    public static double userLng = 0;
    public void onLocationChanged(Location location) {
        if (location != null) {
            Double userLocLat = location.getLatitude();
            Double userLocLng = location.getLongitude();
            userLat = userLocLat;
            userLng = userLocLng;
            Log.i("Location info: Lat", userLocLat.toString());
            Log.i("Location info: Lng", userLocLng.toString());
        }
    }

    public void onProviderDisabled(String provider) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, this);
                } else {
                    Toast.makeText(this, "Location cannot be obtained due to " + "missing permission.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void onClickStartDatabaseListActivity(View view) {
        Intent intent = new Intent(this, DatabaseListActivity.class);
        startActivity(intent);
    }

    public void onClickStartBigMapActivity(View view) {
        Intent intent = new Intent(this, BigMapActivity.class);
        startActivity(intent);
    }
}