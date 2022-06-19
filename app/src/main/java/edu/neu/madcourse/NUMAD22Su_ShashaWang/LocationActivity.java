package edu.neu.madcourse.NUMAD22Su_ShashaWang;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;


public class LocationActivity extends AppCompatActivity {
    private TextView currentLatitude;
    private TextView currentLongitude;
    private TextView travelDistance;
    private FusedLocationProviderClient fusedLocationClient;
    private double latitudeValue;
    private double longitudeValue;
    private double distanceValue;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private CheckBox highPrecisionCheckBox;
    private int locationUpdateInterval = 6000;
    private int fastestInterval = 500;
    private int precisionCode = Priority.PRIORITY_BALANCED_POWER_ACCURACY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        currentLatitude = findViewById(R.id.current_latitude);
        currentLongitude = findViewById(R.id.current_longitude);
        travelDistance = findViewById(R.id.total_distance);
        latitudeValue = 0.0f;
        longitudeValue = 0.0f;
        highPrecisionCheckBox = findViewById(R.id.high_precision);
        if (savedInstanceState != null && savedInstanceState.containsKey("distance")) {
            distanceValue = savedInstanceState.getDouble("distance");
        } else {
            distanceValue = 0.0f;
        }
        currentLatitude.setText("Current latitude: " + latitudeValue);
        currentLongitude.setText("current longitude: " + longitudeValue);
        travelDistance.setText("total_distance: " + String.format("%.2f", distanceValue) + "M");
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocationAndStartLocationUpdate();

    }

    private void getLastLocationAndStartLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    /*requestCode=*/1);
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, this::setLocation);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                onLocationChanged(locationResult.getLastLocation());
            }
        };
        locationRequest = locationRequest.create();
        boolean isHighPrecision = ActivityCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED;
        // fine location granted using high precision
        locationRequest.setInterval(locationUpdateInterval);
        locationRequest.setFastestInterval(fastestInterval);
        locationRequest.setPriority(precisionCode);
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    private void setLocation(Location location) {
        // Got last known location. In some rare situations this can be null.
        if (location == null) {
            currentLatitude.setText("Error getting location");
            currentLongitude.setText("Error getting location");
            travelDistance.setText("Error getting location");
            return;
        }
        latitudeValue = location.getLatitude();
        longitudeValue = location.getLongitude();
        currentLatitude.setText("Current latitude:" + latitudeValue);
        currentLongitude.setText("current longitude: " + longitudeValue);
        travelDistance.setText("total_distance:" + String.format("%.2f", distanceValue) + "M");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (ActivityCompat
                        .checkSelfPermission(
                                this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    onBackPressed();
                    return;
                }
                if (ActivityCompat
                        .checkSelfPermission(
                                this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    highPrecisionCheckBox.setChecked(false);
                    fastestInterval = 500;
                    locationUpdateInterval = 6000;
                    precisionCode = Priority.PRIORITY_BALANCED_POWER_ACCURACY;
                } else {
                    fastestInterval = 50;
                    locationUpdateInterval = 100;
                    precisionCode = Priority.PRIORITY_HIGH_ACCURACY;
                }
                return;
            case 2:
                if (ActivityCompat
                        .checkSelfPermission(
                                this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    highPrecisionCheckBox.setChecked(false);
                    fastestInterval = 500;
                    locationUpdateInterval = 6000;
                    precisionCode = Priority.PRIORITY_BALANCED_POWER_ACCURACY;
                    CharSequence text = "High Precision Location permission not granted, " +
                            "using low precision for now";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                    toast.show();
                } else {
                    fastestInterval = 50;
                    locationUpdateInterval = 100;
                    precisionCode = Priority.PRIORITY_HIGH_ACCURACY;
                }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    public void onLocationChanged(Location location) {
        if (location == null) {
            currentLatitude.setText("Error getting location");
            currentLongitude.setText("Error getting location");
            travelDistance.setText("Error getting location");
            return;
        }
        double newLongitude = location.getLongitude();
        double newLatitude = location.getLatitude();
        float[] res = new float[1];
        Location.distanceBetween(latitudeValue, longitudeValue, newLatitude, newLongitude, res);
        distanceValue += res[0];
        latitudeValue = newLatitude;
        longitudeValue = newLongitude;
        currentLatitude.setText("Current latitude:" + latitudeValue);
        currentLongitude.setText("current longitude: " + longitudeValue);
        travelDistance.setText("total_distance:" + String.format("%.2f", distanceValue) + "M");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putDouble("distance", distanceValue);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void resetDistance(View view) {
        distanceValue = 0.0;
        travelDistance.setText("total_distance:" + String.format("%.2f", distanceValue) + "M");
    }

    public void onHighPrecisionPress(View view) {
        CheckBox checkBox = (CheckBox) view;
        stopLocationUpdates();
        // only start asking user for high precision permission when it is not granted
        if (checkBox.isChecked() && ActivityCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    /*requestCode=*/2);

        } else if (checkBox.isChecked() && ActivityCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            fastestInterval = 50;
            locationUpdateInterval = 100;
            precisionCode = Priority.PRIORITY_HIGH_ACCURACY;
        } else {
            fastestInterval = 500;
            locationUpdateInterval = 6000;
            precisionCode = Priority.PRIORITY_BALANCED_POWER_ACCURACY;
        }
        locationRequest.setInterval(locationUpdateInterval);
        locationRequest.setFastestInterval(fastestInterval);
        locationRequest.setPriority(precisionCode);
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());

    }
}




