package edu.neu.madcourse.NUMAD22Su_ShashaWang;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ClickyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky);
        Button buttonA = findViewById(R.id.a);
        Button buttonB = findViewById(R.id.b);
        Button buttonC = findViewById(R.id.c);
        Button buttonD = findViewById(R.id.d);
        Button buttonE = findViewById(R.id.e);
        Button buttonF = findViewById(R.id.f);
    }
}
