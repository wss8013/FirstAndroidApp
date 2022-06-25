package edu.neu.madcourse.NUMAD22Su_ShashaWang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);
        dogImage = findViewById(R.id.dog);
        breedSpinner = findViewById(R.id.breed_spinner);
        new DownloadBreedTask(breedSpinner, this.getApplicationContext())
                .execute("https://dog.ceo/api/breeds/list/all");
    }

    public void randomGetDogOnClick(View view) {
        GetDogTask webServiceTask = new GetDogTask();
        webServiceTask.execute("https://dog.ceo/api/breeds/image/random");
    }

    private class GetDogTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onProgressUpdate(Integer... value) {
        }

        @Override
        protected String doInBackground(String... params) {
            URL url;
            String dogUrl = "";
            try {
                url = new URL(params[0]);
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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            new DownloadImageTask(dogImage).execute(s);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap myBitmap = null;
            try {
                URL urlConnection = new URL(urlDisplay);
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

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            bmImage.setImageBitmap(result);
        }
    }

    private class DownloadBreedTask extends AsyncTask<String, Void, String[]> {
        Spinner breedSpinner;
        Context context;

        public DownloadBreedTask(Spinner spinner, Context context) {
            this.breedSpinner = spinner;
            this.context = context;
        }

        @Override
        protected String[] doInBackground(String... urls) {
            System.out.println("start downloading bread list " + urls[0]);
            String resStrArray[];
            String urlDisplay = urls[0];
            try {
                URL urlConnection = new URL(urlDisplay);
                HttpsURLConnection connection = (HttpsURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                String resStr = convertStreamToString(input);
                List<String> resList = new ArrayList<>();
                JSONObject object = new JSONObject(resStr);
                JSONObject message = object.getJSONObject("message");
                Iterator<String> keys = message.keys();
                while (keys.hasNext()) {
                    resList.add(keys.next());
                }
                resStrArray = new String[resList.size()];
                for (int i = 0; i < resStrArray.length; i++) {
                    resStrArray[i] = resList.get(i);
                }
            } catch (Exception e) {
                resStrArray = new String[0];
                e.printStackTrace();
            }
            return resStrArray;
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            ArrayAdapter<String> adapter
                    = new ArrayAdapter<>
                    (context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, result);
            breedSpinner.setAdapter(adapter);
        }
    }

    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public void breedButtonOnClick(View view) {
        String breed = breedSpinner.getSelectedItem().toString();
        new GetDogTask().execute("https://dog.ceo/api/breed/" + breed + "/images/random");
    }
}
