package edu.neu.madcourse.NUMAD22Su_ShashaWang;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

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
        TextView buttonText = findViewById(R.id.pressed);
        buttonA.setOnClickListener(v->setButtonText((Button)v,buttonText ));
        buttonB.setOnClickListener(v->setButtonText((Button)v,buttonText ));
        buttonC.setOnClickListener(v->setButtonText((Button)v,buttonText ));
        buttonD.setOnClickListener(v->setButtonText((Button)v,buttonText ));
        buttonE.setOnClickListener(v->setButtonText((Button)v,buttonText ));
        buttonF.setOnClickListener(v->setButtonText((Button)v,buttonText ));
    }

    private void setButtonText(Button button,TextView textView ) {
        textView.setText("Pressed: " + button.getText());
    }
}
