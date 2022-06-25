package edu.neu.madcourse.NUMAD22Su_ShashaWang;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class WebServiceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);
    }

    public void onClickSendRequest(View view) {
        SampleWebServiceTask webServiceTask = new SampleWebServiceTask();
        webServiceTask.execute("https://api.genderize.io?name=peter");
    }

    private class SampleWebServiceTask extends AsyncTask<String,Integer, String> {

        @Override
        protected void onProgressUpdate(Integer...value) {}
        @Override
        protected String doInBackground(String... params) {
            URL url;
            try {
                url= new URL(params[0]);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                InputStream is = conn.getInputStream();

                final String res = convertStreamToString(is);
                System.out.println("jerry \n\n"+res);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
    private String convertStreamToString (InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext()?s.next().replace(",","\n"):"";
    }
}
