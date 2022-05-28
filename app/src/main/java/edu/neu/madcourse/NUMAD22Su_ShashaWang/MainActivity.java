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
        Button aboutMe = (Button) findViewById(R.id.about_me);
        Button clicky = (Button) findViewById(R.id.clicky);

        EditText edit = (EditText)findViewById(R.id.my_edit);
        aboutMe.setOnClickListener(v -> {
            Context context = getApplicationContext();
            CharSequence text = "Name: Shasha Wang \n Email: wang.shas@northeastern.edu \n";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });
        clicky.setOnClickListener(v->{
            Intent intent = new Intent(this, ClickyActivity.class);
            startActivity(intent);
        });


    }
}