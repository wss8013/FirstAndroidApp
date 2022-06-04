package edu.neu.madcourse.NUMAD22Su_ShashaWang;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class LinkCollectorActivity extends AppCompatActivity {
    private List<LinkInfo> links = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);
        FloatingActionButton addButton = findViewById(R.id.add_alarm_fab);
        addButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Type Link");
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            final EditText name = new EditText(this);
            final EditText link = new EditText(this);
            linearLayout.addView(name);
            linearLayout.addView(link);
            builder.setView(linearLayout);
            builder.setPositiveButton("OK", (dialog, which) -> {
                String nameText = name.getText().toString();
                String linkText = link.getText().toString();
                String snackBarText = "";
                if (nameText.isEmpty() || linkText.isEmpty()) {
                    snackBarText = " Link or name must not be empty";
                } else {
                    snackBarText = "Link successfully added";
                    links.add(new LinkInfo(nameText,linkText));
                }

                Snackbar snackbar = Snackbar.make(findViewById(R.id.link_collector_layout),
                        snackBarText, Snackbar.LENGTH_LONG);
                snackbar.setAction("Dismiss", v1 -> {
                    snackbar.dismiss();
                });
                snackbar.show();

            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
        });
    }
}
