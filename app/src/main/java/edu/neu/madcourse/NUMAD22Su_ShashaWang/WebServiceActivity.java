package edu.neu.madcourse.NUMAD22Su_ShashaWang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class WebServiceActivity extends AppCompatActivity {
    ImageView dogImage;
    Spinner breedSpinner;
    ProgressBar progressBar;
    private Handler imageHandler = new Handler();
    private Handler breedListHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);
        dogImage = findViewById(R.id.dog);
        breedSpinner = findViewById(R.id.breed_spinner);
        progressBar = findViewById(R.id.loading_bar);
        new GetBreedListThread(this.getApplicationContext()).start();
    }

    //  Threads that executes web API call to get dog image
    private class GetDogThread extends Thread {
        private String dogApiUrl;

        public GetDogThread(String dogApiUrl) {
            this.dogApiUrl = dogApiUrl;
        }

        @Override
        public void run() {
            String dogUrl = getDogImageUrl(dogApiUrl);
            Bitmap image = downloadDogImage(dogUrl);
            // set image using handler
            imageHandler.post(() -> {
                progressBar.setVisibility(View.GONE);
                dogImage.setImageBitmap(image);
                dogImage.setVisibility(View.VISIBLE);
            });
        }
    }

    protected String getDogImageUrl(String dogApiUrl) {
        URL url;
        String dogUrl = "";
        try {
            url = new URL(dogApiUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            InputStream is = conn.getInputStream();
            final String res = convertStreamToString(is);
            JSONObject jsonObject = new JSONObject(res);
            dogUrl = jsonObject.getString("message");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dogUrl;
    }

    protected Bitmap downloadDogImage(String dogImageUrl) {
        Bitmap myBitmap = null;
        try {
            URL urlConnection = new URL(dogImageUrl);
            HttpsURLConnection connection = (HttpsURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            myBitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myBitmap;
    }

    // Thread downloads all breeds from the web API
    private class GetBreedListThread extends Thread {
        private Context context;

        public GetBreedListThread(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            List<String> resList = new ArrayList<>();
            try {
                URL urlConnection = new URL("https://dog.ceo/api/breeds/list/all");
                HttpsURLConnection connection = (HttpsURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                String resStr = convertStreamToString(input);
                JSONObject object = new JSONObject(resStr);
                JSONObject message = object.getJSONObject("message");
                Iterator<String> keys = message.keys();
                while (keys.hasNext()) {
                    resList.add(keys.next());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            breedListHandler.post(() -> {
                ArrayAdapter<String> adapter
                        = new ArrayAdapter<>
                        (context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, resList);
                breedSpinner.setAdapter(adapter);
            });
        }
    }

    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    // Button onclick listener for getting dog image by breed
    public void breedButtonOnClick(View view) {
        String breed = breedSpinner.getSelectedItem().toString();
        progressBar.setVisibility(View.VISIBLE);
        dogImage.setVisibility(View.GONE);
        new GetDogThread("https://dog.ceo/api/breed/" + breed + "/images/random").start();
    }

    // Button onclick listener for randomly getting all dog image
    public void randomGetDogOnClick(View view) {
        progressBar.setVisibility(View.VISIBLE);
        dogImage.setVisibility(View.GONE);
        new GetDogThread("https://dog.ceo/api/breeds/image/random").start();
    }
}
