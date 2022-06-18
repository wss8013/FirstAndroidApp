package edu.neu.madcourse.NUMAD22Su_ShashaWang;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;

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
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationClient;
    private double latitudeValue;
    private double longitudeValue;
    private double distanceValue;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        currentLatitude = findViewById(R.id.current_latitude);
        currentLongitude = findViewById(R.id.current_longitude);
        travelDistance = findViewById(R.id.total_distance);
        latitudeValue = 0.0f;
        longitudeValue = 0.0f;
        if (savedInstanceState != null && savedInstanceState.containsKey("distance")) {
            System.out.println(" shasha saving instance value ");
            distanceValue = savedInstanceState.getDouble("distance");
        } else {
            distanceValue = 0.0f;
        }
        currentLatitude.setText("Current latitude: " + latitudeValue);
        currentLongitude.setText("current longitude: " + longitudeValue);
        travelDistance.setText("total_distance: " + String.format("%.2f", distanceValue) + "M");
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    /*requestCode=*/1);
            return;
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, this::setLocation);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                System.out.println("jerry here get location update "
                        + locationResult.getLastLocation().toString());
                onLocationChanged(locationResult.getLastLocation());
            }
        };
        locationRequest = locationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(50);
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);
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
        System.out.println("set location here here here ");
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
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                } else {
                    // if permission is denied return to main activity
                    onBackPressed();
                }
                return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();

    }

    private void startLocationUpdates() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
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
}




