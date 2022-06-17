package edu.neu.madcourse.NUMAD22Su_ShashaWang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button aboutMe =  findViewById(R.id.about_me);
        Button clicky = findViewById(R.id.clicky);
        Button linkCollector = findViewById(R.id.link_collector);
        Button findPrime = findViewById(R.id.prime);
        Button location = findViewById(R.id.activity_location);

        aboutMe.setOnClickListener(v -> {
            Intent intent = new Intent(this, AboutMeActivity.class);
            startActivity(intent);

        });
        clicky.setOnClickListener(v->{
            Intent intent = new Intent(this, ClickyActivity.class);
            startActivity(intent);
        });

        linkCollector.setOnClickListener(v->{
            Intent intent = new Intent(this, LinkCollectorActivity.class);
            startActivity(intent);
        });

        findPrime.setOnClickListener(v->{
            Intent intent = new Intent(this, FindPrimeActivity.class);
            startActivity(intent);
        });
    }
}