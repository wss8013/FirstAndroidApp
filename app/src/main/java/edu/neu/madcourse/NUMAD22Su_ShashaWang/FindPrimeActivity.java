package edu.neu.madcourse.NUMAD22Su_ShashaWang;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class FindPrimeActivity extends AppCompatActivity {

    private Button findPrime;
    private Button terminate;
    private TextView currentNum;
    private TextView latestPrime;
    private CheckBox dummyCheck;
    private boolean run;

    private Handler textHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_prime);
        findPrime = findViewById(R.id.find_primes);
        terminate = findViewById(R.id.terminate_search);
        currentNum = findViewById(R.id.current_num);
        latestPrime = findViewById(R.id.latest_prime);
        dummyCheck = findViewById(R.id.dummy_checkbox);
    }

    @Override
    public void onBackPressed() {
        if (run) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
        } else {
            finish();
        }
    }
    //Method which runs on a different thread which uses Thread class.
    public void runSearchPrime(View view) {
        SearchPrime searchPrime = new SearchPrime();
        searchPrime.start();
    }

    public void terminateSearch(View view) {
        TerminateSearch terminateSearch = new TerminateSearch();
        terminateSearch.start();
    }

    class SearchPrime extends Thread {
        @Override
        public void run() {
            int i = 2;
            run = true;
            while (run){
                final int currentChecked = i;
                textHandler.post(() -> {
                    currentNum.setText("Currently Checking: " + currentChecked);
                    if (isPrime(currentChecked)) {
                        latestPrime.setText("Latest Prime: " + currentChecked);
                    }
                });
                try {
                    Thread.sleep(1000); //Makes the thread sleep or be inactive for 1 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }

        private boolean isPrime(int p) {
            for (int i = 2; i < p; i++) {
                if (p % i == 0) {
                    return false;
                }
            }
            return true;
        }
    }
    class TerminateSearch extends Thread {
        @Override
        public void run() {
            run = false;
        }
    }
}