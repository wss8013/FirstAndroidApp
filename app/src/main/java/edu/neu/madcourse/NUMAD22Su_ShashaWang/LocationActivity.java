package edu.neu.madcourse.NUMAD22Su_ShashaWang;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;

public class LocationActivity extends AppCompatActivity  implements
        LocationListener {
    private TextView current_latitude;
    private TextView current_longitude;
    private TextView travel_distance;
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        current_latitude = findViewById(R.id.current_latitude);
        current_longitude = findViewById(R.id.current_longitude);
        travel_distance  = findViewById(R.id.total_distance);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}




